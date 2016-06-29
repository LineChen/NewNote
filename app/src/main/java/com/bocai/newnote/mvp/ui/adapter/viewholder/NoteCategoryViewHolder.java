package com.bocai.newnote.mvp.ui.adapter.viewholder;

import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bocai.newnote.R;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

public class NoteCategoryViewHolder extends RecyclerView.ViewHolder {

    private final TextView cateName;
    private final TextView num;
    private final MaterialRippleLayout ripple;//波纹效果
    private OnRippleClick onRippleClick;
    private OnLongRippleClick onLongRippleClick;


    public NoteCategoryViewHolder(View parent) {
        super(parent);
        ripple = (MaterialRippleLayout) parent.findViewById(R.id.cate_ripple);
        cateName = (TextView) parent.findViewById(R.id.tv_catename);
        num = (TextView) parent.findViewById(R.id.tv_catenum);
        RxView.clicks(ripple).throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe(aVoid -> onRippleClick.onRippleClick(ripple));
        RxView.longClicks(ripple).throttleFirst(1000,TimeUnit.MILLISECONDS).subscribe(aVoid -> onLongRippleClick.onLongRippleClick(ripple));
    }

    public void setCateNameText(CharSequence text){
        setTextView(cateName, text);
    }

    public void setCateNameText(int text){
        setTextView(cateName, text);
    }

    public void setNumText(CharSequence text){
        setTextView(num, text);
    }

    public void setNumText(int text){
        setTextView(num, text);
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
