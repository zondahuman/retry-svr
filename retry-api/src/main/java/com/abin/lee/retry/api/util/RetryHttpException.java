package com.abin.lee.retry.api.util;

/**
 * Created by abin on 2018/7/7.
 */
public class RetryHttpException extends Exception {

    //无参构造方法
    public RetryHttpException() {

        super();
    }

    //有参的构造方法
    public RetryHttpException(String message) {
        super(message);

    }

    // 用指定的详细信息和原因构造一个新的异常
    public RetryHttpException(String message, Throwable cause) {

        super(message, cause);
    }

    //用指定原因构造一个新的异常
    public RetryHttpException(Throwable cause) {

        super(cause);
    }

}