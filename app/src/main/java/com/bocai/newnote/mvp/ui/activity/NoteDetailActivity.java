package com.bocai.newnote.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bocai.newnote.R;
import com.bocai.newnote.mvp.model.ConstanTs;
import com.bocai.newnote.mvp.model.bean.Category;
import com.bocai.newnote.mvp.model.bean.Note;
import com.bocai.newnote.mvp.model.db.DatabaseOperator;
import com.bocai.newnote.mvp.model.evenbus.EventCenter;
import com.bocai.newnote.mvp.ui.activity.base.BaseSwipeBackActivity;
import com.bocai.newnote.utils.BitmapUtils;
import com.bocai.newnote.utils.TimeUtils;
import com.bocai.newnote.widget.spinner.NiceSpinner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class NoteDetailActivity extends BaseSwipeBackActivity implements View.OnClickListener {

    @Bind(R.id.common_toolbar)
    Toolbar mToolBar;
    @Bind(R.id.note_category)
    TextView categiry;
    @Bind(R.id.spinner)
    NiceSpinner mSpinner;
    @Bind(R.id.note_title)
    EditText title;
    @Bind(R.id.note_content)
    EditText content;
    @Bind(R.id.note_location)
    EditText location;
    @Bind(R.id.add_image)
    ImageView addImage;
    @Bind(R.id.play)
    ImageView play;
    @Bind(R.id.timeTextView)
    TextView timeTv;
    @Bind(R.id.note_deleteButton)
    Button delete;
    @Bind(R.id.view_linear)
    LinearLayout linearLayout;

    private MenuItem menuItem;
    private int mRequestCode;

    private int mode = -1;
    private Note note;
    private DatabaseOperator databaseOperator;
    private String categoryName;
    private String audioPath = "";
    private String videoPath = "";
    private String localtionName = "";
    private int categaryId = -1;
    private double localtionX = 0.00;
    private double localtionY = 0.00;
    private ArrayList<Category> categoryList;
    private File photoFile;


    private static final int TAKE_PHOTO = 1;
    private static final int GET_PHOTO = 2;
    private static final int GET_VIDEO = 4;
    private String imagepath;
    private boolean isHaveImageoPath = false;
    private boolean isHaveVideoPath = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initListener();
        initView();
    }

    private void initListener() {
        //TODO　百度地图   添加图片  添加视频
        addImage.setOnClickListener(this);
        play.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_image:
                new PopupWindows(NoteDetailActivity.this,linearLayout);
                break;
            case R.id.play:
                Intent ivideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(ivideo, 4);// 开启视频界面
                break;
        }
    }


    public class PopupWindows extends PopupWindow { // 弹出菜单

        public PopupWindows(Context mContext, View parent) {

            View view = View
                    .inflate(mContext, R.layout.item_popupwindows, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.push_bottom_in_2));

            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view
                    .findViewById(R.id.item_popupwindows_camera); // 相机
            Button bt2 = (Button) view
                    .findViewById(R.id.item_popupwindows_Photo); // 图库
            Button bt3 = (Button) view
                    .findViewById(R.id.item_popupwindows_cancel); // 取消
            //相机
            bt1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent icamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用系统相机
                    // 1 获得的SD卡路径+
                    photoFile = new File(Environment.getExternalStorageDirectory()
                            .getAbsoluteFile()
                            + "/"
                            + TimeUtils.getMyTime(System.currentTimeMillis())
                            + ".jpg");
                    // 2 将路径存起来
                    icamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    startActivityForResult(icamera, 1);
                    dismiss();
                }
            });
            //选择图库的按钮的点击事件
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent igallery = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(igallery, 2);
                    dismiss();
                }
            });
            //取消的按钮
            bt3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.mRequestCode = requestCode;
        if (resultCode == RESULT_OK) {
            switch (mRequestCode) {
                case TAKE_PHOTO:
                    // 3 获取路径，将照片展示出来 利用ImageSpan在EditText中展示出来
                    // 4 存到数据库，传入的是存储的地址。。（String类型）
                    BitmapUtils.convertToBitmap(photoFile.getAbsolutePath(),200,400);
                    break;
                case GET_PHOTO:



                    Uri selectedImageUri = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
//                    Cursor cursor = getContentResolver().query(selectedImageUri,
//                            filePathColumn, null, null, null);
//                    assert cursor != null;
//                    cursor.moveToFirst();
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imagepath = filePathColumn[0];// cursor.getString(columnIndex);
                    Toast.makeText(NoteDetailActivity.this, imagepath, Toast.LENGTH_SHORT).show();
                    Bitmap bm = BitmapUtils.convertToBitmap(imagepath, 200, 400);
                    Log.e("===", "bm:" + (bm == null));
                    if (bm != null) {
                        addImage.setImageBitmap(bm);
                    }

                    isHaveImageoPath = true;
//                    cursor.close();
                    break;
                case 3:

                    break;
                case GET_VIDEO:
                    // 将结果再传给AddNoteActivity
                    Uri uri = data.getData();
                    Cursor cursor1 = this.getContentResolver().query(uri, null,
                            null, null, null);
                    if (cursor1 != null && cursor1.moveToNext()) {
                       videoPath = cursor1.getString(cursor1
                                .getColumnIndex(MediaStore.Video.VideoColumns.DATA));
                       isHaveVideoPath = true;
                    }

                    play.setImageResource(R.mipmap.play2);

                    assert cursor1 != null;
                    cursor1.close();
                    break;
                default:
                    break;
            }
        }
    }

    private void initView() {
        databaseOperator = new DatabaseOperator(NoteDetailActivity.this);
        if (getIntent() != null) {
            mode = getIntent().getIntExtra(ConstanTs.MODE, -1);
            if (mode != -1) {
                switch (mode) {
                    case 0:  //  view mode  编辑模式
                        initViewModeNote();
                        break;
                    case 1: //   create mode 添加模式
                        initCreateModeNote();
                        break;
                }
            }
        }
    }


    //添加模式
    private void initCreateModeNote() {
        categiry.setVisibility(View.VISIBLE);
        categiry.setText(categoryName);
        mSpinner.setVisibility(View.GONE);
        if (databaseOperator.getCategoryList() == null || databaseOperator.getCategoryList().size() == 0) {
            Toast.makeText(NoteDetailActivity.this, "你还没有添加类别！", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(NoteDetailActivity.this,NoteCategoryActivity.class));
            finish();
        } else {
            categoryList = databaseOperator.getCategoryList();
            List<String> data = new ArrayList<>();
            for (Category category : categoryList){
                data.add(category.getCategoryName());
            }
            mSpinner.attachDataSource(data);
            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    categaryId = categoryList.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }

    }
    //编辑模式
    private void initViewModeNote() {
        delete.setVisibility(View.VISIBLE);
        categiry.setVisibility(View.VISIBLE);
        timeTv.setVisibility(View.VISIBLE);
        mSpinner.setVisibility(View.GONE);
        note  = (Note) getIntent().getSerializableExtra(ConstanTs.NOTEINFO);
        categoryName = getIntent().getStringExtra(ConstanTs.CATEGORY_NAME);
        categiry.setText(categoryName);
        title.setText(note.getTitle());
        content.setText(note.getContent());
        location.setText(note.getLocaltionName());
        addImage.setImageBitmap(BitmapUtils.convertToBitmap(note.getImagepath(),200,400));
        play.setImageResource(R.mipmap.play2);
        timeTv.setText(TimeUtils.getMyTime(note.getCreatetime()));
        play.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //TODO  跳转到播放视频界面

                return true;
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseOperator.delNote(note.getId());
                finish();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuItem = menu.findItem(0);
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                if (mode == 0) {
                    update();
                    finish();
                } else  if (mode == 1){
                    add();
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void add() {
        Note newNote = new Note(-1,
                categaryId,
                title.getText().toString(),
                content.getText().toString(),
                System.currentTimeMillis(),
                imagepath,
                audioPath,
                videoPath,
                localtionX,
                localtionY,
                localtionName,
                System.currentTimeMillis());
        long l = databaseOperator.addNote(newNote);
        if (l == -1){
            Toast.makeText(NoteDetailActivity.this, "faial", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(NoteDetailActivity.this, "sucssess", Toast.LENGTH_SHORT).show();
        }
    }

    private void update() {
        if (!isHaveImageoPath){
            imagepath = note.getImagepath();
        }
        if (!isHaveVideoPath){
            videoPath = note.getVideoPath();
        }
        Note newNote = new Note(note.getId(),
                note.getCategoryId(),
                title.getText().toString(),
                content.getText().toString(),
                System.currentTimeMillis(),
                imagepath,
                audioPath,
                videoPath,
                localtionX,
                localtionY,
                localtionName,
                note.getCreatetime());
        databaseOperator.updataNote(newNote);
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
        return R.layout.activity_note_detail;
    }

    @Override
    protected void initToolbar() {
        if (mode == 0) {
            initToolBar(mToolBar);
            mToolBar.setTitle("编辑便签");
        } else {
            initToolBar(mToolBar);
            mToolBar.setTitle("添加便签");
        }

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
}
