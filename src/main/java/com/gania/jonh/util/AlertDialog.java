package com.gania.jonh.util;

import javafx.scene.control.Alert;

public class AlertDialog {
    private static AlertDialog alertDialog;
    private AlertDialog() {

    }

    public static AlertDialog getInstance() {
        if(alertDialog==null) {
            alertDialog = new AlertDialog();
        }
        return alertDialog;
    }

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
