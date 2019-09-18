package com.gania.jonh.section.controller;

import com.gania.jonh.Editable;
import com.gania.jonh.Refreshable;
import com.gania.jonh.section.model.Section;
import com.gania.jonh.util.JsonMapper;
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

public class SectionController implements Editable {
    private Refreshable refreshable;
    private List<Section> sectionList;
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
        if(!sectionNameField.getText().isEmpty() && !yearField.getText().isEmpty() && !sectionIdField.getText().isEmpty()){
            currentSection.setSectionName(sectionNameField.getText());
            currentSection.setYearLevel(yearField.getText());
            sectionIdField.clear();
            sectionNameField.clear();
            yearField.clear();
        }else if(sectionIdField.getText().isEmpty() && !sectionNameField.getText().isEmpty() && !yearField.getText().isEmpty()) {
            Section section = new Section();
            section.setSectionName(sectionNameField.getText());
            section.setYearLevel(yearField.getText());
            sectionList.add(section);
            addDataToTable(sectionList);
            sectionNameField.clear();
            yearField.clear();
        }
        sectionSave(event);
    }

    @FXML
    void sectionDelete(ActionEvent event) {
        if(currentSection != null) {
            Map<String,String> map = new HashMap<>();
            map.put("id",String.valueOf(currentSection.getSectionId()));
            ResourceUtil.getInstance().delete("/api/section/deleteById",map);
            sectionList.remove(currentSection);
            addDataToTable(sectionList);
        }
    }

    @FXML
    void clearClicked(ActionEvent event) {
        sectionIdField.clear();
        sectionNameField.clear();
        yearField.clear();
    }

    @Override
    public void setCurrentController(Refreshable controller) {
        this.refreshable = controller;
    }

    @Override
    public void setData(List list) {
        this.sectionList = list;
        addDataToTable(list);
    }

    private void addDataToTable(List<Section> sectionList) {
        ObservableList<Section> sectionObservableList = FXCollections.observableArrayList(sectionList);
        sectionNameColumn.setCellValueFactory(new PropertyValueFactory<>("sectionName"));
        yearColumn.setCellValueFactory((new PropertyValueFactory<>("yearLevel")));
        sectionTable.setItems(sectionObservableList);
    }

    private void fillSectionFields(Section section) {
        sectionIdField.setText(String.valueOf(section.getSectionId()));
        sectionNameField.setText(section.getSectionName());
        yearField.setText(section.getYearLevel());
    }

    private void sectionSave(ActionEvent event) {
        try{
            List<Section> sections = new ArrayList<>();
            for(Section section : sectionList) {
                String content = JsonMapper.getInstance().writeValueAsString(section);
                String result = ResourceUtil.getInstance().post("/api/section/save",content);
                Section resultSection = JsonMapper.getInstance().readValue(result,Section.class);
                if(resultSection != null) {
                    sections.add(resultSection);
                }
            }
            refreshable.refresh(event,sections,this.getClass());
        }catch (IOException e ) {
            e.printStackTrace();
        }
    }
}
