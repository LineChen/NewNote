package com.bocai.newnote.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bocai.newnote.R;
import com.bocai.newnote.mvp.model.ConstanTs;
import com.bocai.newnote.mvp.model.bean.Category;
import com.bocai.newnote.mvp.model.db.DatabaseOperator;
import com.bocai.newnote.mvp.model.evenbus.EventCenter;
import com.bocai.newnote.mvp.ui.activity.base.BaseSwipeBackActivity;
import com.bocai.newnote.mvp.ui.adapter.NoteCategoryAdapter;

import java.util.ArrayList;

import butterknife.Bind;

public class NoteCategoryActivity extends BaseSwipeBackActivity implements
        NoteCategoryAdapter.OnLongRecyclerItemClickListener, NoteCategoryAdapter.OnRecyclerItemClickListener {

    @Bind(R.id.common_toolbar)
    Toolbar mToolBar;
    @Bind(R.id.cate_recycle)
    RecyclerView mRecyclerView;
    @Bind(R.id.cate_exception)
    LinearLayout mExeption;

    private NoteCategoryAdapter noteCategoryAdapter;
    private DatabaseOperator databaseOperator;
    private ArrayList<Category> mListCategory;
    private EditText et;
    private IBinder ib = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCategoryList();
    }

    private void initCategoryList() {
        databaseOperator = new DatabaseOperator(this);
        mListCategory = new ArrayList<>();
        mListCategory = databaseOperator.getCategoryList();
        noteCategoryAdapter = new NoteCategoryAdapter(this, mListCategory);
        noteCategoryAdapter.setOnLongRecyclerItemClick(this);
        noteCategoryAdapter.setOnRecyclerItemClick(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        if (mListCategory == null || mListCategory.size() == 0) {
            mExeption.setVisibility(View.VISIBLE);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setAdapter(noteCategoryAdapter);
        } else {
            mExeption.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setAdapter(noteCategoryAdapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                addNoteCategory();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addNoteCategory() {
        et = new EditText(this);

        new AlertDialog.Builder(this).setTitle("新建便签类别")
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!"".equals(et.getText().toString())) {
                            addCategory(et.getText().toString());
                        } else {
                            Toast.makeText(getApplicationContext(), "无效名称",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                }).setNegativeButton("取消", null).show();
    }

    private void addCategory(String name) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            ib = et.getWindowToken();
            imm.hideSoftInputFromWindow(ib, 0);
        }
        Category category = new Category();
        category.setCategoryName(name);
        databaseOperator.addCategory(category);
        setData();
        mExeption.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void setData() {
        mListCategory.clear();
        mListCategory.addAll(databaseOperator.getCategoryList());
        noteCategoryAdapter = new NoteCategoryAdapter(this,mListCategory);
        mRecyclerView.setAdapter(noteCategoryAdapter);
        noteCategoryAdapter.setOnLongRecyclerItemClick(this);
        noteCategoryAdapter.setOnRecyclerItemClick(this);
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    protected void on2EventComing(EventCenter eventCenter) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_category_note;
    }

    @Override
    protected void initToolbar() {
        initToolBar(mToolBar);
        mToolBar.setTitle("便签类别");
    }

    @Override
    protected boolean isApplyTranslucency() {
        return true;
    }

    @Override
    protected boolean isApplyButterKnife() {
        return true;
    }

    @Override
    protected boolean isApplyEventBus() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLongRecyclerItemClick(View view, int position) {
        new AlertDialog.Builder(this).setTitle("你确定要删除此类别吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (databaseOperator.getCategoryList() != null && databaseOperator.getCategoryList().size() != 0) {
                            int id = databaseOperator.getCategoryList().get(position).getId();
                            databaseOperator.delCategory(id);
                            setData();
                        }
                    }

                }).setNegativeButton("取消", null).show();
    }

    @Override
    public void onRecyclerItemClick(View view, int position) {
        startActivity(new Intent(this,NoteActivity.class).
                putExtra(ConstanTs.CATEGORY_ID,databaseOperator.getCategoryList().get(position).getId()).
                putExtra(ConstanTs.CATEGORY_NAME,databaseOperator.getCategoryList().get(position).getCategoryName()));
    }


}
