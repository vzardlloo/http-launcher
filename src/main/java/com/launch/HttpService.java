package com.launch;


import org.apache.http.HttpResponse;

public class HttpService {


    public static HttpResponse sendRequest(HttpParams params) {

        return Launcher.bulider()
                .setUrl(params.getUrl())
                .setHttpMethod(params.getMethod())
                .setCharSet(params.getCharSet())
                .setParams(params.getParams())
                .fullResponse();
    }


}
