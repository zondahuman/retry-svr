package com.abin.lee.retry.api.util;

import com.github.rholder.retry.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created by abin on 2018/6/27.
 * https://blog.csdn.net/aitangyong/article/details/53894997
 */
public class RetryStrategy {

    public Retryer<Boolean> proxyBoolean() throws Exception {
        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
//                .retryIfResult(Predicates.equalTo(false))
                .retryIfResult(result -> Objects.equals(result, false))
                .retryIfExceptionOfType(IOException.class)
//                .withWaitStrategy(WaitStrategies.exponentialWait(1000, 30, TimeUnit.SECONDS))//multiplier单位固定是ms，maximumTime最大等待时间
                .withWaitStrategy(WaitStrategies.fixedWait(4, TimeUnit.SECONDS))//multiplier单位固定是ms，maximumTime最大等待时间
                .withStopStrategy(StopStrategies.stopAfterAttempt(4))
                .withAttemptTimeLimiter(AttemptTimeLimiters.fixedTimeLimit(5, TimeUnit.SECONDS))//方法执行时间超过该值，直接抛错
                .withRetryListener(new RetryListener() {
                    @Override
                    public <Boolean> void onRetry(Attempt<Boolean> attempt) {
                        long AttemptNumber = attempt.getAttemptNumber();
                        long DelaySinceFirstAttempt = attempt.getDelaySinceFirstAttempt();
                        Boolean result = null;
                        if (null != attempt.getResult())
                            result = attempt.getResult();
                        Throwable exceptionCause = null;
//                        if(null != attempt.getExceptionCause())
//                            exceptionCause = attempt.getExceptionCause();
                        System.out.println("AttemptNumber=" + AttemptNumber + ",DelaySinceFirstAttempt=" + DelaySinceFirstAttempt + ",result=" + result + ",exceptionCause=" + exceptionCause);
                    }
                })
                .build();
        return retryer;
    }


    public Retryer<String> proxyString() throws Exception {
        Retryer<String> retryer = RetryerBuilder.<String>newBuilder()
//                .retryIfResult(Predicates.equalTo(false))
                .retryIfResult(result -> StringUtils.equals(result, "FAILURE"))
                .retryIfExceptionOfType(IOException.class)
//                .withWaitStrategy(WaitStrategies.exponentialWait(1000, 30, TimeUnit.SECONDS))//multiplier单位固定是ms，maximumTime最大等待时间
                .withWaitStrategy(WaitStrategies.fixedWait(4, TimeUnit.SECONDS))//multiplier单位固定是ms，maximumTime最大等待时间
                .withStopStrategy(StopStrategies.stopAfterAttempt(4))
                .withAttemptTimeLimiter(AttemptTimeLimiters.fixedTimeLimit(5, TimeUnit.SECONDS))//方法执行时间超过该值，直接抛错
                .withRetryListener(new RetryListener() {
                    @Override
                    public <String> void onRetry(Attempt<String> attempt) {
                        long AttemptNumber = attempt.getAttemptNumber();
                        long DelaySinceFirstAttempt = attempt.getDelaySinceFirstAttempt();
                        String result = null;
                        if (null != attempt.getResult())
                            result = attempt.getResult();
                        Throwable exceptionCause = null;
//                        if(null != attempt.getExceptionCause())
//                            exceptionCause = attempt.getExceptionCause();
                        System.out.println("AttemptNumber=" + AttemptNumber + ",DelaySinceFirstAttempt=" + DelaySinceFirstAttempt + ",result=" + result + ",exceptionCause=" + exceptionCause);
                    }
                })
                .build();
        return retryer;
    }


}
