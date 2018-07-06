package com.abin.lee.retry.api.service;

/**
 * Created by abin on 2018/6/27.
 */
public interface RetryService {

    void retryCall(String taskName);

    void costRecord(String taskName) throws InterruptedException;















}
