package com.gania.jonh.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gania.jonh.util.JsonMapper;
import com.gania.jonh.util.ResourceUtil;

public class LoginResourceController {

    private static final String API_FACADE_SAVE = "/api/facade/save";

    public void login(int id) {
        try{
            String content = JsonMapper.getInstance().writeValueAsString(id);
            ResourceUtil.getInstance().post(API_FACADE_SAVE,content);
        }catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
