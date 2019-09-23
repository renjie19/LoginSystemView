package com.gania.jonh.subject.controller;

import com.gania.jonh.Editable;
import com.gania.jonh.Refreshable;
import com.gania.jonh.employee.controller.ViewEmployeeController;
import com.gania.jonh.employee.model.Employee;
import com.gania.jonh.subject.SubjectResourceController;
import com.gania.jonh.subject.model.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.*;

public class SubjectController implements Editable<ViewEmployeeController, Employee> {
    private Refreshable<Employee> refreshable;
    private Subject currentSubject;
    private Employee employee;
    @FXML
    private TableView<Subject> subjectTable;
    @FXML
    private TableColumn<Subject, String> subjectColumn;
    @FXML
    private TextField subjectIdField;
    @FXML
    private TextField subjectNameField;

    @FXML
    void onItemClick(MouseEvent event) {
        if(subjectTable.getSelectionModel().getSelectedIndex()>=0) {
            int index = subjectTable.getSelectionModel().getSelectedIndex();
            currentSubject = subjectTable.getItems().get(index);
            fillInFields(currentSubject);
        }
    }

    @FXML
    void onSubjectRemoveClick(ActionEvent event) {
        if(currentSubject != null) {
            new SubjectResourceController().delete(currentSubject);
            employee.getSubjectList().remove(currentSubject);
            onClearClick(event);
        }
    }

    @FXML
    void onSubjectAddClick(ActionEvent event) {
        if(!subjectIdField.getText().isEmpty() && !subjectNameField.getText().isEmpty()) {
            currentSubject.setSubjectName(subjectNameField.getText());
            subjectNameField.clear();
        }else if(!subjectNameField.getText().isEmpty()) {
            Subject subject = new Subject();
            subject.setSubjectName(subjectNameField.getText());
            employee.getSubjectList().add(subject);
            addDataToTable(employee.getSubjectList());
            subjectNameField.clear();
        }
        subjectSave(event);
    }

    @FXML
    void onClearClick(ActionEvent event) {
        subjectIdField.clear();
        subjectNameField.clear();
    }

    private void fillInFields(Subject subject) {
        subjectIdField.setText(String.valueOf(subject.getId()));
        subjectNameField.setText(subject.getSubjectName());
    }

    private void addDataToTable(List<Subject> subjects) {
        ObservableList<Subject> subjectObservableList = FXCollections.observableArrayList(subjects);
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        subjectTable.setItems(subjectObservableList);
    }

    private void subjectSave(ActionEvent event) {
        refreshable.refresh(event,employee);
    }

    @Override
    public void setParameters(ViewEmployeeController controller, Employee object) {
        this.refreshable = controller;
        this.employee = object;
        addDataToTable(employee.getSubjectList());
    }
}
