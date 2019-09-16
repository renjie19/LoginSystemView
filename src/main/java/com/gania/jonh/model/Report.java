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
    private String timeIn;
    private String timeOut;

    public TimeLog getTimeInLog() {
        return timeInLog;
    }

    public void setTimeInLog(TimeLog timeInLog) {
        this.timeInLog = timeInLog;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-HH-dd");
        this.date = dateFormat.format(new Date(timeInLog.getTime()));
        this.timeIn = new SimpleDateFormat("HH:mm:ss").format(new Date(timeInLog.getTime()));
    }

    public TimeLog getTimeOutLog() {
        return timeOutLog;
    }

    public void setTimeOutLog(TimeLog timeOutLog) {
        if(timeOutLog!=null) {
            this.timeOutLog = timeOutLog;
            this.timeOut = new SimpleDateFormat("HH:mm:ss").format(new Date(timeOutLog.getTime()));
        }
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

    public String getTimeIn() {
        return timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }
}
