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
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bocai.newnote.R;
import com.bocai.newnote.mvp.model.ConstanTs;
import com.bocai.newnote.mvp.model.bean.Note;
import com.bocai.newnote.mvp.model.db.DatabaseOperator;
import com.bocai.newnote.mvp.model.evenbus.EventCenter;
import com.bocai.newnote.mvp.ui.activity.base.BaseSwipeBackActivity;
import com.bocai.newnote.mvp.ui.adapter.NoteAdapter;
import com.bocai.newnote.mvp.ui.adapter.NoteCategoryAdapter;
import com.bocai.newnote.utils.SPUtils;

import java.util.ArrayList;

import butterknife.Bind;

public class NoteActivity extends BaseSwipeBackActivity implements NoteAdapter.OnLongRecyclerItemClickListener, NoteAdapter.OnRecyclerItemClickListener {

    @Bind(R.id.common_toolbar)
    Toolbar mToolBar;
    @Bind(R.id.note_recycle)
    RecyclerView mRecyclerView;
    @Bind(R.id.note_exception)
    LinearLayout mExeption;

    private NoteAdapter noteAdapter;
    private DatabaseOperator databaseOperator;
    private ArrayList<Note> mListNote;
    private int categoryId;
    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initCategoryList() {
        if (getIntent() != null) {
            categoryId = getIntent().getIntExtra(ConstanTs.CATEGORY_ID, -1);

            categoryName = getIntent().getStringExtra(ConstanTs.CATEGORY_NAME);
            SPUtils.put(this, "CATEGORYID", categoryId);
            SPUtils.put(this, "CATEGORYNAME", categoryName);
            if (categoryId != -1) {

                databaseOperator = new DatabaseOperator(this);
                mListNote = new ArrayList<>();
                mListNote = databaseOperator.getNoteList(categoryId);
                noteAdapter = new NoteAdapter(this, mListNote);
                noteAdapter.setOnLongRecyclerItemClick(this);
                noteAdapter.setOnRecyclerItemClick(this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                if (mListNote == null || mListNote.size() == 0) {
                    mExeption.setVisibility(View.VISIBLE);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mRecyclerView.setAdapter(noteAdapter);
                } else {
                    mExeption.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mRecyclerView.setAdapter(noteAdapter);
                }
            }
        } else {
            categoryId = (int) SPUtils.get(this, "CATEGORYID", 1);

            categoryName = (String) SPUtils.get(this, "CATEGORYNAME", "");

            if (categoryId != -1) {

                databaseOperator = new DatabaseOperator(this);
                mListNote = new ArrayList<>();
                mListNote = databaseOperator.getNoteList(categoryId);
                noteAdapter = new NoteAdapter(this, mListNote);
                noteAdapter.setOnLongRecyclerItemClick(this);
                noteAdapter.setOnRecyclerItemClick(this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                if (mListNote == null || mListNote.size() == 0) {
                    mExeption.setVisibility(View.VISIBLE);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mRecyclerView.setAdapter(noteAdapter);
                } else {
                    mExeption.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mRecyclerView.setAdapter(noteAdapter);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCategoryList();
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
        return R.layout.activity_note;
    }

    @Override
    protected void initToolbar() {
        initToolBar(mToolBar);
        mToolBar.setTitle("便签");
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
                startActivity(new Intent(NoteActivity.this, NoteDetailActivity.class).
                        putExtra(ConstanTs.MODE, ConstanTs.CREATE_MODE)
                );
                return true;
        }
        return super.onOptionsItemSelected(item);
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
    public void onLongRecyclerItemClick(View view, int position) {
        new AlertDialog.Builder(this).setTitle("你确定要删除便签吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (databaseOperator.getNoteList(categoryId) != null && databaseOperator.getNoteList(categoryId).size() != 0) {
                            int id = databaseOperator.getNoteList(categoryId).get(position).getId();
                            databaseOperator.delNote(id);
                            setData();
                        }
                    }
                }).setNegativeButton("取消", null).show();
    }

    private void setData() {
        mListNote.clear();
        mListNote.addAll(databaseOperator.getNoteList(categoryId));
        noteAdapter = new NoteAdapter(this, mListNote);
        mRecyclerView.setAdapter(noteAdapter);
        noteAdapter.setOnLongRecyclerItemClick(this);
        noteAdapter.setOnRecyclerItemClick(this);
    }

    @Override
    public void onRecyclerItemClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ConstanTs.NOTEINFO, databaseOperator.getNoteList(categoryId).get(position));
        startActivity(new Intent(NoteActivity.this, NoteDetailActivity.class).
                putExtra(ConstanTs.MODE, ConstanTs.VIEW_MODE).
                putExtras(bundle).
                putExtra(ConstanTs.CATEGORY_NAME, categoryName)
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
