package com.gania.jonh.controller;

import com.gania.jonh.model.Report;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeLogController {
    private ReportController reportController;
    private Report report;
    @FXML
    private TextField inField;

    @FXML
    private TextField outField;

    @FXML
    void timelogSaveClick(ActionEvent event) {
        Report
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
}