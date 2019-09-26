package com.gania.jonh.report.controller;

import com.gania.jonh.Editable;
import com.gania.jonh.Refreshable;
import com.gania.jonh.employee.model.Employee;
import com.gania.jonh.report.ReportResourceController;
import com.gania.jonh.report.model.Report;
import com.gania.jonh.util.AlertDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ReportController implements Initializable,Refreshable {
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
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, String> employeeNameColumn;
    private Employee currentEmployee;

    @FXML
    void onEmployeeColumnClick(MouseEvent event) {
        int index = employeeTable.getSelectionModel().getSelectedIndex();
        currentEmployee = employeeTable.getItems().get(index);
        employeeNameField.setText(currentEmployee.getName());
        addDataToReportTable(new ArrayList<>());
    }

    @FXML
    void onSearchClick(ActionEvent event) {
        try{
            List<Report> reports = new ReportResourceController().getReports(currentEmployee.getEmployeeId(),
                    startDateField.getValue().toString(),endDateField.getValue().toString());
            addDataToReportTable(reports);
        }catch (Exception e) {
            AlertDialog.getInstance().showAlert(e.getMessage());
        }
    }

    @FXML
    void onItemClick(MouseEvent event) {
        if(reportTable.getSelectionModel().getSelectedIndex()>=0) {
            int index = reportTable.getSelectionModel().getSelectedIndex();
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/TimeLog.fxml"));
                AnchorPane anchorPane = loader.load();
                Editable timeLogController = loader.getController();
                timeLogController.setParameters(this,reportTable.getItems().get(index));
                Stage stage = new Stage();
                stage.setScene(new Scene(anchorPane));
                stage.show();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void refresh(ActionEvent event,Object report) {
        onSearchClick(event);
        employeeNameField.requestFocus();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Employee> employees = new ReportResourceController().getAllEmployees();
        ObservableList<Employee> employeeList = FXCollections.observableArrayList(employees);
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeeTable.setItems(employeeList);
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
        overAllHours.setText(String.format("%.2f",totalHours));
    }
}
