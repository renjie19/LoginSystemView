package com.gania.jonh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gania.jonh.model.Employee;
import com.gania.jonh.model.Report;
import com.gania.jonh.model.TimeLog;
import com.gania.jonh.util.ResourceUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class ReportController {
    private Report currentReport;
    @FXML
    private TextField employeeNameField;
    @FXML
    private DatePicker startDateField;
    @FXML
    private DatePicker endDateField;
    @FXML
    private TableView<Report> reportTable;
    @FXML
    private TableColumn<Report, String> dateColumn;
    @FXML
    private TableColumn<Report, String> timeInColumn;
    @FXML
    private TableColumn<Report, String> timeOutColumn;
    @FXML
    private TableColumn<Report, Double> totalHoursColumn;
    @FXML
    private Label overAllHours;

    @FXML
    void onSearchClick(ActionEvent event) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String,String> map = new HashMap<>();
            map.put("name",employeeNameField.getText());
            String content = ResourceUtil.getInstance().get("/api/employee/getByName",map);
            Employee employee = objectMapper.readValue(content,Employee.class);
            map.clear();
            map.put("id",String.valueOf(employee.getEmployeeId()));
            map.put("startDate",startDateField.getValue().toString());
            map.put("endDate",endDateField.getValue().toString());
            content = ResourceUtil.getInstance().get("/api/report/viewByEmployeeId",map);
            List<Report> reportList =  Arrays.asList(objectMapper.readValue(content, Report[].class));
            addDataToReportTable(reportList);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addDataToReportTable(List<Report> reportList) {
        ObservableList<Report> reportObservableList = FXCollections.observableArrayList(reportList);
        dateColumn.setCellValueFactory(new PropertyValueFactory<Report, String>("date"));
        timeInColumn.setCellValueFactory(new PropertyValueFactory<Report,String>("timeIn"));
        timeOutColumn.setCellValueFactory(new PropertyValueFactory<Report,String>("timeOut"));
        totalHoursColumn.setCellValueFactory(new PropertyValueFactory<Report,Double>("totalHours"));
        reportTable.setItems(reportObservableList);
        double totalHours = 0;
        for(Report results : reportObservableList) {
            totalHours = totalHours + results.getTotalHours();
        }
        overAllHours.setText(String.valueOf(totalHours));
    }
    @FXML
    void itemClicked(MouseEvent event) {
        int index = reportTable.getSelectionModel().getSelectedIndex();
        currentReport = reportTable.getItems().get(index);
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/TimeLog.fxml"));
            AnchorPane anchorPane = loader.load();
            TimeLogController timeLogController = loader.getController();
            timeLogController.setReportController(this);
            timeLogController.setReport(currentReport);
            Stage stage = new Stage();
            stage.setScene(new Scene(anchorPane));
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }



}
