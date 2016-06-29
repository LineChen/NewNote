package com.bocai.newnote.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.bocai.newnote.R;
import com.bocai.newnote.mvp.model.evenbus.EventCenter;
import com.bocai.newnote.mvp.ui.activity.base.BaseSwipeBackActivity;
import com.bocai.newnote.widget.BrowserLayout;

import butterknife.Bind;

public class WebViewActivity extends BaseSwipeBackActivity {

    @Bind(R.id.common_toolbar)
    Toolbar mToolBar;
    @Bind(R.id.brower)
    BrowserLayout mBrowser;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            String url = bundle.getString("URL");
            mBrowser.loadUrl(url);
        }

    }

    @Override protected void on2EventComing(EventCenter eventCenter) {

    }

    @Override protected int getContentView() {
        return R.layout.activity_web_view;
    }

    @Override protected void initToolbar() {
        initToolBar(mToolBar);
        mToolBar.setTitle("项目源码");
    }

    @Override
    public void onBackPressed() {
        if (mBrowser.canGoBack()) {
            mBrowser.getWebView().goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override protected boolean isApplyTranslucency() {
        return true;
    }

    @Override protected boolean isApplyButterKnife() {
        return true;
    }

    @Override protected boolean isApplyEventBus() {
        return false;
    }

    @Override protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override protected boolean toggleOverridePendingTransition() {
        return false;
    }
}
