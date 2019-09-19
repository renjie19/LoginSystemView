package com.gania.jonh.subject;

import com.gania.jonh.subject.model.Subject;
import com.gania.jonh.util.ResourceUtil;

import java.util.HashMap;
import java.util.Map;

public class SubjectResourceController {
    public void delete(Subject subject) {
        Map<String,String> map = new HashMap<>();
        map.put("id",String.valueOf(subject.getId()));
        ResourceUtil.getInstance().delete("/api/subject/deleteById",map);
    }
}
