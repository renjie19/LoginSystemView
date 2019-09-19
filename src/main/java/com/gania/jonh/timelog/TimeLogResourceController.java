package com.gania.jonh.timelog;

import com.gania.jonh.timelog.model.TimeLog;
import com.gania.jonh.util.JsonMapper;
import com.gania.jonh.util.ResourceUtil;

import java.io.IOException;

public class TimeLogResourceController {

    private static final String API_TIME_LOG_UPDATE = "/api/timeLog/update";

    public TimeLog createTimeOutLog(TimeLog timeOutLog) {
        try{
            String content = JsonMapper.getInstance().writeValueAsString(timeOutLog);
            String result = ResourceUtil.getInstance().post(API_TIME_LOG_UPDATE,content);
            return JsonMapper.getInstance().readValue(result,TimeLog.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
