package com.launch;


import org.apache.http.HttpResponse;

import java.util.Map;

public abstract class Launcher {



    public static class bulider{

        private String httpMethod;

        private String url;

        private Map<String, String> params;

        private String charSet;

        private Class type;


        public bulider setHttpMethod(String httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public bulider setUrl(String url) {
            this.url = url;
            return this;
        }

        public bulider setParams(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public bulider setCharSet(String charSet) {
            this.charSet = charSet;
            return this;
        }

        public  String bulid(){
            if (httpMethod.equalsIgnoreCase(HttpMethod.GET)){
                return HttpTemplate.doGet(this.url,this.params,this.charSet);
            }
            if (httpMethod.equalsIgnoreCase(HttpMethod.POST)){
                return HttpTemplate.doPost(this.url,this.params,this.charSet);
            }
            return null;
        }

        public <T> T jsonObject(Class<T> type) {
            if (httpMethod.equalsIgnoreCase(HttpMethod.GET)) {
                return HttpTemplate.getReObj(type, this.url, this.params, this.charSet);
            }
            if (httpMethod.equalsIgnoreCase(HttpMethod.POST)) {
                return HttpTemplate.postReObj(type, this.url, this.params, this.charSet);
            }
            return null;
        }

        public HttpResponse fullResponse() {
            if (httpMethod.equalsIgnoreCase(HttpMethod.GET)) {
                return HttpTemplate.getReResponse(this.url, this.params, this.charSet);
            }
            if (httpMethod.equalsIgnoreCase(HttpMethod.POST)) {
                return HttpTemplate.postReResponse(this.url, this.params, this.charSet);
            }
            return null;
        }

    }

    public static bulider bulider(){
        return new bulider();
    }


}