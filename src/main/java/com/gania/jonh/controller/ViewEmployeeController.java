package com.gania.jonh.controller;

import com.gania.jonh.model.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ViewEmployeeController {
    @FXML
    private TableView<Employee> personTable;
    @FXML
    private TableColumn<Employee, String> firstNameColumn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField positionField;
    @FXML
    private TextField licenseField;
    @FXML
    private TextField idField;

    @FXML
    void deleteClicked(ActionEvent event) {
        Integer.parseInt(idField.getText());
        //pass int
    }

    @FXML
    void saveClicked(ActionEvent event) {

    }

    @FXML
    void updateClicked(ActionEvent event) {

    }
}
