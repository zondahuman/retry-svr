package com.abin.lee.retry.api.service;

import com.abin.lee.retry.api.util.RetryHttpException;

/**
 * Created by abin on 2018/6/27.
 */
public interface RetryService {

    void retryCall(String taskName);

    void costTimeOut(String taskName) throws InterruptedException, RetryHttpException;
    void costNoTimeOut(String taskName) throws InterruptedException;















}
