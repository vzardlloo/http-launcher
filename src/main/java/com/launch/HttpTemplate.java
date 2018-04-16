package com.launch;


import com.alibaba.fastjson.JSON;

import com.launch.kit.StringKit;
import com.sun.deploy.util.SessionState;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.Closeable;
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
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = (HttpGet) initRequestParam("Get", url, param, charset);
            response = httpClient.execute(httpGet);
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
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String doPost(String url, Map<String, String> params, String charset) {
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = (HttpPost) initRequestParam("Post", url, params, charset);
            response = httpClient.execute(httpPost);
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
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return null;
    }


    public static <T> T doGet(Class<T> type, String url, Map<String, String> param, String charset) {
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = (HttpGet) initRequestParam("Get", url, param, charset);
            response = httpClient.execute(httpGet);
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
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static <T> T doPost(Class<T> type, String url, Map<String, String> params, String charset) {
        CloseableHttpResponse response = null;
        try {
            HttpPost httpPost = (HttpPost) initRequestParam("Post", url, params, charset);
            response = httpClient.execute(httpPost);
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
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static HttpResponse getResponse(String url, Map<String, String> param, String charset) {
        CloseableHttpResponse httpResponse = null;
        try {
            HttpGet httpGet = (HttpGet) initRequestParam("Get", url, param, charset);
            httpResponse = httpClient.execute(httpGet);
            return httpResponse;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static HttpResponse postResponse(String url, Map<String, String> param, String charset) {
        CloseableHttpResponse httpResponse = null;
        try {
            HttpPost httpPost = (HttpPost) initRequestParam("Post", url, param, charset);
            httpResponse = httpClient.execute(httpPost);
            return httpResponse;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static HttpRequestBase initRequestParam(String methodName, String url, Map<String, String> params, String charset) {
        logger.info(url);
        if (!StringKit.isNotBlank(url)) {
            return null;
        }
        if (null == charset) {
            charset = DEFAULT_CHARSET;
        }
        List<NameValuePair> pairList = null;
        if (params != null && !params.isEmpty()) {
            pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String value = entry.getValue();
                if (value != null) {
                    pairList.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }
        }

        if ("Get".equalsIgnoreCase(methodName)) {
            try {

                if (pairList != null) {
                    url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairList, charset));
                }
                logger.info(url);
                HttpGet httpGet = new HttpGet(url);
                return httpGet;
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if ("Post".equalsIgnoreCase(methodName)) {
            try {
                HttpPost httpPost = new HttpPost(url);
                if (pairList != null && !pairList.isEmpty()) {
                    httpPost.setEntity(new UrlEncodedFormEntity(pairList));
                }
                return httpPost;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

}