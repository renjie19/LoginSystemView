package com.gania.jonh.employee.controller;
import com.gania.jonh.LoginSystem;
import com.gania.jonh.employee.EmployeeResourceController;
import com.gania.jonh.employee.model.Employee;
import com.gania.jonh.license.model.License;
import com.gania.jonh.util.AlertDialog;
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

    @FXML
    void onEmployeeClearClick(ActionEvent event) {
        employeeNameField.clear();
        ageField.clear();
        positionField.clear();
        addressField.clear();
        licenseField.clear();
    }

    @FXML
    void onEmployeeSaveClick(ActionEvent event) {
        if(!allRequiredFieldHasValue()) {
            Employee employee = new Employee();
            employee.setName(employeeNameField.getText());
            employee.setAge(Integer.parseInt(ageField.getText()));
            employee.setAddress(addressField.getText());
            employee.setPosition(positionField.getText());
            if(!licenseField.getText().isEmpty()) {
                License license = new License();
                license.setLicenseNumber(Integer.parseInt(licenseField.getText()));
                employee.setLicense(license);
            }else{
                employee.setLicense(new License());
            }
            EmployeeResourceController employeeResourceController = new EmployeeResourceController();
            employee = employeeResourceController.createEmployee(employee);
            onEmployeeClearClick(event);
            LoginSystem main = new LoginSystem();
            main.showLogin();
            AlertDialog.getInstance().showAlert("Add Success","Employee Id: "+employee.getEmployeeId());
        }else {
            AlertDialog.getInstance().showAlert("Add Failed","Fill in all required fields");
        }
    }

    private Boolean allRequiredFieldHasValue() {
        boolean nameNotEmpty = employeeNameField.getText().isEmpty();
        boolean ageNotEmpty = ageField.getText().isEmpty();
        boolean addressNotEmpty = addressField.getText().isEmpty();
        boolean positionNotEmpty = positionField.getText().isEmpty();
        return nameNotEmpty && ageNotEmpty && addressNotEmpty && positionNotEmpty;
    }
}
