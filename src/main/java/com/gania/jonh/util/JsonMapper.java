package com.gania.jonh.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper extends ObjectMapper{
    private static JsonMapper jsonMapper;

    public static JsonMapper getInstance() {
        if(jsonMapper==null) {
            jsonMapper = new JsonMapper();
        }
        return jsonMapper;
    }
}
