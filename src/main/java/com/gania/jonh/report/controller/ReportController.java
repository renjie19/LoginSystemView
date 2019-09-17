package com.gania.jonh.report.controller;

import com.gania.jonh.Editable;
import com.gania.jonh.Refreshable;
import com.gania.jonh.employee.model.Employee;
import com.gania.jonh.report.model.Report;
import com.gania.jonh.util.JsonMapper;
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
import java.text.SimpleDateFormat;
import java.util.*;

public class ReportController implements Refreshable {
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
            Map<String,String> map = new HashMap<>();
            map.put("name",employeeNameField.getText());
            String content = ResourceUtil.getInstance().get("/api/employee/getByName",map);
            Employee employee = JsonMapper.getInstance().readValue(content,Employee.class);
            map.clear();
            map.put("id",String.valueOf(employee.getEmployeeId()));
            map.put("startDate",startDateField.getValue().toString());
            map.put("endDate",endDateField.getValue().toString());
            content = ResourceUtil.getInstance().get("/api/report/viewByEmployeeId",map);
            List<Report> reportList =  Arrays.asList(JsonMapper.getInstance().readValue(content, Report[].class));
            setReportDates(reportList);
            addDataToReportTable(reportList);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void itemClicked(MouseEvent event) {
        if(reportTable.getSelectionModel().getSelectedIndex()>=0) {
            int index = reportTable.getSelectionModel().getSelectedIndex();
            List<Report> reportList = new ArrayList<>();
            reportList.add(reportTable.getItems().get(index));
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/TimeLog.fxml"));
                AnchorPane anchorPane = loader.load();
                Editable timeLogController = loader.getController();
                timeLogController.setCurrentController(this);
                timeLogController.setData(reportList);
                Stage stage = new Stage();
                stage.setScene(new Scene(anchorPane));
                stage.show();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void refresh(ActionEvent event, List list, Class type) {
        onSearchClick(event);
        employeeNameField.requestFocus();
    }

    private void addDataToReportTable(List<Report> reportList) {
        ObservableList<Report> reportObservableList = FXCollections.observableArrayList(reportList);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeInColumn.setCellValueFactory(new PropertyValueFactory<>("timeInLog"));
        timeOutColumn.setCellValueFactory(new PropertyValueFactory<>("timeOutLog"));
        totalHoursColumn.setCellValueFactory(new PropertyValueFactory<>("totalHours"));
        reportTable.setItems(reportObservableList);
        double totalHours = 0;
        for(Report results : reportObservableList) {
            totalHours = totalHours + results.getTotalHours();
        }
        overAllHours.setText(String.valueOf(totalHours));
    }

    private void setReportDates(List<Report> list) {
        for(Report report : list) {
            report.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date(report.getTimeInLog().getTime())));
        }
    }
}
