package com.csy.ddapp.uitls;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/1/18.
 */
public class HttpUtil {


    /**
     * Post请求
     * @param url
     * @param params
     * @return
     */
    public static String post(String url, Map<String, Object> params) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        if (params != null) {
            List<NameValuePair> pairList = new ArrayList<>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            httpPost.addHeader("content-type", "application/json");
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
        }

        return execute(client, httpPost);
    }

    /**
     * Post请求
     * @param url
     * @return
     */
    public static String post(String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        return execute(client, httpPost);
    }

    /**
     * Get请求
     * @param url
     * @return
     */
    public static String get(String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        return execute(client, httpGet);
    }

    private static String execute(CloseableHttpClient client, HttpRequestBase request) {
        String strResult = null;
        CloseableHttpResponse response = null;
        try {
            response = client.execute(request);
            HttpEntity entity = response.getEntity();
            strResult = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(request != null) {
                request.releaseConnection();
            }
        }
        return strResult;
    }
}
