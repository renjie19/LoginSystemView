package com.gania.jonh.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gania.jonh.LoginSystem;
import com.gania.jonh.model.Employee;
import com.gania.jonh.model.License;
import com.gania.jonh.util.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

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
        try {
            Employee employee = new Employee();
            employee.setName(employeeNameField.getText());
            employee.setAge(Integer.parseInt(ageField.getText()));
            employee.setAddress(addressField.getText());
            employee.setPosition(positionField.getText());

            License license = new License();
            license.setLicenseNumber(Integer.parseInt(licenseField.getText()));
            ObjectMapper objectMapper = new ObjectMapper();
            String content = objectMapper.writeValueAsString(license);
            Server server = new Server();
            String result = server.postRquest("/api/license/save",content);
            license = objectMapper.readValue(result,License.class);

            employee.setLicense(license);
            content = objectMapper.writeValueAsString(employee);
            String employeeResult = server.postRquest("/api/employee/save",content);
            employee = objectMapper.readValue(employeeResult,Employee.class);
            employeeClearClicked(new ActionEvent());
            alertBox("Added Success","Welcome "+employee.getName(),"Employee Id : "+employee.getEmployeeId());
            main = new LoginSystem();
            main.showLogin();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void alertBox(String title,String header,String context) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);

        alert.showAndWait();
    }
}
