package com.gania.jonh.report.model;

import com.gania.jonh.timelog.model.TimeLog;

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
    }

    public void setDate(String date) {
        this.date = date;
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
