package com.abin.lee.retry.api.controller;

import com.abin.lee.retry.api.service.RetryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by abin on 2018/6/27.
 */
@Controller
@RequestMapping("/retry")
@Slf4j
public class RetryController {
    @Autowired
    RetryService retryService;

    @RequestMapping(value = "/flash")
    @ResponseBody
    public String flash(String taskName) {
        String result = "FAILURE";
        try {
            this.retryService.retryCall(taskName);
            result = "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/cost")
    @ResponseBody
    public String cost(String taskName) {
        String result = "FAILURE";
        this.retryService.retryCall(taskName);
        result = "SUCCESS";
        return result;
    }


}
