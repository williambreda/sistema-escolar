package br.com.sistemaescolar.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;

import static br.com.sistemaescolar.helpers.BuildHelpers.arrayToMap;
import static br.com.sistemaescolar.helpers.TestUtils.queryParametersComposition;
import static br.com.sistemaescolar.helpers.TestUtils.setPathParameters;

public class Api2POJO implements Serializable {
    private String body;
    private String uri;
    private String[] headers;
    private String[] queryParams;
    private String[] pathParams;
    private String baseUrl;
    private String requestMethod;
    private String endpoint;

    private String setEndpointWithParams(String endpoint, Map<String, String> queryPars) {
        return queryPars != null && !queryPars.isEmpty() ? (String) queryParametersComposition.apply(endpoint, queryPars) : endpoint;
    }

    public URI getURI() {
        var tempEndpoint = this.getEndpoint();

        if (this.getEndpoint() != null && !this.getEndpoint().isBlank()) {
            tempEndpoint = setPathParameters.apply(this.endpoint, arrayToMap.apply(pathParams));
        }

        if (this.getEndpoint() != null && !this.getEndpoint().isBlank()) {
            tempEndpoint = this.setEndpointWithParams(tempEndpoint, arrayToMap.apply(this.getQueryParams()));
        }

        if (this.getEndpoint() != null) {
            return URI.create(this.getBaseUrl()).resolve(tempEndpoint);
        }

        return URI.create(this.getBaseUrl());

    }

    public Api2POJO() {
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public String[] getHeaders() {

//        var a = Arrays.stream(headers).toList();
        return headers;
    }

    public void setQueryParams(String[] queryParams) {
        this.queryParams = queryParams;
    }

    public String[] getQueryParams() {
        return queryParams;
    }

    public void setPathParams(String[] pathParams) {
        this.pathParams = pathParams;
    }

    public String[] getPathParams() {
        return pathParams;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getHeader(String header) {
        var headers = arrayToMap.apply(getHeaders());

        return headers.get(header) != null ? headers.get(header) : "header " + header + " não definido";
    }

    public String getQueryParam(String queryParam) {
        var params = arrayToMap.apply(getQueryParams());

        return params.get(queryParam) != null ? params.get(queryParam) : "query param " + queryParam + " não definido";
    }

    @Override
    public String toString() {
        return Try.of(() -> new ObjectMapper().writeValueAsString(this)).getOrElse("");
    }
}

