package com.launch;


import org.apache.http.HttpResponse;

public class Application {

    public static void main(String[] args) {

        HttpResponse result = Launcher.bulider()
                .setCharSet("utf-8")  //指定字符编码，默认是utf-8
                .setHttpMethod("GET") //指定Http方法
                .setUrl("http://www.baidu.com") //指定url
                .fullResponse();


        System.out.println(result.getStatusLine().getStatusCode());
    }

}
