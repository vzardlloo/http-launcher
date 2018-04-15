package com.launch;


import org.apache.http.HttpResponse;
import java.util.ArrayList;
import java.util.Map;


public class Puncher extends Launcher {

    static volatile ArrayList<HttpResponse> resultList = new ArrayList<HttpResponse>();

    public static class newer {

        private String httpMethod;

        private String url;

        private Map<String, String> params;

        private String charSet;

        private Class type;

        private Integer times;


        public String getHttpMethod() {
            return httpMethod;
        }

        public String getUrl() {
            return url;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public String getCharSet() {
            return charSet;
        }

        public Class getType() {
            return type;
        }

        public newer setHttpMethod(String httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public newer setUrl(String url) {
            this.url = url;
            return this;
        }

        public newer setParams(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public newer setCharSet(String charSet) {
            this.charSet = charSet;
            return this;
        }

        public newer setType(Class type) {
            this.type = type;
            return this;
        }

        public newer setTimes(Integer times) {
            this.times = times;
            return this;
        }


        public String feedback() {
            if (null == times || 0 == times) {
                times = 10;
            }
            HttpParams httpParams = HttpParams.builder()
                    .params(getParams())
                    .charSet(getCharSet())
                    .method(getHttpMethod())
                    .url(getUrl())
                    .build();
            for (int i = 0; i < times; i++) {
                HttpResponse httpResponse = HttpService.sendRequest(httpParams);
                resultList.add(httpResponse);
            }
            return resultList.toString();
        }

        public void clear() {
            resultList.clear();
        }


    }


    public static newer newer() {
        return new newer();
    }


}
