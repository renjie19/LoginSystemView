package com.gania.jonh;

import javafx.event.ActionEvent;


public interface Refreshable<T> {
    void refresh(ActionEvent event,T object );
}
