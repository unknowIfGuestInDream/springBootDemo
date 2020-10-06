package com.tangl.demo.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * cache默认配置，如果需要自由操作Redis 调用 RedisService
 *
 * @author 唐亮
 * @date 13:02 2020/7/11
 * @return
 */
@Configuration
@Slf4j
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;
    @Value("${redis.key.expire.authCode}")
    private Long AUTH_CODE_EXPIRE_SECONDS;

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisSerializer<Object> serializer = redisSerializer();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();

        //运用代理添加redis日志
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(redisTemplate);
        //setProxyTargetClass为true，就是以cglib动态代理方式生成代理类，淡然设置为false，就是默认用JDK动态代理技术
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.addAdvice(new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation methodInvocation) throws Throwable {
                //拦截opsForHash
                Boolean b = methodInvocation.getMethod().getName().equals("opsForValue");
                if (b) {
                    //取得opsForHash结果
                    ValueOperations valueOperations = (ValueOperations) methodInvocation.proceed();
                    //对hashOperations代理
                    ProxyFactory proxyFactory = new ProxyFactory();
                    proxyFactory.setTarget(valueOperations);
                    proxyFactory.setProxyTargetClass(false);
                    //添加日志
                    proxyFactory.addAdvice(new MethodBeforeAdviceInterceptor(new MethodBeforeAdvice() {
                        @Override
                        public void before(Method method, Object[] args, Object o) throws Throwable {
                            log.debug("method:{}, args:{}", method.getName(), JSON.toJSONString(args, SerializerFeature.PrettyFormat));
                        }
                    }));
                    //返回代理
                    return proxyFactory.getProxy();
                }
                return methodInvocation.proceed();
            }
        });
        Object proxy = proxyFactory.getProxy();
        return (RedisTemplate<String, Object>) proxy;
        //return redisTemplate;
    }

    @Bean
    public RedisSerializer<Object> redisSerializer() {
        //创建JSON序列化器
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }

    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        //设置Redis缓存有效期为1天
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer()))
                .prefixCacheNameWith(REDIS_KEY_PREFIX_AUTH_CODE)
                //失效时间
                .entryTtl(Duration.ofSeconds(AUTH_CODE_EXPIRE_SECONDS))
                .disableCachingNullValues();
        //.disableKeyPrefix();
        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new IgnoreExceptionCacheErrorHandler();
    }
}
