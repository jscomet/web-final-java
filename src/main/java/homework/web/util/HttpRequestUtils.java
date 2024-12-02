package homework.web.util;

import com.alibaba.fastjson2.JSON;
import lombok.Data;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestUtils {
    @Data
    public static class Params {
        private String url;
        private String contentType;
        private Map<String, Object> params;
        private Map<String, Object> data;
        private List<BasicClientCookie> cookies;
        private List<BasicHeader> headers;
        private HttpVersion httpVersion;
        /**
         * dataEntity 会替代掉 data
         */
        private AbstractHttpEntity dataEntity;
    }

    private static final List<BasicHeader> REQUEST_HEADERS = List.of(
            new BasicHeader(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"),
            new BasicHeader(HttpHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7,ja;q=0.6,zh-TW;q=0.5"),
            new BasicHeader(HttpHeaders.CACHE_CONTROL, "max-age=0"),
            new BasicHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.5060.114 Safari/537.36 Edg/103.0.1264.62")
    );

    public static HttpResponse get(Params params) throws IOException {
        return request(HttpMethod.GET, params);
    }

    public static HttpResponse post(Params params) throws IOException {
        return request(HttpMethod.POST, params);
    }

    public static HttpResponse put(Params params) throws IOException {
        return request(HttpMethod.PUT, params);
    }

    public static HttpResponse patch(Params params) throws IOException {
        return request(HttpMethod.PATCH, params);
    }

    public static HttpResponse request(HttpMethod method, Params params) throws IOException {
        return request(HttpClients.createDefault(), method, params);
    }

    public static HttpResponse request(HttpClient httpClient, HttpMethod method, Params params) throws IOException {
        // 根据参数构建 URL
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(params.getUrl());
        if (params.getParams() != null) {
            for (Map.Entry<String, Object> entry : params.getParams().entrySet()) {
                uriBuilder.queryParam(entry.getKey(), entry.getValue());
            }
        }
        URI uri = uriBuilder.build().toUri();
        // 根据数据构建请求
        HttpEntity entity = new StringEntity("");
        if (params.getDataEntity() != null) {
            entity = params.getDataEntity();
        } else if (params.getData() != null && params.getContentType() != null) {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            for (Map.Entry<String, Object> entry : params.getData().entrySet()) {
                Object value = entry.getValue();
                if (value == null) {
                    value = "null";
                }
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), value.toString()));
            }
            String contentType = params.getContentType().split(";")[0];
            if (ContentType.APPLICATION_FORM_URLENCODED.toString().contains(contentType)) {
                entity = new UrlEncodedFormEntity(nameValuePairs);
            } else if (ContentType.APPLICATION_JSON.toString().contains(contentType)) {
                entity = new StringEntity(JSON.toJSONString(params.getData()), ContentType.APPLICATION_JSON);
            } else {
                entity = new UrlEncodedFormEntity(nameValuePairs);
            }
        }
        HttpRequestBase request;
        if (method.equals(HttpMethod.GET)) {
            request = new HttpGet(uri);
        } else if (method.equals(HttpMethod.POST)) {
            request = new HttpPost(uri);
            ((HttpPost) request).setEntity(entity);
        } else if (method.equals(HttpMethod.PUT)) {
            request = new HttpPut(uri);
            ((HttpPut) request).setEntity(entity);
        } else if (method.equals(HttpMethod.PATCH)) {
            request = new HttpPatch(uri);
            ((HttpPatch) request).setEntity(entity);
        } else if (method.equals(HttpMethod.OPTIONS)) {
            request = new HttpOptions(uri);
        } else if (method.equals(HttpMethod.HEAD)) {
            request = new HttpHead(uri);
        } else {
            throw new IOException("错误的请求方法");
        }
        // 设置默认请求头部
        for (BasicHeader header : REQUEST_HEADERS) {
            request.setHeader(header.getName(), header.getValue());
        }
        // 设置请求头部
        if (params.getHeaders() != null) {
            for (BasicHeader header : params.getHeaders()) {
                request.setHeader(header.getName(), header.getValue());
            }
        }
        // 设置请求 Cookie
        if (params.getCookies() != null) {
            String cookieStr = params.getCookies().stream()
                    .map(cookie -> cookie.getName() + "=" + cookie.getValue())
                    .collect(Collectors.joining("; "));
            request.setHeader("Cookie", cookieStr);
        }
        // 设置内容类型
        if (params.getContentType() != null && !params.getContentType().isBlank()) {
            request.setHeader(HttpHeaders.CONTENT_TYPE, params.getContentType());
        }
        // 设置 HTTP 版本
        if (params.getHttpVersion() != null) {
            request.setProtocolVersion(params.getHttpVersion());
        }
        return httpClient.execute(request);
    }
}