package com.gania.jonh.employee.controller;
import com.gania.jonh.LoginSystem;
import com.gania.jonh.employee.model.Employee;
import com.gania.jonh.license.model.License;
import com.gania.jonh.util.JsonMapper;
import com.gania.jonh.util.ResourceUtil;
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
        if(!allRequiredFieldHasValue()) {
            try {
                Employee employee = new Employee();
                employee.setName(employeeNameField.getText());
                employee.setAge(Integer.parseInt(ageField.getText()));
                employee.setAddress(addressField.getText());
                employee.setPosition(positionField.getText());
                if(!licenseField.getText().isEmpty()) {
                    createLicense(employee);
                }
                String content = JsonMapper.getInstance().writeValueAsString(employee);
                String employeeResult = ResourceUtil.getInstance().post("/api/employee/save",content);
                employee = JsonMapper.getInstance().readValue(employeeResult,Employee.class);
                employeeClearClicked(new ActionEvent());
                LoginSystem main = new LoginSystem();
                main.showLogin();
                alertBox("Added Success","Welcome "+employee.getName(),"Employee Id : "+employee.getEmployeeId());
            }catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            alertBox("","Add Failed","Please fill in all fields");
        }
    }

    private Boolean allRequiredFieldHasValue() {
        boolean nameNotEmpty = employeeNameField.getText().isEmpty();
        boolean ageNotEmpty = ageField.getText().isEmpty();
        boolean addressNotEmpty = addressField.getText().isEmpty();
        boolean positionNotEmpty = positionField.getText().isEmpty();
        return nameNotEmpty && ageNotEmpty && addressNotEmpty && positionNotEmpty;
    }

    private void createLicense(Employee employee) {
        try{
            License license = new License();
            license.setLicenseNumber(Integer.parseInt(licenseField.getText()));
            String content = JsonMapper.getInstance().writeValueAsString(license);
            String result = ResourceUtil.getInstance().post("/api/license/save",content);
            license = JsonMapper.getInstance().readValue(result,License.class);
            employee.setLicense(license);
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
