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
 * guava-retrying重试工具库: 阻塞策略BlockStrategy
 * https://blog.csdn.net/aitangyong/article/details/53894615
 */
public class RetryStrategyBlockTest {
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS");

    private static Callable<Boolean> alwaysExceptionTask = new Callable<Boolean>() {

        @Override
        public Boolean call() throws Exception {
            System.out.println("called");
            throw new NullPointerException();
        }
    };


    public static void main(String[] args) throws Exception {
        Thread retryThread = runInNewThread();
        // 让retryThread先执行
        Thread.sleep(2000);
        retryThread.interrupt();
    }

    // 第一次重试是立刻执行的,无需等待
    private static Thread runInNewThread() {
        Runnable runnable = () -> {
            Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                    .retryIfException()
                    .withBlockStrategy(BlockStrategies.threadSleepStrategy())
                    .withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.SECONDS))
                    .withStopStrategy(StopStrategies.stopAfterAttempt(5))
                    .build();

            try {
                retryer.call(alwaysExceptionTask);
            } catch (Exception e) {
                System.err.println("still failed after retry." + e.getCause().toString());
            }
        };

        Thread retryThread = new Thread(runnable);
        retryThread.start();
        return retryThread;
    }



    @Test
    public void testRetry1() throws InterruptedException {
        Thread retryThread = runInNewThread();

        // 让retryThread先执行
        Thread.sleep(2000);
        retryThread.interrupt();

    }





}
