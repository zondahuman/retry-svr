package com.abin.lee.retry.api.service.impl;

import com.abin.lee.retry.api.service.RetryService;
import com.abin.lee.retry.common.util.DateUtil;
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
        System.out.println("start---Time =" + DateUtil.getYMDHMSTime() + " , taskName=" + taskName);
        Thread.sleep(5000L);
        System.out.println("end---Time =" + DateUtil.getYMDHMSTime() + " , taskName=" + taskName);

    }



}
