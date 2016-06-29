package com.bocai.newnote.mvp.ui.view;

public interface BaseView {

    void readyGoThenKill(Class clazz);
    void kill();
    void showSnackBar(String msg);
}
