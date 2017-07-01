package com.fladimir.yujungong;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by NingJiang on 2017/6/29.
 * Class Note:多线程处理
 */

public class ExecutorServiceTest {
    @Test
    public void simpleUsage() throws Exception {
        ExecutorService executorServiceTest = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 10; i++) {
            executorServiceTest.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + ":" + System.currentTimeMillis());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        Thread.sleep(10000);
    }
}
