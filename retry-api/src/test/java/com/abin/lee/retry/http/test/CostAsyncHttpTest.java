package com.abin.lee.retry.http.test;

import com.abin.lee.retry.common.util.AsyncHttpClientUtil;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by abin on 2018/7/6.
 */
public class CostAsyncHttpTest {

    //    private static final String httpURL = "http://localhost:8099/retry/costTimeOut";
    private static final String httpURL = "http://localhost:8099/retry/costNoTimeOut";

    @Test
    public void testCostHttp() {
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
        }
    }

    @Test
    public void testCostHttpCallBack() {
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
            Future<HttpResponse> response = httpClient.execute(httpPost, new HttpCallAsyncResponse());
            System.out.println("----------------------------------------");
//            System.out.println(response.getStatusLine());
//            System.out.println(EntityUtils.toString(response.getEntity()));
            result = EntityUtils.toString(response.get().getEntity());
            System.out.println("async result=================" + result + " ,taskName= " + id);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static class HttpCallAsyncResponse implements FutureCallback<HttpResponse> {
        @Override
        public void completed(HttpResponse result) {
            String resulter = null;
            try {
                resulter = EntityUtils.toString(result.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ;
            System.out.println("HttpCallAsyncResponse--resulter=" + resulter);
        }

        @Override
        public void failed(Exception ex) {
            System.out.println("ex------------------" + ex);
        }

        @Override
        public void cancelled() {
            System.out.println("HttpCallAsyncResponse--cancelled............................................");

        }
    }


}
