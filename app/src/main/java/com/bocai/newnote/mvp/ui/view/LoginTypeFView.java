package com.bocai.newnote.mvp.ui.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public interface LoginTypeFView {

    void initRecycler(LinearLayoutManager linearLayoutManager, RecyclerView.Adapter adapter);
    void readGo(Class clazz, int type, int position, int positionType);
    void hideException();
    void showException();
}
