package com.gania.jonh.controller;

import com.gania.jonh.model.Report;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ReportController {

    @FXML
    private TextField employeeNameField;

    @FXML
    private DatePicker startDateField;

    @FXML
    private DatePicker endDateField;

    @FXML
    private TableView<Report> tableView;

    @FXML
    private TableColumn<Report, String> dateColumn;

    @FXML
    private TableColumn<Report, String> timeInColumn;

    @FXML
    private TableColumn<Report, String> timeOutColumn;

    @FXML
    private TableColumn<Report, Integer> totalHoursColumn;

    @FXML
    void onSearchClick(ActionEvent event) {
        employeeNameField.getText();
        startDateField.getValue();
        endDateField.getValue();
    }

}
