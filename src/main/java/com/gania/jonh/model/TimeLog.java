package com.gania.jonh.model;

import com.gania.jonh.LoginView.util.StateEnum;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeLog {
    private int logId;
    private int employeeId;
    private Long time;
    private StateEnum type;

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public StateEnum getType() {
        return type;
    }

    public void setType(StateEnum type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date(time));
    }
}
