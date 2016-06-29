package com.bocai.newnote.mvp.ui.view;

public interface CreateLockAView extends BaseView{

    void initLockPatternView();

    void lockDisplayError();

    void setResults(int isSuccess);

    void clearPattern();
}
