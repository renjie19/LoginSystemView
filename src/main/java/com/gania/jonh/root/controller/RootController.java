package com.gania.jonh.root.controller;

import com.gania.jonh.LoginSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class RootController {

    @FXML
    void onAddEmployeeClick(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/CreateEmployee.fxml"));
            AnchorPane anchorPane = loader.load();
            LoginSystem.getRoot().setCenter(anchorPane);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onEmployeeListClick(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/ViewEmployees.fxml"));
            AnchorPane anchorPane = loader.load();
            LoginSystem.getRoot().setCenter(anchorPane);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onReportClick(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/Report.fxml"));
            AnchorPane anchorPane = loader.load();
            LoginSystem.getRoot().setCenter(anchorPane);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onLoginClick(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/Login.fxml"));
            AnchorPane anchorPane = loader.load();
            LoginSystem.getRoot().setCenter(anchorPane);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
