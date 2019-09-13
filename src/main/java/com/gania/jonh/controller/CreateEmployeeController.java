package com.gania.jonh.controller;
import com.gania.jonh.LoginSystem;
import com.gania.jonh.model.Employee;
import com.gania.jonh.model.License;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CreateEmployeeController {
    @FXML
    private TextField employeeNameField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField positionField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField licenseField;
    private LoginSystem main;

    @FXML
    void employeeClearClicked(ActionEvent event) {
        employeeNameField.clear();
        ageField.clear();
        positionField.clear();
        addressField.clear();
        licenseField.clear();
    }

    @FXML
    void employeeSaveClicked(ActionEvent event) {
        Employee employee = new Employee();
        employee.setName(employeeNameField.getText());
        employee.setAge(Integer.parseInt(ageField.getText()));
        employee.setAddress(addressField.getText());
        employee.setPosition(positionField.getText());

        License license = new License();
        license.setLicenseNumber(Integer.parseInt(licenseField.getText()));
        //save license first here and get return

        employee.setLicense(license);
        //save employee here and get return;
        main = new LoginSystem();
        main.showLogin();
    }
}
