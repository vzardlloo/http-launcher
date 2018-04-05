package com.launch;


public class Application {

    public static void main(String[] args) {
        String result = Launcher.bulider()
                .setHttpMethod("GET")
                .setUrl("http://www.baidu.com")
                .bulid();


        System.out.println(result);
    }

}
