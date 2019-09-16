package com.gania.jonh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gania.jonh.util.ResourceUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField idField;

    @FXML
    void submitClicked(ActionEvent event) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String content = objectMapper.writeValueAsString(idField.getText());
            ResourceUtil.getInstance().post("/api/facade/save",content);
            idField.clear();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
