package com.abin.lee.retry.api.service;

/**
 * Created by abin on 2018/6/27.
 */
public interface RetryService {

    void retryCall(String taskName);

    void costTimeOut(String taskName) throws InterruptedException;
    void costNoTimeOut(String taskName) throws InterruptedException;















}
