package com.gania.jonh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gania.jonh.model.Employee;
import com.gania.jonh.model.License;
import com.gania.jonh.model.Section;
import com.gania.jonh.model.Subject;
import com.gania.jonh.util.ResourceUtil;
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

public class ViewEmployeeController implements Initializable {
    private ObjectMapper objectMapper;
    private Employee currentEmployee;
    @FXML
    private TableView<Employee> tableView;
    @FXML
    private TableColumn<Employee, String> employeeName;
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
        try{
            Map<String,String> map = new HashMap<>();
            map.put("id",String.valueOf(currentEmployee.getEmployeeId()));
            String result = ResourceUtil.getInstance().delete("/api/employee/deleteEmployee",map);
            loadTableData();
        }catch (Exception e) {
            e.printStackTrace();
        }
        fillInFields(null);
    }

    @FXML
    void updateClicked(ActionEvent event) {
        try {
            currentEmployee.setName(nameField.getText());
            currentEmployee.setAge(Integer.parseInt(ageField.getText()));
            currentEmployee.setAddress(addressField.getText());
            currentEmployee.setPosition(positionField.getText());
            currentEmployee.getLicense().setLicenseNumber(Integer.parseInt(licenseField.getText()));
            currentEmployee.setLicense(saveLicense(currentEmployee.getLicense()));
            String content = getObjectMapper().writeValueAsString(currentEmployee);
            ResourceUtil.getInstance().post("/api/employee/update", content);
            loadTableData();
            fillInFields(null);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
           loadTableData();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onCellClick(MouseEvent event) {
        if(tableView.getSelectionModel().getSelectedIndex()>=0){
            fillInFields(null);
            int index = tableView.getSelectionModel().getSelectedIndex();
            currentEmployee = tableView.getItems().get(index);
            fillInFields(currentEmployee);
        }
    }

    @FXML
    void sectionClick(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/Section.fxml"));
            AnchorPane anchorPane = loader.load();
            SectionController sectionController = loader.getController();
            sectionController.setViewEmployeeController(this);
            sectionController.setSectionList(currentEmployee.getSectionList());
            Stage stage = new Stage();
            stage.setScene(new Scene(anchorPane));
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void subjectClick(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/Subject.fxml"));
            AnchorPane anchorPane = loader.load();
            SubjectController subjectController = loader.getController();
            subjectController.setViewEmployeeController(this);
            subjectController.setSubjectList(currentEmployee.getSubjectList());
            Stage stage = new Stage();
            stage.setScene(new Scene(anchorPane));
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private License saveLicense(License license) {
        try{
            objectMapper = getObjectMapper();
            String content = objectMapper.writeValueAsString(license);
            String result = ResourceUtil.getInstance().post("/api/license/update",content);
            return objectMapper.readValue(result,License.class);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    private void loadTableData() throws IOException{
        objectMapper = getObjectMapper();
        String result = ResourceUtil.getInstance().getAllRequest("/api/employee/getAll");
        List<Employee> employeeList1 = Arrays.asList(objectMapper.readValue(result, Employee[].class));
        ObservableList<Employee> employeeList = FXCollections.observableArrayList(employeeList1);
        employeeName.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        tableView.setItems(employeeList);
    }

    private ObjectMapper getObjectMapper() {
        if(objectMapper==null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

    public void setEmployeeSubjects(List<Subject> subjectList) {
        currentEmployee.setSubjectList(subjectList);
    }

    public void setEmployeeSections(List<Section> sectionList) {
        currentEmployee.setSectionList(sectionList);
    }
}
