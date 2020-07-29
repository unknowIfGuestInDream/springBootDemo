package com.tangl.demo.config;

import com.tangl.demo.async.IdleConnectionEvictor;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: TangLiang
 * @date: 2020/7/29 8:40
 * @since: 1.0
 */
@Configuration
public class HttpClientConfig {
    @Value("${http.maxTotal}")
    private Integer maxTotal;// 最大连接
    @Value("${http.defaultMaxPerRoute}")
    private Integer defaultMaxPerRoute;// 每个host的最大连接
    @Value("${http.connectTimeout}")
    private Integer connectTimeout;// 连接超时时间
    @Value("${http.connectionRequestTimeout}")
    private Integer connectionRequestTimeout;// 请求超时时间
    @Value("${http.socketTimeout}")
    private Integer socketTimeout;// 响应超时时间
    @Value("${http.waitTime}")
    private int waitTime;
    @Value("${http.idleConTime}")
    private int idleConTime;
    @Value("${http.retryCount}")
    private int retryCount;
    @Value("${http.validateAfterInactivity}")
    private int validateAfterInactivity;

    @Bean
    public PoolingHttpClientConnectionManager createPoolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager poolmanager = new PoolingHttpClientConnectionManager();
        poolmanager.setMaxTotal(maxTotal);
        poolmanager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        poolmanager.setValidateAfterInactivity(validateAfterInactivity);
        return poolmanager;
    }

    @Bean
    public CloseableHttpClient createHttpClient(PoolingHttpClientConnectionManager poolManager) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create().setConnectionManager(poolManager);
        httpClientBuilder.setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {

            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                HeaderElementIterator iterator = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (iterator.hasNext()) {
                    HeaderElement headerElement = iterator.nextElement();
                    String param = headerElement.getName();
                    String value = headerElement.getValue();
                    if (null != value && param.equalsIgnoreCase("timeout")) {
                        return Long.parseLong(value) * 1000;
                    }
                }
                return 30 * 1000;
            }
        });
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(retryCount, false));
        return httpClientBuilder.build();
    }

    @Bean
    public RequestConfig createRequestConfig() {
        return RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)     // 从连接池中取连接的超时时间
                .setConnectTimeout(connectTimeout)                        // 连接超时时间
                .setSocketTimeout(socketTimeout)                        // 请求超时时间
                .build();
    }

    @Bean
    public IdleConnectionEvictor createIdleConnectionEvictor(PoolingHttpClientConnectionManager poolManager) {
        IdleConnectionEvictor idleConnectionEvictor = new IdleConnectionEvictor(poolManager, waitTime, idleConTime);
        return idleConnectionEvictor;
    }
}
