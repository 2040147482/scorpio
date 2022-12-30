package com.leslie.recommender;

import com.leslie.clients.ProductClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.leslie.recommender.mapper")
@SpringBootApplication
@EnableFeignClients(clients = ProductClient.class)
public class recommenderApplication {
    public static void main(String[] args){
        SpringApplication.run(recommenderApplication.class, args);
    }
}
