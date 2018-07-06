package com.abin.lee.retry.api.service.impl;

import com.abin.lee.retry.api.service.RetryService;
import org.springframework.stereotype.Service;

/**
 * Created by abin on 2018/6/27.
 */
@Service
public class RetryServiceImpl implements RetryService {

    @Override
    public void retryCall(String taskName) {

    }

    @Override
    public void costRecord(String taskName) throws InterruptedException {
        Thread.sleep(5000L);
    }



}
