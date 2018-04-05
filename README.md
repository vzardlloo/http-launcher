### ⚡ http-launcher
##### 简介
这是一个简单的Http请求发送的小的封装库,采用流式API风格来发起Http请求，支持以字符串形式和指定对象的形式来获Http响应。

##### 依赖
`fastjson`,`httpclient`,`lombok`

##### 使用
返回字符串
```java
String result = Launcher.bulider()
                .setCharSet("utf-8")  //指定字符编码，默认是utf-8
                .setHttpMethod("GET") //指定Http方法
                .setUrl("http://www.baidu.com") //指定url
                .setParams(param) //指定请求参数
                .bulid();
```
返回对象
```java
 YourObject result = Launcher.bulider()
                .setCharSet("utf-8")  //指定字符编码，默认是utf-8
                .setHttpMethod("GET") //指定Http方法
                .setUrl("http://www.baidu.com") //指定url
                .setParams(param) //指定请求参数
                .jsonObject(YourObject.class);
```