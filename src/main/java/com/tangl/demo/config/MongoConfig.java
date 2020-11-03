package com.tangl.demo.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.util.Arrays;

/**
 * @author: TangLiang
 * @date: 2020/10/2 21:38
 * @since: 1.0
 */
@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private Integer port;

    @Value("${spring.data.mongodb.grid-fs-database}")
    private String db;

    @Value("log")
    private String bucketName;

    @Bean
    @Qualifier("mongoClient")
    public MongoClient getMongoClient() {
        MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyToClusterSettings(builder ->
                                builder.hosts(Arrays.asList(new ServerAddress(host, port))))
                        .build());
        return mongoClient;
    }

    @Bean
    public GridFsTemplate gridFsTemplate(@Qualifier("mongoClient") MongoClient mongoClient, MongoConverter converter) {
        MongoDatabase database = mongoClient.getDatabase(db);
        MongoDatabaseFactory mongoDatabaseFactory = new SimpleMongoClientDatabaseFactory(mongoClient, database.getName());
        return new GridFsTemplate(mongoDatabaseFactory, converter, bucketName);
    }

    @Bean
    public GridFSBucket getGridFSBuckets(@Qualifier("mongoClient") MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase(db);
        return GridFSBuckets.create(database, bucketName);
    }
}
