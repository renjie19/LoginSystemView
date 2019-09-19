package com.gania.jonh.section;

import com.gania.jonh.section.model.Section;
import com.gania.jonh.util.JsonMapper;
import com.gania.jonh.util.ResourceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SectionResourceController {

    private static final String API_SECTION_DELETE_BY_ID = "/api/section/deleteById";
    private static final String API_SECTION_SAVE = "/api/section/save";

    public void deleteSection(int id) {
        Map<String,String> map = new HashMap<>();
        map.put("id",String.valueOf(id));
        ResourceUtil.getInstance().delete(API_SECTION_DELETE_BY_ID,map);
    }

    public Section saveSections(Section section) {
        try{
//            for(Section section : sections) {
//
////                if(resultSection != null) {
////                    sections.add(resultSection);
////                }
//            }
            String content = JsonMapper.getInstance().writeValueAsString(section);
            String result = ResourceUtil.getInstance().post(API_SECTION_SAVE,content);
            return JsonMapper.getInstance().readValue(result,Section.class);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}