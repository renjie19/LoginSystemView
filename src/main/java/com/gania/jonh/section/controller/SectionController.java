package com.gania.jonh.section.controller;

import com.gania.jonh.Editable;
import com.gania.jonh.Refreshable;
import com.gania.jonh.employee.controller.ViewEmployeeController;
import com.gania.jonh.employee.model.Employee;
import com.gania.jonh.section.SectionResourceController;
import com.gania.jonh.section.model.Section;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class SectionController implements Editable<ViewEmployeeController, Employee> {
    private Refreshable<Employee> refreshable;
    private Section currentSection;
    private Employee employee;
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
    void onItemClick(MouseEvent event) {
        if(sectionTable.getSelectionModel().getSelectedIndex()>=0) {
            int index = sectionTable.getSelectionModel().getSelectedIndex();
            currentSection = sectionTable.getItems().get(index);
            fillSectionFields(currentSection);
        }
    }

    @FXML
    void onSectionAddClick(ActionEvent event) {
        if(!sectionNameField.getText().isEmpty() && !yearField.getText().isEmpty() && !sectionIdField.getText().isEmpty()){
            currentSection.setSectionName(sectionNameField.getText());
            currentSection.setYearLevel(yearField.getText());
            sectionIdField.clear();
            sectionNameField.clear();
            yearField.clear();
            sectionSave(event,currentSection);
        }else if(sectionIdField.getText().isEmpty() && !sectionNameField.getText().isEmpty() && !yearField.getText().isEmpty()) {
            Section section = new Section();
            section.setSectionName(sectionNameField.getText());
            section.setYearLevel(yearField.getText());
            employee.getSectionList().add(section);
            addDataToTable(employee.getSectionList());
            sectionNameField.clear();
            yearField.clear();
            sectionSave(event,section);
        }
    }

    @FXML
    void onSectionDeleteClick(ActionEvent event) {
        if(currentSection != null) {
            new SectionResourceController().deleteSection(currentSection.getSectionId());
            employee.getSectionList().remove(currentSection);
            addDataToTable(employee.getSectionList());
            onClearClick(event);
        }
    }

    @FXML
    void onClearClick(ActionEvent event) {
        sectionIdField.clear();
        sectionNameField.clear();
        yearField.clear();
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

    private void sectionSave(ActionEvent event,Section section) {
        refreshable.refresh(event,employee);
    }

    @Override
    public void setParameters(ViewEmployeeController controller, Employee object) {
        this.refreshable = controller;
        this.employee = object;
        addDataToTable(employee.getSectionList());
    }
}
