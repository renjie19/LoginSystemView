package com.gania.jonh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gania.jonh.model.Section;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SectionController {
    private ViewEmployeeController viewEmployeeController;
    private List<Section> sectionList;
    private ObjectMapper objectMapper;
    private Section currentSection;
    @FXML
    private TableView<Section> sectionTable;
    @FXML
    private TableColumn<Section, String> sectionNameColumn;
    @FXML
    private TableColumn<Section,String>  yearColumn;
    @FXML
    private TextField sectionIdField;
    @FXML
    private TextField sectionNameField;
    @FXML
    private TextField yearField;

    @FXML
    void itemClicked(MouseEvent event) {
        if(sectionTable.getSelectionModel().getSelectedIndex()>=0) {
            int index = sectionTable.getSelectionModel().getSelectedIndex();
            currentSection = sectionTable.getItems().get(index);
            fillSectionFields(currentSection);
        }
    }

    @FXML
    void sectionAdd(ActionEvent event) {
        if(!sectionNameField.getText().isEmpty() && !yearField.getText().isEmpty()) {
            Section section = new Section();
            section.setSectionName(sectionNameField.getText());
            section.setYearLevel(yearField.getText());
            if(!section.getSectionName().equals("")) {
                sectionList.add(section);
            }
            addDataToTable(sectionList);
            sectionNameField.clear();
        }
    }

    @FXML
    void sectionDelete(ActionEvent event) {
        if(currentSection != null) {
            try{
                objectMapper = getObjectMapper();
                String content = objectMapper.writeValueAsString(currentSection);
                Map<String,String> map = new HashMap<>();
                map.put("id",String.valueOf(currentSection.getSectionId()));
                ResourceUtil.getInstance().delete("/api/section/deleteById",map);
                sectionList.remove(currentSection);
                addDataToTable(sectionList);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void sectionSave(ActionEvent event) {
        try{
            List<Section> sections = new ArrayList<>();
            objectMapper = getObjectMapper();
            for(Section section : sectionList) {
                String content = objectMapper.writeValueAsString(section);
                String result = ResourceUtil.getInstance().post("/api/section/save",content);
                Section resultSection = objectMapper.readValue(result,Section.class);
                if(resultSection != null) {
                    sections.add(resultSection);
                }
            }
            viewEmployeeController.setEmployeeSections(sections);
            viewEmployeeController.updateClicked(event);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }catch (IOException e ) {
            e.printStackTrace();
        }
    }

    @FXML
    void clearClicked(ActionEvent event) {
        sectionIdField.clear();
        sectionNameField.clear();
        yearField.clear();
    }

    public void setViewEmployeeController(ViewEmployeeController viewEmployeeController) {
        this.viewEmployeeController = viewEmployeeController;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
        addDataToTable(sectionList);
    }

    private ObjectMapper getObjectMapper() {
        if(objectMapper==null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

    private void addDataToTable(List<Section> sectionList) {
        ObservableList<Section> sectionObservableList = FXCollections.observableArrayList(sectionList);
        sectionNameColumn.setCellValueFactory(new PropertyValueFactory<Section,String>("sectionName"));
        yearColumn.setCellValueFactory((new PropertyValueFactory<Section,String>("yearLevel")));
        sectionTable.setItems(sectionObservableList);
    }

    private void fillSectionFields(Section section) {
        sectionIdField.setText(String.valueOf(section.getSectionId()));
        sectionNameField.setText(section.getSectionName());
        yearField.setText(section.getYearLevel());
    }

}
