package com.gania.jonh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gania.jonh.model.Subject;
import com.gania.jonh.util.ResourceUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class SubjectController {
    private ViewEmployeeController viewEmployeeController;
    private List<Subject> subjectList;
    private ObjectMapper objectMapper;
    private Subject currentSubject;
    @FXML
    private TableView<Subject> subjectTable;
    @FXML
    private TableColumn<Subject, String> subjectColumn;
    @FXML
    private TextField subjectIdField;
    @FXML
    private TextField subjectNameField;

    @FXML
    void itemClicked(MouseEvent event) {
        if(subjectTable.getSelectionModel().getSelectedIndex()>=0) {
            int index = subjectTable.getSelectionModel().getSelectedIndex();
            currentSubject = subjectTable.getItems().get(index);
            fillInFields(currentSubject);
        }
    }

    @FXML
    void subjectDelete(ActionEvent event) {
        if(currentSubject != null) {
            try{
                objectMapper = getObjectMapper();
                String content = objectMapper.writeValueAsString(currentSubject);
                Map<String,String> map = new HashMap<>();
                map.put("id",String.valueOf(currentSubject.getId()));
                ResourceUtil.getInstance().delete("/api/subject/deleteById",map);
                subjectList.remove(currentSubject);
                addDataToTable(subjectList);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void subjectSave(ActionEvent event) {
        List<Subject> subjects =  new ArrayList<>();
        try{
            for(Subject subject : subjectList) {
                objectMapper = getObjectMapper();
                String content = objectMapper.writeValueAsString(subject);
                String result = ResourceUtil.getInstance().post("/api/subject/save",content);
                Subject resultSubject = objectMapper.readValue(result,Subject.class);
                if(resultSubject != null) {
                    subjects.add(resultSubject);
                }
            }
            viewEmployeeController.setEmployeeSubjects(subjects);
            viewEmployeeController.updateClicked(event);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void subjectAdd(ActionEvent event) {
        if(!subjectIdField.getText().isEmpty() && !subjectNameField.getText().isEmpty()) {
            currentSubject.setSubjectName(subjectNameField.getText());
        }else if(!subjectNameField.getText().isEmpty()) {
            Subject subject = new Subject();
            subject.setSubjectName(subjectNameField.getText());
            subjectList.add(subject);
            addDataToTable(subjectList);
            subjectNameField.clear();
        }
    }

    @FXML
    void clearClicked(ActionEvent event) {
        subjectIdField.clear();
        subjectNameField.clear();
    }

    public void setViewEmployeeController(ViewEmployeeController controller) {
        this.viewEmployeeController = controller;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
        addDataToTable(subjectList);
    }

    private void fillInFields(Subject subject) {
        subjectIdField.setText(String.valueOf(subject.getId()));
        subjectNameField.setText(subject.getSubjectName());
    }

    private void addDataToTable(List<Subject> subjects) {
        ObservableList<Subject> subjectObservableList = FXCollections.observableArrayList(subjects);
        subjectColumn.setCellValueFactory(new PropertyValueFactory<Subject, String>("subjectName"));
        subjectTable.setItems(subjectObservableList);
    }

    private ObjectMapper getObjectMapper() {
        if(objectMapper==null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }
}
