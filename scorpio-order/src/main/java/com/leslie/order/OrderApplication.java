package com.leslie.order;

import com.leslie.clients.ProductClient;
import com.leslie.clients.UserClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 20110
 * @descript 订单服务启动类
 */
@SpringBootApplication
@MapperScan("com.leslie.order.mapper")
@EnableFeignClients(clients = {ProductClient.class, UserClient.class})
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
