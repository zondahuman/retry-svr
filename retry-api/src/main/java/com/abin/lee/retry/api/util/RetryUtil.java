package com.abin.lee.retry.api.util;

import com.alibaba.fastjson.JSON;
import com.github.rholder.retry.*;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RetryUtil<T> {

  private static int thirdWaitTime = 2;//秒
  private static int thirdRetryTimes = 5;

  public Retryer<T> thirdRetry()
      throws RetryException, ExecutionException {
    return retry(thirdWaitTime, thirdRetryTimes);
  }

  private Retryer<T> retry(int waitTime, int retryTimes)  throws RetryException, ExecutionException {
    return RetryerBuilder.<T>newBuilder()
        .retryIfResult(t -> {
            //如果返回空，直接重试
            String rr = (String)t;
            if(Strings.isNullOrEmpty(rr)){
                return true;
            }
            //如果返回错误code，直接重试
//            ReturnResult r = new ReturnResult();
//            try {
//                log.info(rr);
//                r = JSON.parseObject((String) t, ReturnResult.class);
//                if(r!=null){
//                    return r.getCode() == Constants.ERR_CODE; //只有错误的时候重试
//                }
//            } catch (Exception e) {
//                log.error("决策引擎返回的结果为：{}", t);
//                throw new ServiceException(e.getMessage());
//            }
          return false;
        })
        .retryIfRuntimeException()
        .retryIfException()
        .withAttemptTimeLimiter(AttemptTimeLimiters.noTimeLimit())
        .withWaitStrategy(WaitStrategies.fixedWait(waitTime, TimeUnit.SECONDS))
        .withStopStrategy(StopStrategies.stopAfterAttempt(retryTimes))
        .withRetryListener(new RetryListener() {
          @Override
          public <V> void onRetry(Attempt<V> attempt) {
            log.info("retry times: " + attempt.getAttemptNumber() + ", elapsed time: "
                + attempt.getDelaySinceFirstAttempt() + "ms");
          }
        })
        .build();
  }

}
