package com.launch;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Builder
@Getter
public class HttpParams {

    private String url;

    private String charSet;

    private String method;

    private Map<String, String> params;


}
