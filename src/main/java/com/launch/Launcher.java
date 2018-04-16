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

        /**
         * return http response entity as string
         * @return the string of http response entity
         */
        public String data() {
            if (httpMethod.equalsIgnoreCase(HttpMethod.GET)){
                return HttpTemplate.doGet(this.url,this.params,this.charSet);
            }
            if (httpMethod.equalsIgnoreCase(HttpMethod.POST)){
                return HttpTemplate.doPost(this.url,this.params,this.charSet);
            }
            return null;
        }

        /**
         * return an object which response entity
         * @param type response object type
         * @param <T>  object type
         * @return response object
         */
        public <T> T object(Class<T> type) {
            if (httpMethod.equalsIgnoreCase(HttpMethod.GET)) {
                return HttpTemplate.doGet(type, this.url, this.params, this.charSet);
            }
            if (httpMethod.equalsIgnoreCase(HttpMethod.POST)) {
                return HttpTemplate.doPost(type, this.url, this.params, this.charSet);
            }
            return null;
        }

        /**
         * return full http response
         * @return full http response
         */
        public HttpResponse response() {
            if (httpMethod.equalsIgnoreCase(HttpMethod.GET)) {
                return HttpTemplate.getResponse(this.url, this.params, this.charSet);
            }
            if (httpMethod.equalsIgnoreCase(HttpMethod.POST)) {
                return HttpTemplate.postResponse(this.url, this.params, this.charSet);
            }
            return null;
        }

    }

    public static builder bulider() {
        return new builder();
    }


}