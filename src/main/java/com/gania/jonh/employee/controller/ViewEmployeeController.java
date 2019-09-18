package com.gania.jonh.employee.controller;

import com.gania.jonh.Editable;
import com.gania.jonh.Refreshable;
import com.gania.jonh.employee.model.Employee;
import com.gania.jonh.license.model.License;
import com.gania.jonh.subject.controller.SubjectController;
import com.gania.jonh.util.AlertDialog;
import com.gania.jonh.util.JsonMapper;
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

public class ViewEmployeeController implements Initializable, Refreshable {
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
        if(currentEmployee != null ) {
            try{
                Map<String,String> map = new HashMap<>();
                map.put("id",String.valueOf(currentEmployee.getEmployeeId()));
                ResourceUtil.getInstance().delete("/api/employee/deleteEmployee",map);
                loadTableData();
            }catch (Exception e) {
                e.printStackTrace();
            }
            fillInFields(null);
        }
    }

    @FXML
    void updateClicked(ActionEvent event) {
        if(currentEmployee != null) {
            try {
                currentEmployee.setName(nameField.getText());
                currentEmployee.setAge(Integer.parseInt(ageField.getText()));
                currentEmployee.setAddress(addressField.getText());
                currentEmployee.setPosition(positionField.getText());
                currentEmployee.getLicense().setLicenseNumber(Integer.parseInt(licenseField.getText()));
                currentEmployee.setLicense(saveLicense(currentEmployee.getLicense()));
                String content = JsonMapper.getInstance().writeValueAsString(currentEmployee);
                ResourceUtil.getInstance().post("/api/employee/update", content);
                loadTableData();
                fillInFields(null);
            }catch (IOException e) {
                e.printStackTrace();
            }
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
    void subjectClick(ActionEvent event) {
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

    private License saveLicense(License license) {
        try{
            String content = JsonMapper.getInstance().writeValueAsString(license);
            String result = ResourceUtil.getInstance().post("/api/license/update",content);
            return JsonMapper.getInstance().readValue(result,License.class);
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
        String result = ResourceUtil.getInstance().getAllRequest("/api/employee/getAll");
        List<Employee> employeeList1 = Arrays.asList(JsonMapper.getInstance().readValue(result, Employee[].class));
        ObservableList<Employee> employeeList = FXCollections.observableArrayList(employeeList1);
        employeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableView.setItems(employeeList);
    }

    @Override
    public void refresh(ActionEvent event, List list,Class type) {
        if(type.equals(SubjectController.class)){
            currentEmployee.setSubjectList(list);
        }else{
            currentEmployee.setSectionList(list);
        }
        updateClicked(event);
        fillInFields(currentEmployee);

    }
}
