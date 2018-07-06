package com.abin.lee.retry.http.test;

import com.abin.lee.retry.common.util.HttpClientUtil;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abin on 2018/7/6.
 */
public class CostHttpTest {

    private static final String httpURL = "http://localhost:8099/retry/cost";
//    private static final String httpURL = "http://localhost:8099/retry/cost";

    @Test
    public void testCostHttp() {
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//            nvps.add(new BasicNameValuePair("taskName", "second"));
            int id = (int)(Math.random()*10000000L);
            nvps.add(new BasicNameValuePair("taskName", "" + id));
            HttpPost httpPost = new HttpPost(httpURL);
//            httpPost.setHeader("Cookie", getCookie());
//            httpPost.setHeader("Cookie", "JSESSIONID=7588C522A6900BFD581AA18FDA64D347");

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
            System.out.println("Executing request: " + httpPost.getRequestLine());
            HttpResponse response = httpClient.execute(httpPost);
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            System.out.println(EntityUtils.toString(response.getEntity()));
            String result = EntityUtils.toString(response.getEntity()) ;
            System.out.println("async result=================" + result);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }




}
