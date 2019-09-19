package com.gania.jonh.employee.controller;

import com.gania.jonh.Editable;
import com.gania.jonh.Refreshable;
import com.gania.jonh.employee.EmployeeResourceController;
import com.gania.jonh.employee.model.Employee;
import com.gania.jonh.subject.controller.SubjectController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ViewEmployeeController implements Initializable, Refreshable {
    private Employee currentEmployee;
    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, String> employeeNameColumn;
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
    void onDeleteClick(ActionEvent event) {
        if(currentEmployee != null ) {
            try{
                new EmployeeResourceController().deleteEmployee(currentEmployee);
                loadTableData();
            }catch (Exception e) {
                e.printStackTrace();
            }
            fillInFields(null);
        }
    }

    @FXML
    void onUpdateClick(ActionEvent event) {
        if(currentEmployee != null) {
            currentEmployee.setName(nameField.getText());
            currentEmployee.setAge(Integer.parseInt(ageField.getText()));
            currentEmployee.setAddress(addressField.getText());
            currentEmployee.setPosition(positionField.getText());
            currentEmployee.getLicense().setLicenseNumber(Integer.parseInt(licenseField.getText()));
            currentEmployee.setLicense(currentEmployee.getLicense());
            new EmployeeResourceController().updateEmployee(currentEmployee);
            loadTableData();
            fillInFields(null);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTableData();
    }

    @FXML
    void onCellClick(MouseEvent event) {
        if(employeeTable.getSelectionModel().getSelectedIndex()>=0){
            fillInFields(null);
            int index = employeeTable.getSelectionModel().getSelectedIndex();
            currentEmployee = employeeTable.getItems().get(index);
            fillInFields(currentEmployee);
        }
    }

    @FXML
    void onSectionClick(ActionEvent event) {
        if(currentEmployee != null) {
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/Section.fxml"));
                AnchorPane anchorPane = loader.load();
                Editable sectionController = loader.getController();
                sectionController.setCurrentController(this);
                sectionController.setData(currentEmployee.getSectionList());
                Stage stage = new Stage();
                stage.setScene(new Scene(anchorPane));
                stage.show();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void onSubjectClick(ActionEvent event) {
        if(currentEmployee != null) {
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/Subject.fxml"));
                AnchorPane anchorPane = loader.load();
                Editable subjectController = loader.getController();
                subjectController.setCurrentController(this);
                subjectController.setData(currentEmployee.getSubjectList());
                Stage stage = new Stage();
                stage.setScene(new Scene(anchorPane));
                stage.show();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void fillInFields(Employee employee) {
        if(employee==null) {
            idField.clear();
            nameField.clear();
            ageField.clear();
            addressField.clear();
            positionField.clear();
            licenseField.clear();
        } else {
            idField.setText(String.valueOf(employee.getEmployeeId()));
            nameField.setText(employee.getName());
            ageField.setText(String.valueOf(employee.getAge()));
            addressField.setText(employee.getAddress());
            positionField.setText(employee.getPosition());
            licenseField.setText(String.valueOf(employee.getLicense().getLicenseNumber()));
        }
    }

    private void loadTableData() {
        List<Employee> employees = new EmployeeResourceController().getAll();
        ObservableList<Employee> employeeList = FXCollections.observableArrayList(employees);
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        employeeTable.setItems(employeeList);
    }

    @Override
    public void refresh(ActionEvent event, List list,Class type) {
        if(type.equals(SubjectController.class)){
            currentEmployee.setSubjectList(list);
        }else{
            currentEmployee.setSectionList(list);
        }
        onUpdateClick(event);
        fillInFields(currentEmployee);

    }
}
