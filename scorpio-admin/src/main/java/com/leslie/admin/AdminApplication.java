package com.leslie.admin;

import com.leslie.clients.OrderClient;
import com.leslie.clients.ProductClient;
import com.leslie.clients.UserInfoClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 20110
 */
@MapperScan("com.leslie.admin.mapper")
@SpringBootApplication
@EnableFeignClients(clients = {UserInfoClient.class, ProductClient.class, OrderClient.class})
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
