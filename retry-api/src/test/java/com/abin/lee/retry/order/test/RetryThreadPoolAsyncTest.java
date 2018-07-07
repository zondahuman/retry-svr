package com.abin.lee.retry.order.test;

import com.abin.lee.retry.api.util.RetryHttpException;
import com.abin.lee.retry.api.util.RetryStrategy;
import com.abin.lee.retry.common.util.AsyncHttpClientUtil;
import com.abin.lee.retry.common.util.HttpClientUtil;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by abin on 2018/7/6.
 */
public class RetryThreadPoolAsyncTest {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                RetryStrategy retryStrategy = new RetryStrategy();
                try {
                    retryStrategy.proxyString().call(httpTask());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }


    private static Callable<String> httpTask() {
        return new Callable<String>() {
            @Override
            public String call() {
                System.out.println("called");
                String flag = null;
                try {
                    flag = httpCallNoTimeOut();
                } catch (RetryHttpException e) {
                    e.printStackTrace();
                    throw new RuntimeException("-------httptask ---call-=--------------------------");
                }
                return flag;
            }
        };
    }


    private static final String httpURL = "http://localhost:8099/retry/costTimeOut";
//    private static final String httpURL = "http://localhost:8099/retry/costNoTimeOut";

    public static String httpCallNoTimeOut() throws RetryHttpException {
        String result = "";
        int id = (int) (Math.random() * 10000000L);
        try {
            CloseableHttpAsyncClient httpClient = AsyncHttpClientUtil.getHttpAsyncClient();
            httpClient.start();
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//            nvps.add(new BasicNameValuePair("taskName", "second"));
//            int id = (int) (Math.random() * 10000000L);
            nvps.add(new BasicNameValuePair("taskName", "" + id));
            HttpPost httpPost = new HttpPost(httpURL);
//            httpPost.setHeader("Cookie", getCookie());
//            httpPost.setHeader("Cookie", "JSESSIONID=7588C522A6900BFD581AA18FDA64D347");
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
            System.out.println("Executing request: " + httpPost.getRequestLine());
            Future<HttpResponse> response = httpClient.execute(httpPost, null);
            System.out.println("----------------------------------------");
//            System.out.println(response.getStatusLine());
//            System.out.println(EntityUtils.toString(response.getEntity()));
            result = EntityUtils.toString(response.get().getEntity());
            System.out.println("async result=================" + result + " ,taskName= " + id);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RetryHttpException(".................http call exception......................taskName:" + id);
        }
        return result;
    }


    public static String httpCallTimeOut() throws RetryHttpException {
        String result = "";
        int id = (int) (Math.random() * 10000000L);
        try {
            CloseableHttpAsyncClient httpClient = AsyncHttpClientUtil.getHttpAsyncClient();
            httpClient.start();
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//            nvps.add(new BasicNameValuePair("taskName", "second"));
//            int id = (int) (Math.random() * 10000000L);
            nvps.add(new BasicNameValuePair("taskName", "" + id));
            HttpPost httpPost = new HttpPost(httpURL);
//            httpPost.setHeader("Cookie", getCookie());
//            httpPost.setHeader("Cookie", "JSESSIONID=7588C522A6900BFD581AA18FDA64D347");

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
            System.out.println("Executing request: " + httpPost.getRequestLine());
            Future<HttpResponse> response = httpClient.execute(httpPost, null);
            System.out.println("----------------------------------------");
//            System.out.println(response.getStatusLine());
//            System.out.println(EntityUtils.toString(response.getEntity()));
            result = EntityUtils.toString(response.get().getEntity());
            System.out.println("async result=================" + result + " ,taskName= " + id);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RetryHttpException(".................http call exception......................taskName:" + id);
        }
        return result;
    }




}
