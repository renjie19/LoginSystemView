    package com.gania.jonh.util;

import javafx.scene.control.Alert;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

    public class ResourceUtil {
    private final String url = "http://localhost:8080";
    private static final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";
    HttpClient client = HttpClientBuilder.create().build();
    private static ResourceUtil resourceUtil;

    private ResourceUtil() {

    }

    public static ResourceUtil getInstance() {
        if(resourceUtil==null) {
            resourceUtil = new ResourceUtil();
        }
        return resourceUtil;
    }

    public String getAllRequest(String urlRequest) {
       try{
           HttpGet request = new HttpGet(url+urlRequest);
           request.setHeader("Content-Type", APPLICATION_JSON_UTF8);
           HttpResponse response = client.execute(request);
           return checkResponse(response);
       }catch (Exception e) {
           e.printStackTrace();
       }
       return null;
    }

    public String post(String urlRequest, String content) {
        try{
            HttpPost post = new HttpPost(url+urlRequest);
            post.setHeader("Content-Type", APPLICATION_JSON_UTF8);
            post.setEntity(new StringEntity(content,"UTF-8"));
            HttpResponse response = client.execute(post);
            return checkResponse(response);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String get(String urlRequest, Map<String,String> map) {
        try {
            HttpGet get = new HttpGet(UriBuilder(urlRequest,map));
            get.setHeader("Content-Type", APPLICATION_JSON_UTF8);
            HttpResponse response = client.execute(get);
            return checkResponse(response);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(String urlRequest, Map<String,String> map) {
        try{
            HttpDelete delete = new HttpDelete(UriBuilder(urlRequest,map));
            delete.setHeader("Content-Type", APPLICATION_JSON_UTF8);
            client.execute(delete);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String checkResponse(HttpResponse response) {
        try{
            if(response.getStatusLine().getStatusCode() != 200) {
                String result = EntityUtils.toString(response.getEntity(),"UTF-8");
                ResourceServerResponse error = JsonMapper.getInstance().readValue(result,ResourceServerResponse.class);
                AlertDialog.getInstance().showAlert(error.getMessage());
            }else{
                return  EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private URI UriBuilder(String urlRequest, Map<String,String> map) {
        try{
            URIBuilder builder = new URIBuilder(url+urlRequest);
            for(Map.Entry<String,String> content : map.entrySet()) {
                builder.addParameter(content.getKey(),content.getValue());
            }
            return builder.build();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
