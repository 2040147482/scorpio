package com.leslie.order;

import com.leslie.order.utils.OrderIdBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 20110
 * @descript 订单测试类
 */
@SpringBootTest
@Slf4j
public class TestApp {

    @Autowired
    private OrderIdBuilder orderIdBuilder;

    private ExecutorService es = Executors.newFixedThreadPool(100);

    @Test
    void testIdBuilder() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(300);
        Runnable task = () -> {
            for (int i = 0; i < 100; i++) {
                long id = orderIdBuilder.nextId("order");
                System.out.println("id =" + id);
            }
            latch.countDown();
        };
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 300; i++) {
            es.submit(task);

        }
        latch.await();
        long end = System.currentTimeMillis();

        log.error("耗时：" + (end - begin));
    }


}



