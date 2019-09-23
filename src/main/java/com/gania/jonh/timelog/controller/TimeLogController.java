package com.gania.jonh.timelog.controller;

import com.gania.jonh.Editable;
import com.gania.jonh.LoginView.util.StateEnum;
import com.gania.jonh.Refreshable;
import com.gania.jonh.report.controller.ReportController;
import com.gania.jonh.report.model.Report;
import com.gania.jonh.timelog.TimeLogResourceController;
import com.gania.jonh.timelog.model.TimeLog;
import com.gania.jonh.util.JsonMapper;
import com.gania.jonh.util.ResourceUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeLogController implements Editable<Refreshable<ReportController>,Report> {
    private Refreshable<ReportController> refreshable;
    private Report currentReport;
    @FXML
    private TextField timeInField;
    @FXML
    private TextField timeOutField;
    @FXML
    private Label emptyLabel;

    @FXML
    void onTimeLogSaveClick(ActionEvent event) {
        if(!timeInField.getText().isEmpty() && !timeOutField.getText().isEmpty()) {
            try {
                emptyLabel.setText(null);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                long newTimeIn = dateFormat.parse(currentReport.getDate()+" "+ timeInField.getText()).getTime();
                long newTimeOut = dateFormat.parse(currentReport.getDate()+" "+ timeOutField.getText()).getTime();
                currentReport.getTimeInLog().setTime(newTimeIn);
                if(currentReport.getTimeOutLog()==null) {
                    createTimeOutLog(newTimeOut);
                }
                currentReport.getTimeOutLog().setTime(newTimeOut);
                saveNewLogs(currentReport);
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.close();
                refreshable.refresh(event,null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            emptyLabel.setText("*Fill in all fields");
        }
    }

    private void saveNewLogs(Report report) {
        try{
            List<TimeLog> timeLogList = new ArrayList<>();
            timeLogList.add(report.getTimeInLog());
            timeLogList.add(report.getTimeOutLog());
            for(TimeLog log : timeLogList) {
                String content = JsonMapper.getInstance().writeValueAsString(log);
                ResourceUtil.getInstance().post("/api/timeLog/update",content);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createTimeOutLog(Long timeOut) {
        TimeLog timeLog = new TimeLog();
        timeLog.setEmployeeId(currentReport.getEmployeeId());
        timeLog.setType(StateEnum.OUT);
        timeLog.setTime(timeOut);
        new TimeLogResourceController().createTimeOutLog(timeLog);
    }

    @Override
    public void setParameters(Refreshable<ReportController> controller, Report object) {
        this.refreshable = controller;
        this.currentReport = object;
        String timeIn = new SimpleDateFormat("HH:mm:ss").format(new Date(currentReport.getTimeInLog().getTime()));
        timeInField.setText(timeIn);
        if(currentReport.getTimeOutLog() != null) {
            String timeOut = new SimpleDateFormat("HH:mm:ss").format(new Date(currentReport.getTimeOutLog().getTime()));
            timeOutField.setText(timeOut);
        }
    }
}