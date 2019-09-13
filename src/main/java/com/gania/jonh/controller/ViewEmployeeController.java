package com.gania.jonh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gania.jonh.model.Employee;
import com.gania.jonh.util.Server;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ViewEmployeeController implements Initializable {
    private ObjectMapper objectMapper;
    @FXML
    private TableView<Employee> TableView;

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
        Integer.parseInt(idField.getText());
        //pass int
    }

    @FXML
    void saveClicked(ActionEvent event) {

    }

    @FXML
    void updateClicked(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            objectMapper = new ObjectMapper();
            Server server = new Server();
            String result = server.getRequest("/api/employee/getAll");
            List<Employee> employeeList1 = Arrays.asList(objectMapper.readValue(result, Employee[].class));
            ObservableList<Employee> employeeList = FXCollections.observableArrayList(employeeList1);
            employeeName.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
            TableView.setItems(employeeList);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    private ObjectMapper getObjectMapper() {
        if(objectMapper==null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }
}
