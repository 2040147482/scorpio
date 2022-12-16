package com.leslie.admin;

import com.leslie.clients.FastdfsClient;
import com.leslie.clients.OrderClient;
import com.leslie.clients.ProductClient;
import com.leslie.clients.UserClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 20110
 */
@MapperScan("com.leslie.admin.mapper")
@SpringBootApplication
@EnableFeignClients(clients = {UserClient.class, ProductClient.class, OrderClient.class, FastdfsClient.class})
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
