package com.gania.jonh;

import javafx.event.ActionEvent;

import java.util.List;

public interface Refreshable {
    void refresh(ActionEvent event, List list,Class type);
}
