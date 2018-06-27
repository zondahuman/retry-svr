package com.abin.lee.retry.strategy.test;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.google.common.base.Predicates;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * Created by abin on 2018/6/27.
 * guava-retrying重试工具库: 什么时候重试
 * https://blog.csdn.net/aitangyong/article/details/53886293
 */
public class RetryStrategyRetryTest {
    private static Callable<Void> runtimeExceptionTask = new Callable<Void>() {
        @Override
        public Void call() throws Exception {
            System.out.println("runtime was called.");
            throw new NullPointerException("runtime");
        }
    };

    private static Callable<Void> checkedExceptionTask = new Callable<Void>() {
        @Override
        public Void call() throws Exception {
            System.out.println("checked was called.");
            throw new IOException("checked");
        }
    };

    private static Callable<Void> errorTask = new Callable<Void>() {
        @Override
        public Void call() throws Exception {
            System.out.println("error was called.");
            throw new ThreadDeath();
        }
    };

    @Test
    public void testRetry1() {
        Retryer<Void> retryer = RetryerBuilder.<Void>newBuilder()
                .retryIfException() // 抛出异常时重试
                .withStopStrategy(StopStrategies.stopAfterAttempt(3)) // 重试3次后停止
                .build();
        try {
            retryer.call(checkedExceptionTask);
        } catch (Exception e) {

        }
        try {
            retryer.call(runtimeExceptionTask);
        } catch (Exception e) {

        }
        try {
            retryer.call(errorTask);
        } catch (Exception e) {

        }
    }

    @Test
    public void testRetry2() {
        Retryer<Void> retryer = RetryerBuilder.<Void>newBuilder()
                .retryIfRuntimeException() // 抛出runtime异常时重试
                .withStopStrategy(StopStrategies.stopAfterAttempt(3)) // 重试3次后停止
                .build();
        try {
            retryer.call(checkedExceptionTask);
        } catch (Exception e) {

        }
        try {
            retryer.call(runtimeExceptionTask);
        } catch (Exception e) {

        }
        try {
            retryer.call(errorTask);
        } catch (Exception e) {

        }
    }


    @Test
    public void testRetry3() {
        Retryer<Void> retryer = RetryerBuilder.<Void>newBuilder()
                .retryIfExceptionOfType(Error.class)// 只在抛出error重试
                .withStopStrategy(StopStrategies.stopAfterAttempt(3)) // 重试3次后停止
                .build();
        try {
            retryer.call(errorTask);
        } catch (Exception e) {

        }
        try {
            retryer.call(runtimeExceptionTask);
        } catch (Exception e) {

        }
        try {
            retryer.call(checkedExceptionTask);
        } catch (Exception e) {

        }
    }


    private static Callable<Void> nullPointerExceptionTask = new Callable<Void>() {
        @Override
        public Void call() throws Exception {
            System.out.println("nullPointerExceptionTask was called.");
            throw new NullPointerException();
        }
    };

    private static Callable<Void> illegalStateExceptionTask = new Callable<Void>() {
        @Override
        public Void call() throws Exception {
            System.out.println("illegalStateExceptionTask was called.");
            throw new IllegalStateException();
        }
    };

    @Test
    public void testRetry4() {
        Retryer<Void> retryer = RetryerBuilder.<Void>newBuilder()
                .retryIfExceptionOfType(IllegalStateException.class)
                .withStopStrategy(StopStrategies.stopAfterAttempt(3)) // 重试3次后停止
                .build();
        try {
            retryer.call(nullPointerExceptionTask);
        } catch (Exception e) {

        }
        try {
            retryer.call(illegalStateExceptionTask);
        } catch (Exception e) {

        }
    }


    @Test
    public void testRetry5() {
        Retryer<Void> retryer = RetryerBuilder.<Void>newBuilder()
                .retryIfExceptionOfType(IllegalStateException.class)
                .retryIfExceptionOfType(NullPointerException.class)
                .withStopStrategy(StopStrategies.stopAfterAttempt(3)) // 重试3次后停止
                .build();
        try {
            retryer.call(nullPointerExceptionTask);
        } catch (Exception e) {

        }
        try {
            retryer.call(illegalStateExceptionTask);
        } catch (Exception e) {

        }

    }

    @Test
    public void testRetry6() {
        Retryer<Void> retryer1 = RetryerBuilder.<Void>newBuilder()
                .retryIfException(Predicates.or(Predicates.instanceOf(NullPointerException.class),
                        Predicates.instanceOf(IllegalStateException.class)))
                .withStopStrategy(StopStrategies.stopAfterAttempt(3)) // 重试3次后停止
                .build();
        try {
            retryer1.call(nullPointerExceptionTask);
        } catch (Exception e) {

        }
        try {
            retryer1.call(illegalStateExceptionTask);
        } catch (Exception e) {

        }
    }


    // 第一次返回false,第二处返回true
    private static Callable<Boolean> booleanTask = new Callable<Boolean>() {
        private int count = 0;

        @Override
        public Boolean call() throws Exception {
            System.out.println("booleanTask was called.");
            if (count == 0) {
                count++;
                return false;
            } else {
                return true;
            }
        }
    };

    private static Callable<CharSequence> stringTask = new Callable<CharSequence>() {
        private int count = 0;

        @Override
        public CharSequence call() throws Exception {
            System.out.println("stringTask was called.");
            if (count == 0) {
                count++;
                return UUID.randomUUID() + "_error";
            } else {
                return UUID.randomUUID() + "_success";
            }
        }
    };


    @Test
    public void testRetry7() {
    // 返回false重试
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfResult(Predicates.equalTo(false))
                .withStopStrategy(StopStrategies.stopAfterAttempt(3)) // 重试3次后停止
                .build();
        try {
            retryer.call(booleanTask);
        } catch (Exception e) {

        }
        System.out.println();
        // 以_error结尾才重试
        Retryer<CharSequence> retryer1 = RetryerBuilder.<CharSequence>newBuilder()
                .retryIfResult(Predicates.containsPattern("_error$"))
                .withStopStrategy(StopStrategies.stopAfterAttempt(3)) // 重试3次后停止
                .build();
        try {
            retryer1.call(stringTask);
        } catch (Exception e) {

        }
    }


}
