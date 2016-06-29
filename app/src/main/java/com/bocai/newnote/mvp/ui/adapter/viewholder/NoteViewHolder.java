package com.bocai.newnote.mvp.ui.adapter.viewholder;


import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bocai.newnote.R;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

public class NoteViewHolder extends RecyclerView.ViewHolder{
    private final TextView title;
    private final TextView content;
    private final ImageView photo;
    private final ImageView camera;
    private final ImageView videocamera;
    private final ImageView location;
    private final MaterialRippleLayout ripple;
    private OnRippleClick onRippleClick;
    private OnLongRippleClick onLongRippleClick;


    public NoteViewHolder(View parent) {
        super(parent);
        ripple = (MaterialRippleLayout) parent.findViewById(R.id.note_ripple);
        title = (TextView) parent.findViewById(R.id.tv_title);
        content = (TextView) parent.findViewById(R.id.tv_content);
        photo = (ImageView) parent.findViewById(R.id.photo);
        camera = (ImageView) parent.findViewById(R.id.camera);
        videocamera = (ImageView) parent.findViewById(R.id.video_camera);
        location = (ImageView) parent.findViewById(R.id.location);
        RxView.clicks(ripple).throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe(aVoid -> onRippleClick.onRippleClick(ripple));
        RxView.longClicks(ripple).throttleFirst(1000,TimeUnit.MILLISECONDS).subscribe(aVoid -> onLongRippleClick.onLongRippleClick(ripple));
    }

    public void setTitleText(CharSequence text){
        setTextView(title, text);
    }

    public void setTitleText(int text){
        setTextView(title, text);
    }

    public void setContentText(CharSequence text){
        setTextView(content, text);
    }

    public void setContentText(int text){
        setTextView(content, text);
    }

    public void setPhotoVisible(boolean visibility) {
        if (visibility) {
            photo.setVisibility(View.VISIBLE);
        } else {
            photo.setVisibility(View.GONE);
        }
    }

    public void setLocationVisible(boolean visibility) {
        if (visibility) {
            location.setVisibility(View.VISIBLE);
        } else {
            location.setVisibility(View.GONE);
        }
    }

    public void setVideoCameraVisible(boolean visibility) {
        if (visibility) {
            videocamera.setVisibility(View.VISIBLE);
        } else {
            videocamera.setVisibility(View.GONE);
        }
    }

    private void setTextView(TextView view, CharSequence text){
        if (view == null )
            return;
        if (TextUtils.isEmpty(text))
            view.setVisibility(View.GONE);
        view.setText(text);
    }

    private void setTextView(TextView view, @StringRes int text){
        if (view == null || text <= 0)
            return;
        view.setText(text);
    }

    public void setOnRippleClickListener(OnRippleClick onRippleClick){
        this.onRippleClick = onRippleClick;
    }

    public interface OnRippleClick{
        void onRippleClick(View view);
    }

    public interface OnLongRippleClick{
        void onLongRippleClick(View view);
    }

    public void setOnLongRippleClickListener(OnLongRippleClick onLongRippleClick){
        this.onLongRippleClick = onLongRippleClick;
    }
}
