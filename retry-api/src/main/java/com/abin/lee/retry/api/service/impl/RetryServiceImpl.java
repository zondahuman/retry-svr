package com.abin.lee.retry.api.service.impl;

import com.abin.lee.retry.api.service.RetryService;
import com.abin.lee.retry.api.util.RetryHttpException;
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
    public void costTimeOut(String taskName) throws InterruptedException, RetryHttpException {
        System.out.println("costTimeOut--start---Time =" + DateUtil.getYMDHMSTime() + " , taskName=" + taskName);
        Thread.sleep(5000L);
        if(true)
            throw new RetryHttpException("this is a exception.................................");
        System.out.println("costTimeOut--end---Time =" + DateUtil.getYMDHMSTime() + " , taskName=" + taskName);

    }

    @Override
    public void costNoTimeOut(String taskName) throws InterruptedException {
        System.out.println("costNoTimeOut--start---Time =" + DateUtil.getYMDHMSTime() + " , taskName=" + taskName);
//        Thread.sleep(5000L);
        System.out.println("costNoTimeOut--end---Time =" + DateUtil.getYMDHMSTime() + " , taskName=" + taskName);

    }


}
