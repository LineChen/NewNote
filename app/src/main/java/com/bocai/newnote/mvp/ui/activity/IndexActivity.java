package com.bocai.newnote.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bocai.newnote.R;
import com.bocai.newnote.databinding.ActivityIndexBinding;
import com.bocai.newnote.mvp.model.ConstanTs;
import com.bocai.newnote.mvp.model.db.DatabaseOperator;
import com.bocai.newnote.mvp.model.evenbus.EventCenter;
import com.bocai.newnote.mvp.presenter.impl.IndexPreImpl;
import com.bocai.newnote.mvp.ui.activity.base.BaseActivity;
import com.bocai.newnote.mvp.ui.adapter.IndexContentAdapter;
import com.bocai.newnote.mvp.ui.view.IndexAView;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

public class IndexActivity extends BaseActivity implements IndexAView {

    private static final int INDEX_REQUEST_CODE = 1;
    private static final int SETTING_REQUEST_CODE = 2;
    private static final int NOTE_REQUEST_CODE = 3;
    private static final int EDIT_SAVE = 1;
    private int SUCCESS = 1;
    @Bind(R.id.common_toolbar)
    Toolbar mToolBar;
    private IndexPreImpl mIndexPre;
    private ActivityIndexBinding mDataBinding;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private int INDEX_EVENT_SUCCESS = 1;
    private DatabaseOperator databaseOperator;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = (ActivityIndexBinding) super.mDataBinding;
        mIndexPre = new IndexPreImpl(this, this, mDataBinding);
        mIndexPre.onCreate(savedInstanceState);
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override protected int getContentView() {
        return R.layout.activity_index;
    }

    @Override protected void initToolbar() {
        super.initToolBar(mToolBar);
    }

    @Override protected boolean isApplyTranslucency() {
        return true;
    }

    @Override protected boolean isApplyButterKnife() {
        return true;
    }

    @Override
    protected boolean isApplyEventBus() {
        return true;
    }

    @Override public void initDrawerToggle() {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDataBinding.drawerLayout, mDataBinding.commonToolbar, 0, 0){
            @Override public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
            @Override public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        mDataBinding.drawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    @Override public void initXViewPager() {
        mDataBinding.content.setOffscreenPageLimit(3);
        IndexContentAdapter indexContentAdapter = new IndexContentAdapter(getSupportFragmentManager());
        mDataBinding.content.setAdapter(indexContentAdapter);
    }

    @Override public void readyGoForResult(Class clazz) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("CREATE_MODE", ConstanTs.CREATE_MODE);
        startActivityForResult(intent, INDEX_REQUEST_CODE);
    }

    @Override
    public void readyGo2ForResult(Class clazz) {
        startActivity(new Intent(IndexActivity.this,clazz).
                putExtra(ConstanTs.MODE,ConstanTs.CREATE_MODE));
    }

    @Override protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mActionBarDrawerToggle != null) {
            mActionBarDrawerToggle.syncState();
        }
    }

    @Override public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mActionBarDrawerToggle != null) {
            mActionBarDrawerToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.setting:
                go2Setting();
                return true;
            case R.id.about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public void go2Setting() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivityForResult(intent, SETTING_REQUEST_CODE);
    }

    @Override
    public void go2Note() {
        //TODO to noteActivity
        databaseOperator = new DatabaseOperator(this);
        if (databaseOperator.getCategoryList() == null || databaseOperator.getCategoryList().size() == 0) {
            new AlertDialog.Builder(this).setTitle("请先添加便签类别！")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(IndexActivity.this, NoteCategoryActivity.class);
                            startActivityForResult(intent, NOTE_REQUEST_CODE);
                        }

                    }).setNegativeButton("取消", null).show();
        }else {
            Intent intent = new Intent(this, NoteCategoryActivity.class);
            startActivityForResult(intent, NOTE_REQUEST_CODE);
        }
    }

    @Override
    public void showSnackBar(String msg) {
        Snackbar.make(mToolBar, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void kill() {
        finish();
    }

    @Override
    protected void on2EventComing(EventCenter eventCenter) {
        if (eventCenter.getEventCode() == ConstanTs.EVEN_BUS.CHANGE_THEME) {
            reload(false);
        }
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INDEX_REQUEST_CODE) {
            if (resultCode == EDIT_SAVE && resultCode == SUCCESS) {
                EventCenter eventCenter = new EventCenter(INDEX_EVENT_SUCCESS, true);
                EventBus.getDefault().post(eventCenter);
            }
        } else if (requestCode == SETTING_REQUEST_CODE) {

        }
    }

    @Override
    public void onBackPressed() {
        if (mIndexPre.onBackPress()) {
            super.onBackPressed();
        }
    }
}
