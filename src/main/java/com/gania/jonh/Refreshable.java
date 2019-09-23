package com.gania.jonh;

import javafx.event.ActionEvent;

import java.util.List;

public interface Refreshable<T> {
    void refresh(ActionEvent event,T object );
}
