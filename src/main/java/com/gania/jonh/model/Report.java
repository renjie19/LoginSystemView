package com.gania.jonh.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Report {
    private int employeeId;
    private TimeLog timeInLog;
    private TimeLog timeOutLog;
    private Double totalHours;
    private String date;

    public TimeLog getTimeInLog() {
        return timeInLog;
    }

    public void setTimeInLog(TimeLog timeInLog) {
        this.timeInLog = timeInLog;
        setDate(timeInLog.getTime());
    }

    public void setDate(Long time) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.date = dateFormat.format(new Date(time));
    }

    public TimeLog getTimeOutLog() {
        return timeOutLog;
    }

    public void setTimeOutLog(TimeLog timeOutLog) {
        this.timeOutLog = timeOutLog;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getDate() {
        return date;
    }

    public Double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Double totalHours) {
        this.totalHours = totalHours;
    }
}
