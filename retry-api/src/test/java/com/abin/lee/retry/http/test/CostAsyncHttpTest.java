package com.abin.lee.retry.http.test;

import com.abin.lee.retry.api.util.RetryHttpException;
import com.abin.lee.retry.common.util.AsyncHttpClientUtil;
import com.abin.lee.retry.common.util.HttpClientUtil;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

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




}
