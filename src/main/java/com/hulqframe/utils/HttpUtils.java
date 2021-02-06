package com.hulqframe.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class HttpUtils {
    public static String getPost(String url, Map<String,Object> params){
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse closeableHttpResponse;
        StringBuffer str=new StringBuffer("");
        String key="";
        if(params!=null){
            Iterator<String> iterator= params.keySet().iterator();
            while (iterator.hasNext()){
                key=iterator.next();
                str.append(key).append("=").append(params.get(key)).append("&");
            }
            str.deleteCharAt(str.lastIndexOf("&"));
        }
        url=url+"?"+str.toString();
        try {
            HttpPost httpPost = new HttpPost(url);

            httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");

            StringEntity entity = new StringEntity("", ContentType.create("text/json", "UTF-8"));
            entity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(entity);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
            httpPost.setConfig(requestConfig);

            closeableHttpResponse = closeableHttpClient.execute(httpPost);

            int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                //TODO:状态码非200代表没有正常返回,此处处理你的业务
            }

            HttpEntity httpEntity = closeableHttpResponse.getEntity();
            String asset_synchronization = EntityUtils.toString(httpEntity, "UTF-8");
            return asset_synchronization;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getJS(String url){
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse closeableHttpResponse;

        try {
            HttpPost httpPost = new HttpPost(url);

            httpPost.addHeader("Content-Type", "application/javascript;charset=UTF-8");

            StringEntity entity = new StringEntity("", ContentType.create("text/json", "UTF-8"));
            httpPost.setEntity(entity);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
            httpPost.setConfig(requestConfig);

            closeableHttpResponse = closeableHttpClient.execute(httpPost);

            int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                //TODO:状态码非200代表没有正常返回,此处处理你的业务
            }

            HttpEntity httpEntity = closeableHttpResponse.getEntity();
            String asset_synchronization = EntityUtils.toString(httpEntity, "UTF-8");
            return asset_synchronization;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
