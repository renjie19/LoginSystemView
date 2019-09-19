package com.gania.jonh.login.controller;

import com.gania.jonh.login.LoginResourceController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField idField;

    @FXML
    void onSubmitClick(ActionEvent event) {
        if(!idField.getText().isEmpty()) {
            new LoginResourceController().login(Integer.parseInt(idField.getText()));
            idField.clear();
        }
    }
}
