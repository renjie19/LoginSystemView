package com.gania.jonh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gania.jonh.model.Report;
import com.gania.jonh.model.TimeLog;
import com.gania.jonh.util.ResourceUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeLogController {
    private ReportController reportController;
    private Report report;
    @FXML
    private TextField inField;

    @FXML
    private TextField outField;

    @FXML
    void timeLogSaveClick(ActionEvent event) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long newTimeIn = dateFormat.parse(report.getDate()+" "+inField.getText()).getTime();
            long newTimeOut = dateFormat.parse(report.getDate()+" "+outField.getText()).getTime();
            report.getTimeInLog().setTime(newTimeIn);
            report.getTimeOutLog().setTime(newTimeOut);
            saveNewLogs(report);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.close();
            reportController.onSearchClick(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setReportController(ReportController reportController) {
        this.reportController = reportController;
    }

    public void setReport(Report report) {
        this.report = report;
        String timeIn = new SimpleDateFormat("HH:mm:ss").format(new Date(report.getTimeInLog().getTime()));
        inField.setText(timeIn);
        String timeOut = new SimpleDateFormat("HH:mm:ss").format(new Date(report.getTimeOutLog().getTime()));
        outField.setText(timeOut);
    }

    private void saveNewLogs(Report report) {
        try{
            List<TimeLog> timeLogList = new ArrayList<>();
            timeLogList.add(report.getTimeInLog());
            timeLogList.add(report.getTimeOutLog());
            ObjectMapper objectMapper =  new ObjectMapper();
            for(TimeLog log : timeLogList) {
                String content = objectMapper.writeValueAsString(log);
                ResourceUtil.getInstance().post("/api/timeLog/update",content);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}