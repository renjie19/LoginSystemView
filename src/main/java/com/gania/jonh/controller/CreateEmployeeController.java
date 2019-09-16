package com.gania.jonh.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gania.jonh.LoginSystem;
import com.gania.jonh.model.Employee;
import com.gania.jonh.model.License;
import com.gania.jonh.util.ResourceUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CreateEmployeeController {
    private ObjectMapper objectMapper;
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
            objectMapper = getObjectMapper();
            Employee employee = new Employee();
            employee.setName(employeeNameField.getText());
            employee.setAge(Integer.parseInt(ageField.getText()));
            employee.setAddress(addressField.getText());
            employee.setPosition(positionField.getText());
            if(!licenseField.getText().equals("")) {
                License license = new License();
                license.setLicenseNumber(Integer.parseInt(licenseField.getText()));
                String content = objectMapper.writeValueAsString(license);
                String result = ResourceUtil.getInstance().post("/api/license/save",content);
                license = objectMapper.readValue(result,License.class);
                employee.setLicense(license);
            }
            String employeeJson = objectMapper.writeValueAsString(employee);
            String employeeResult = ResourceUtil.getInstance().post("/api/employee/save",employeeJson);
            employee = objectMapper.readValue(employeeResult,Employee.class);
            employeeClearClicked(new ActionEvent());
            main = new LoginSystem();
            main.showLogin();
            alertBox("Added Success","Welcome "+employee.getName(),"Employee Id : "+employee.getEmployeeId());
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

    private ObjectMapper getObjectMapper() {
        if(objectMapper==null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }
}
