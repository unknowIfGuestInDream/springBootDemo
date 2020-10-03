package com.tangl.demo.config;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: TangLiang
 * @date: 2020/9/8 13:56
 * @since: 1.0
 */
@Configuration
public class minioConfig {

    @Value("${minio.endpoint}")
    private String ENDPOINT;
    @Value("${minio.accessKey}")
    private String ACCESS_KEY;
    @Value("${minio.secretKey}")
    private String SECRET_KEY;

    @Bean
    public MinioClient minioClient() {
        try {
            return new MinioClient(ENDPOINT, ACCESS_KEY, SECRET_KEY);
        } catch (InvalidEndpointException e) {
            e.printStackTrace();
        } catch (InvalidPortException e) {
            e.printStackTrace();
        }
        return null;
    }
}