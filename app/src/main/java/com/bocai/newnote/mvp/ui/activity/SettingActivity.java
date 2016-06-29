package com.bocai.newnote.mvp.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.bocai.newnote.R;
import com.bocai.newnote.mvp.model.evenbus.EventCenter;
import com.bocai.newnote.mvp.ui.activity.base.BaseSwipeBackActivity;
import com.bocai.newnote.mvp.ui.fragment.SettingFragment;

import butterknife.Bind;

public class SettingActivity extends BaseSwipeBackActivity {

    @Bind(R.id.common_toolbar)
    Toolbar mToolBar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFragment();
    }

    @Override protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }

    @Override protected boolean toggleOverridePendingTransition() {
        return true;
    }

    private void setFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new SettingFragment())
                .commit();
    }

    @Override protected void on2EventComing(EventCenter eventCenter) {

    }

    @Override protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override protected void initToolbar() {
        initToolBar(mToolBar);
        mToolBar.setTitle("设置");
    }

    @Override protected boolean isApplyTranslucency() {
        return true;
    }

    @Override protected boolean isApplyButterKnife() {
        return true;
    }

    @Override protected boolean isApplyEventBus() {
        return true;
    }

}
