package com.launch;


import org.apache.http.HttpResponse;

import java.util.Map;

public abstract class Launcher {


    public static class builder {

        private String httpMethod;

        private String url;

        private Map<String, String> params;

        private String charSet;

        private Class type;


        public builder setHttpMethod(String httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public builder setParams(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public builder setCharSet(String charSet) {
            this.charSet = charSet;
            return this;
        }


        public String data() {
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

    public static builder bulider() {
        return new builder();
    }


}