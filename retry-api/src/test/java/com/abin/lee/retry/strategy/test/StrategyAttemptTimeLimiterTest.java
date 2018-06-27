package com.abin.lee.retry.strategy.test;

import com.github.rholder.retry.*;
import com.google.common.base.Predicates;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by abin on 2018/6/27.
 * guava-retrying重试工具库: 阻塞策略BlockStrategy
 * https://blog.csdn.net/aitangyong/article/details/53894615
 */
public class StrategyAttemptTimeLimiterTest {
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS");

    private static Callable<Boolean> buildTask() {
        return new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                System.out.println("called");
                long start = System.currentTimeMillis();
                long end = start;
                while (end - start <= 10 * 1000) {
                    end = System.currentTimeMillis();
                }

                return true;
            }
        };
    }


    @Test
    public void testRetry1() throws InterruptedException {
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfException()
                .retryIfResult(Predicates.equalTo(false))
                .withAttemptTimeLimiter(AttemptTimeLimiters.fixedTimeLimit(3, TimeUnit.SECONDS))
                .withStopStrategy(StopStrategies.stopAfterAttempt(5))
                .build();

        System.out.println("begin..." + df.format(new Date()));

        try {
            retryer.call(buildTask());
        } catch (Exception e) {
            System.err.println("still failed after retry." + e.getCause().toString());
        }

        System.out.println("end..." + df.format(new Date()));


    }





}
