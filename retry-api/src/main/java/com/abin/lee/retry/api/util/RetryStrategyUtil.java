package com.abin.lee.retry.api.util;

import com.github.rholder.retry.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by abin on 2018/6/27.
 * https://blog.csdn.net/aitangyong/article/details/53894997
 */
public class RetryStrategyUtil {
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS");

    public static void main(String[] args) throws Exception {

        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
//                .retryIfResult(Predicates.equalTo(false))
                .retryIfResult(result -> Objects.equals(result, false))
                .retryIfExceptionOfType(IOException.class)
//                .withWaitStrategy(WaitStrategies.exponentialWait(1000, 30, TimeUnit.SECONDS))//multiplier单位固定是ms，maximumTime最大等待时间
                .withWaitStrategy(WaitStrategies.fixedWait(3, TimeUnit.SECONDS))//multiplier单位固定是ms，maximumTime最大等待时间
                .withStopStrategy(StopStrategies.stopAfterAttempt(5))
                .withAttemptTimeLimiter(AttemptTimeLimiters.fixedTimeLimit(5, TimeUnit.SECONDS))//方法执行时间超过该值，直接抛错
                .withRetryListener(new RetryListener() {
                    @Override
                    public <Boolean> void onRetry(Attempt<Boolean> attempt) {
                        long AttemptNumber = attempt.getAttemptNumber();
                        long DelaySinceFirstAttempt = attempt.getDelaySinceFirstAttempt();
                        Boolean result = null;
                        if(null != attempt.getResult())
                            result = attempt.getResult();
                        Throwable exceptionCause = null;
//                        if(null != attempt.getExceptionCause())
//                            exceptionCause = attempt.getExceptionCause();
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

    private static Callable<Boolean> buildTask() {
        return new Callable<Boolean>() {
            private int i = 0;
             @Override
            public Boolean call() throws Exception {
                System.out.println("called");
                i++;
                if (i == 1) {
                    return Boolean.TRUE;
                } else {
                    return Boolean.TRUE;
                }
            }
        };
    }







}
