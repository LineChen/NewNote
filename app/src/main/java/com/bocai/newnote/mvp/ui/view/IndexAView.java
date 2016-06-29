package com.bocai.newnote.mvp.ui.view;

public interface IndexAView {

    void initDrawerToggle();

    void initXViewPager();

    void readyGoForResult(Class clazz);
    void readyGo2ForResult(Class clazz);

    void go2Setting();

    void go2Note();

    void showSnackBar(String msg);

    void kill();
}
