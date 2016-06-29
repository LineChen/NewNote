package com.bocai.newnote.mvp.ui.view;


import com.bocai.newnote.mvp.model.bean.God;

import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;

public interface EditAView {

    void initSpinner(List<String> data);
    void initCreateModel();
    void initEditModel();
    void initViewModel(God god, int positionType);
    String getTitleName();
    String getUserName();
    String getPassWord();
    String getMemoInfo();
    void setTime(String time);
    void showSnackToast(String msg);
    void setItemMenuVisible(boolean visible);
    void finishActivity();
    void setPassWordVisible(boolean visible);
    void hideKeyBoard();
    void setToolBarTitle(int resId);
    void showDialog(String msg, String positiveMsg);
    void hideSaveDialog();
    SwipeBackLayout getSwipeBack();
}
