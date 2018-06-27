package com.abin.lee.retry.strategy.test;

import com.github.rholder.retry.*;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by abin on 2018/6/27.
 * guava-retrying重试工具库: 什么时候终止
 * https://blog.csdn.net/aitangyong/article/details/53888889
 */
public class RetryStrategyStopTest {

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS");

    private static Callable<Void> task = new Callable<Void>() {
        @Override
        public Void call() throws Exception {
            System.out.println(df.format(new Date()));
            throw new RuntimeException();
        }
    };

    @Test
    public void testRetry1() {
        // 每次间隔1s后重试
        Retryer<Void> retryer = RetryerBuilder.<Void>newBuilder()
                .retryIfException()
                .withWaitStrategy(WaitStrategies.fixedWait(5, TimeUnit.SECONDS))
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                //.withStopStrategy(StopStrategies.neverStop())
                //.withStopStrategy(StopStrategies.stopAfterDelay(10, TimeUnit.SECONDS))
                .build();

        System.out.println("begin..." + df.format(new Date()));

        try {
            retryer.call(task);
        } catch (Exception e) {
            System.err.println("still failed after retry." + e.getCause().toString());
        }

        System.out.println("end..." + df.format(new Date()));
    }


    public static class MyStopStrategy implements StopStrategy {
        private int attemptCount;
        public MyStopStrategy() {
            this.attemptCount = (int) Math.floor(Math.random() * 10) + 1;
            System.out.println("重试" + attemptCount + "后终止");
        }
        @Override
        public boolean shouldStop(Attempt failedAttempt) {
            return failedAttempt.getAttemptNumber() == attemptCount;
        }
    }

    @Test
    public void testRetry2() {
        Retryer<Void> retryer = RetryerBuilder.<Void>newBuilder()
                .retryIfException()
                .withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.SECONDS))
                .withStopStrategy(new MyStopStrategy())
                .build();
        try {
            retryer.call(task);
        } catch (Exception e) {
            System.err.println("still failed after retry." + e.getCause().toString());
        }
    }














}
