package com.gania.jonh.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.swing.text.html.parser.Entity;

public class Server {
    private final String url = "http://localhost:8080";
    private static final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";

    public String getRequest(String urlRequest) {
       try{
           HttpClient client = HttpClientBuilder.create().build();
           HttpGet request = new HttpGet(url+urlRequest);
           request.setHeader("Content-Type", APPLICATION_JSON_UTF8);
           HttpResponse response = client.execute(request);
           String result = EntityUtils.toString(response.getEntity(), "UTF-8");
           return result;
       }catch (Exception e) {
           e.printStackTrace();
       }
       return null;
    }

    public String postRquest(String urlRequest, String content) {
        try{
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(url+urlRequest);
            post.setHeader("Content-Type", APPLICATION_JSON_UTF8);
            post.setEntity(new StringEntity(content,"UTF-8"));
            HttpResponse response = client.execute(post);
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
