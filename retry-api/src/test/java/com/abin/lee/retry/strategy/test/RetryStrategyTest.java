package com.abin.lee.retry.strategy.test;

import com.github.rholder.retry.*;
import com.google.common.base.Predicates;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by abin on 2018/6/27.
 * https://blog.csdn.net/aitangyong/article/details/53894997
 */
public class RetryStrategyTest {
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS");


    @Test
    public void testRetry1() throws InterruptedException {

        Retryer<String> retryer = RetryerBuilder.<String>newBuilder()
                .retryIfResult(Predicates.equalTo("success"))
//                .retryIfExceptionOfType(IOException.class)
                .withWaitStrategy(WaitStrategies.exponentialWait(1000, 10, TimeUnit.SECONDS))
                .withStopStrategy(StopStrategies.stopAfterAttempt(5))
                .withAttemptTimeLimiter(AttemptTimeLimiters.fixedTimeLimit(5, TimeUnit.SECONDS))//方法执行时间超过该值，直接抛错
                .withRetryListener(new RetryListener() {
                    @Override
                    public <String> void onRetry(Attempt<String> attempt) {
                        long AttemptNumber = attempt.getAttemptNumber();
                        long DelaySinceFirstAttempt = attempt.getDelaySinceFirstAttempt();
                        String result = null;
//                        if(null != attempt.getResult())
//                            result = attempt.getResult();
                        Throwable exceptionCause = null;
                        if(null != attempt.getExceptionCause())
                            exceptionCause = attempt.getExceptionCause();
                        System.out.println("AttemptNumber=" + AttemptNumber + ",DelaySinceFirstAttempt=" + DelaySinceFirstAttempt + ",result=" + result+ ",exceptionCause=" + exceptionCause);
                    }
                })
                .build();

        System.out.println("begin..." + df.format(new Date()));

        try {
            retryer.call(buildTask());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("end..." + df.format(new Date()));
    }


    private Callable<String> buildTask() {
        return new Callable<String>() {
            private int i = 0;
            @Override
            public String call() throws Exception {
                System.out.println("called");
                i++;
                if (i == 1) {
                    return "success";
                } else {
                    return "success";
                }
            }
        };
    }







}
