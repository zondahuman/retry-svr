package com.abin.lee.retry.strategy.test;

import com.github.rholder.retry.*;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by abin on 2018/6/27.
 * guava-retrying重试工具库: 隔多长时间重试
 * https://blog.csdn.net/aitangyong/article/details/53889036
 */
public class RetryStrategyIntervalTest {
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS");

    public static class AlwaysExceptionTask implements Callable<Boolean> {
        private int times = 1;
        @Override
        public Boolean call() throws Exception {
            System.out.println(df.format(new Date()));
            int thisTimes = times;
            times++;

            if (thisTimes == 1) {
                throw new NullPointerException();
            } else if (thisTimes == 2) {
                throw new IOException();
            } else if (thisTimes == 3) {
                throw new ArithmeticException();
            } else {
                throw new Exception();
            }
        }
    }

    @Test
    public void testRetry1() {

        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfException()
                .withWaitStrategy(WaitStrategies.noWait())
                .build();

        System.out.println("begin..." + df.format(new Date()));

        try {
            retryer.call(new AlwaysExceptionTask());
        } catch (Exception e) {
            System.err.println("still failed after retry." + e.getCause().toString());
        }

        System.out.println("end..." + df.format(new Date()));
    }

    @Test
    public void testRetry2() {
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfException()
                .withWaitStrategy(WaitStrategies.fixedWait(5, TimeUnit.SECONDS))
                .build();
        System.out.println("begin..." + df.format(new Date()));
        try {
            retryer.call(new AlwaysExceptionTask());
        } catch (Exception e) {
            System.err.println("still failed after retry." + e.getCause().toString());
        }
    }

    @Test
    public void testRetry3() {
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfException()
                .withWaitStrategy(WaitStrategies.randomWait(10, TimeUnit.SECONDS))
                .build();

        System.out.println("begin..." + df.format(new Date()));

        try {
            retryer.call(new AlwaysExceptionTask());
        } catch (Exception e) {
            System.err.println("still failed after retry." + e.getCause().toString());
        }


    }

    @Test
    public void testRetry4() {
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfException()
                .withWaitStrategy(WaitStrategies.randomWait(5, TimeUnit.SECONDS, 10, TimeUnit.SECONDS))
                .build();

        System.out.println("begin..." + df.format(new Date()));

        try {
            retryer.call(new AlwaysExceptionTask());
        } catch (Exception e) {
            System.err.println("still failed after retry." + e.getCause().toString());
        }

        System.out.println("end..." + df.format(new Date()));
    }


    @Test
    public void testRetry5() {
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfException()
                .withWaitStrategy(WaitStrategies.fibonacciWait(100, 10, TimeUnit.SECONDS))
                .build();

        System.out.println("begin..." + df.format(new Date()));

        try {
            retryer.call(new AlwaysExceptionTask());
        } catch (Exception e) {
            System.err.println("still failed after retry." + e.getCause().toString());
        }
    }

    @Test
    public void testRetry6() {
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfException()
                .withWaitStrategy(WaitStrategies.exponentialWait(100, 10, TimeUnit.SECONDS))
                .build();
        System.out.println("begin..." + df.format(new Date()));
        try {
            retryer.call(new AlwaysExceptionTask());
        } catch (Exception e) {
            System.err.println("still failed after retry." + e.getCause().toString());
        }
    }



    @Test
    public void testRetry7() {
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfException()
                .withWaitStrategy(WaitStrategies.exponentialWait(100, 10, TimeUnit.SECONDS))
                .build();
        System.out.println("begin..." + df.format(new Date()));
        try {
            retryer.call(new AlwaysExceptionTask());
        } catch (Exception e) {
            System.err.println("still failed after retry." + e.getCause().toString());
        }
    }



    @Test
    public void testRetry8() {
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfException()
                .withWaitStrategy(WaitStrategies.exponentialWait(100, 10, TimeUnit.SECONDS))
                .build();
        System.out.println("begin..." + df.format(new Date()));
        try {
            retryer.call(new AlwaysExceptionTask());
        } catch (Exception e) {
            System.err.println("still failed after retry." + e.getCause().toString());
        }
    }





}
