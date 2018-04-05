package com.launch;


import com.alibaba.fastjson.JSON;

import com.launch.kit.StringKit;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpTemplate {

    private static final Logger logger = LoggerFactory.getLogger(HttpTemplate.class);

    private static final CloseableHttpClient httpClient;
    public static final String DEFAULT_CHARSET = "utf-8";

    static {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(6000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }


    public static String doGet(String url, Map<String, String> param, String charset) {
        if (!StringKit.isNotBlank(url)) {
            return null;
        }
        if (null == charset) {
            charset = DEFAULT_CHARSET;
        }
        try {
            List<NameValuePair> pairList = null;
            if (param != null && !param.isEmpty()) {
                pairList = new ArrayList<NameValuePair>(param.size());
                for (Map.Entry<String, String> entry : param.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairList.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            if (pairList != null) {
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairList, charset));
            }
            HttpGet httpGet = new HttpGet(url);

            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (!String.valueOf(statusCode).startsWith("2")) {
                throw new RuntimeException("Http Template Error :: status code " + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, charset);
            }
            EntityUtils.consume(entity);

            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doPost(String url, Map<String, String> params, String charset) {
        if (!StringKit.isNotBlank(url)) {
            return null;
        }
        if (null == charset) {
            charset = DEFAULT_CHARSET;
        }
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(pairs.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            if (pairs != null && !pairs.isEmpty()) {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs));
            }
            HttpResponse response = httpClient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if (!String.valueOf(status).startsWith("2")) {
                throw new RuntimeException("Http Template Error :: status error " + status);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
            EntityUtils.consume(entity);
            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


    public static <T> T getReObj(Class<T> type, String url, Map<String, String> param, String charset) {
        if (!StringKit.isNotBlank(url)) {
            return null;
        }
        if (null == charset) {
            charset = DEFAULT_CHARSET;
        }
        try {
            List<NameValuePair> pairList = null;
            if (param != null && !param.isEmpty()) {
                pairList = new ArrayList<NameValuePair>(param.size());
                for (Map.Entry<String, String> entry : param.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairList.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            if (pairList != null) {
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairList, charset));
            }
            logger.info(url);
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (!String.valueOf(statusCode).startsWith("2")) {
                throw new RuntimeException("Http Template Error :: status code " + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, charset);
            }
            logger.info(result);
            EntityUtils.consume(entity);
            T resultNew = JSON.parseObject(result, type);
            logger.info(resultNew.getClass().toString());
            return resultNew;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T> T postReObj(Class<T> type, String url, Map<String, String> params, String charset) {
        if (!StringKit.isNotBlank(url)) {
            return null;
        }
        if (null == charset) {
            charset = DEFAULT_CHARSET;
        }
        try {
            List<NameValuePair> pairs = null;
            if (params != null && !params.isEmpty()) {
                pairs = new ArrayList<NameValuePair>(pairs.size());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value != null) {
                        pairs.add(new BasicNameValuePair(entry.getKey(), value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            if (pairs != null && !pairs.isEmpty()) {
                httpPost.setEntity(new UrlEncodedFormEntity(pairs));
            }
            HttpResponse response = httpClient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if (!String.valueOf(status).startsWith("2")) {
                throw new RuntimeException("Http Template Error :: status error " + status);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, charset);
            }
            EntityUtils.consume(entity);

            T resultNew = JSON.parseObject(result, type);
            return resultNew;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}