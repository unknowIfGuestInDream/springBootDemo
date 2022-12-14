package com.tangl.demo.aop;

import com.tangl.demo.annotation.LogAnno;
import com.tangl.demo.util.IpUtils;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.tangl.demo.util.BaseUtil.getIp;
import static com.tangl.demo.util.BaseUtil.getUrl;

/**
 * @author: TangLiang
 * @date: 2020/7/2 14:00
 * @since: 1.0
 */
@Aspect
@Component
//@Order(Ordered.LOWEST_PRECEDENCE-1)
@Order(1)
@Slf4j
public class WebLogAspect {
    //private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Autowired
    HttpServletRequest request;

    /**
     * 定义切入点
     * 通过@Pointcut注解声明频繁使用的切点表达式
     */
    @Pointcut("execution(public * com.tangl.demo.controller.FirstController.selectTest(..)))")
    public void BrokerAspect() {

    }

    @Pointcut("execution(* com.tangl.demo.controller.*.select*(..))")
    public void select() {

    }

    @Pointcut("execution(* com.tangl.demo.controller.*.insert*(..))")
    public void insert() {

    }

    @Pointcut("@annotation(com.tangl.demo.annotation.LogAnno)")
    public void logAnno() {
    }

    /**
     * @description 在连接点执行之前执行的通知
     */
    @Before("BrokerAspect() || insert()")
    public void doBeforeGame(JoinPoint joinPoint) {
//        System.out.println("目标方法名为:" + joinPoint.getSignature().getName());
//        System.out.println("目标方法所属类的简单类名:" + joinPoint.getSignature().getDeclaringType().getSimpleName());
//        System.out.println("目标方法所属类的类名:" + joinPoint.getSignature().getDeclaringTypeName());
//        System.out.println("目标方法声明类型:" + Modifier.toString(joinPoint.getSignature().getModifiers()));
//        //获取传入目标方法的参数
//        Object[] args = joinPoint.getArgs();
//        for (int i = 0; i < args.length; i++) {
//            System.out.println("第" + (i + 1) + "个参数为:" + args[i]);
//        }
//        System.out.println("被代理的对象:" + joinPoint.getTarget());
//        System.out.println("代理对象自己:" + joinPoint.getThis());
    }

    //@Around("BrokerAspect()")
    @Around("logAnno()")
    public Object doAround(ProceedingJoinPoint pjd) {
        log.info("进入[{}]方法", pjd.getSignature().getName());
        Object result;
        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) pjd.getSignature();
        // 获取方法
        Method method = methodSignature.getMethod();
        LogAnno logAnno = method.getAnnotation(LogAnno.class);
        String operateType = logAnno.operateType();

        //获取request 也可以通过注解 都是安全的
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
//                .getRequest();

        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));   //req就是request请求
        Browser browser = userAgent.getBrowser();  //获取浏览器信息
        Version version = userAgent.getBrowserVersion();//浏览器版本
        OperatingSystem os = userAgent.getOperatingSystem(); //获取操作系统信息
        String ipName = IpUtils.getHostName();
        String ip = getIp(request);//获取ip地址
        String url = getUrl(request);//获取url

        log.info("方法描述: {} \n 浏览器: {} \n 操作系统: {} \n 用户: {} \n IP: {} \n URL: {}", operateType, browser, os, ipName, ip, url);
        try {
            //执行目标方法
            result = pjd.proceed();
            //用新的参数值执行目标方法
            //result = pjd.proceed(new Object[]{"newSpring", "newAop"});
            log.info("离开方法: {} ", pjd.getSignature().getName());
        } catch (Throwable e) {
            e.printStackTrace();
            Map map = new HashMap();
            map.put("success", false);
            map.put("message", e.getMessage());
            log.error("异常信息: {} ", e.getMessage());
            return map;
        }

        return result;
    }

    /**
     * @description 在连接点执行之后执行的通知（返回通知和异常通知的异常）
     */
    @After("BrokerAspect()")
    public void doAfterGame(JoinPoint joinPoint) {
    }

    /**
     * @description 在连接点执行之后执行的通知（返回通知）
     */
    @AfterReturning(value = "BrokerAspect()", returning = "result")
    public void doAfterReturningGame(JoinPoint joinPoint, Object result) {
    }

    /**
     * @description 在连接点执行之后执行的通知（异常通知）
     */
    @AfterThrowing(value = "BrokerAspect()", throwing = "exception")
    public void doAfterThrowingGame(JoinPoint joinPoint, Exception exception) {
    }
}
