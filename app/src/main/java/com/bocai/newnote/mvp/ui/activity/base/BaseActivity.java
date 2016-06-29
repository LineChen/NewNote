package com.bocai.newnote.mvp.ui.activity.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.WindowManager;

import com.bocai.newnote.R;
import com.bocai.newnote.mvp.model.evenbus.EventCenter;
import com.bocai.newnote.utils.ThemeUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


public abstract class BaseActivity extends Base {

    protected ViewDataBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initTheme();
        super.onCreate(savedInstanceState);

        if (isApplyTranslucency()) initWindow();
        mDataBinding = DataBindingUtil.setContentView(this, getContentView());
        if (isApplyButterKnife()) ButterKnife.bind(this);
        initToolbar();
        if (isApplyEventBus()) {
            EventBus.getDefault().register(BaseActivity.this);
        }
    }

    private void initTheme() {
        ThemeUtils.Theme currentTheme = ThemeUtils.getCurrentTheme(this);
        ThemeUtils.changeTheme(this, currentTheme);
    }

    protected void initToolBar(Toolbar toolbar) {

        if (toolbar == null) return;

        toolbar.setBackgroundColor(getColorPrimary());
        toolbar.setTitle(getString(com.bocai.newnote.R.string.app_name));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void reload(boolean anim) {
        Intent intent = getIntent();
        if (!anim) {
            overridePendingTransition(0, 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        }
        finish();
        if (!anim) {
            overridePendingTransition(0, 0);
        }
        startActivity(intent);
    }

    /**
     * api大于19的时候，实现沉浸式状态栏
     */
    @TargetApi(19)
    protected void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getStatusBarColor());
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    //配合 初始化主题设置主体颜色
    protected int getStatusBarColor() {
        return getColorPrimary();
    }

    private int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        MobclickAgent.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (isApplyButterKnife()) ButterKnife.unbind(this);
        if (isApplyEventBus()) EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    //把通过onEventMainThread 接收消息
    public void onEventMainThread(EventCenter eventCenter) {
        if (eventCenter != null) {
            on2EventComing(eventCenter);
        }
    }

    protected abstract void on2EventComing(EventCenter eventCenter);

    protected abstract int getContentView();

    protected abstract void initToolbar();

    protected abstract boolean isApplyTranslucency();

    protected abstract boolean isApplyButterKnife();

    protected abstract boolean isApplyEventBus();
}
