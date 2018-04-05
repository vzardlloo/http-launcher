package com.launch;


public class Application {

    public static void main(String[] args) {

        String result = Launcher.bulider()
                .setCharSet("utf-8")  //指定字符编码，默认是utf-8
                .setHttpMethod("GET") //指定Http方法
                .setUrl("http://www.baidu.com") //指定url
                .bulid();


        System.out.println(result);
    }

}
