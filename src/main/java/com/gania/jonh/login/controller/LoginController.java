package com.gania.jonh.login.controller;

import com.gania.jonh.util.JsonMapper;
import com.gania.jonh.util.ResourceUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField idField;

    @FXML
    void onSubmitClick(ActionEvent event) {
        if(!idField.getText().isEmpty()) {
            try {
                String content = JsonMapper.getInstance().writeValueAsString(idField.getText());
                ResourceUtil.getInstance().post("/api/facade/save",content);
                idField.clear();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
