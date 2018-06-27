package com.abin.lee.retry.strategy.test;

import com.github.rholder.retry.*;
import com.google.common.base.Predicates;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by abin on 2018/6/27.
 * guava-retrying重试工具库: 阻塞策略BlockStrategy
 * https://blog.csdn.net/aitangyong/article/details/53894615
 */
public class StrategyRetryListenerTest {
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS");

    public class MyRetryListener<Boolean> implements RetryListener {

        @Override
        public <Boolean> void onRetry(Attempt<Boolean> attempt) {

            // 第几次重试,(注意:第一次重试其实是第一次调用)
            System.out.print("[retry]time=" + attempt.getAttemptNumber());

            // 距离第一次重试的延迟
            System.out.print(",delay=" + attempt.getDelaySinceFirstAttempt());

            // 重试结果: 是异常终止, 还是正常返回
            System.out.print(",hasException=" + attempt.hasException());
            System.out.print(",hasResult=" + attempt.hasResult());

            // 是什么原因导致异常
            if (attempt.hasException()) {
                System.out.print(",causeBy=" + attempt.getExceptionCause().toString());
            } else {
                // 正常返回时的结果
                System.out.print(",result=" + attempt.getResult());
            }

            // bad practice: 增加了额外的异常处理代码
            try {
                Boolean result = attempt.get();
                System.out.print(",rude get=" + result);
            } catch (ExecutionException e) {
                System.err.println("this attempt produce exception." + e.getCause().toString());
            }

            System.out.println();
        }
    }


    private static Callable<Boolean> maySuccessTask = new Callable<Boolean>() {
        private int times = 0;
        @Override
        public Boolean call() throws Exception {
            System.out.println("called");
            times++;

            if (times == 1) {
                throw new NullPointerException();
            } else if (times == 2) {
                return false;
            } else {
                return true;
            }
        }
    };



    @Test
    public void testRetry1() throws InterruptedException {
        // 异常或者返回false都继续重试
        // 每秒重试一次
        // 最多重试5次
        // 允许添加多个RetryListener
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfResult(Predicates.equalTo(false))
                .retryIfException()
                .withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.SECONDS))
                .withStopStrategy(StopStrategies.stopAfterAttempt(5))
                .withRetryListener(new MyRetryListener<>())
                .withRetryListener(new MyRetryListener<>())
                .build();

        try {
            retryer.call(maySuccessTask);
        } catch (Exception e) {
            System.err.println("still failed after retry.");
            e.printStackTrace();
        }
    }











}
