package com.bocai.newnote.mvp.presenter.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;

import com.bocai.newnote.R;
import com.bocai.newnote.databinding.ActivityCheckLockBinding;
import com.bocai.newnote.mvp.model.bean.LockBean;
import com.bocai.newnote.mvp.presenter.ActivityPresenter;
import com.bocai.newnote.mvp.ui.activity.IndexActivity;
import com.bocai.newnote.mvp.ui.view.CheckLockAView;
import com.bocai.newnote.utils.LockPatternUtils;
import com.bocai.newnote.widget.LockPatternView;

import java.util.List;


public class CheckLockAImpl implements ActivityPresenter {

    private final Context mContext;
    private final ActivityCheckLockBinding mDataBinding;
    private LockBean lockBean;
    private final CheckLockAView mCheckView;

    public CheckLockAImpl(Context context, CheckLockAView view, ActivityCheckLockBinding binding) {
        mContext = context;
        mCheckView = view;
        mDataBinding = binding;
    }

    @Override public void onCreate(Bundle savedInstanceState) {

        lockBean = new LockBean(mContext.getString(R.string.check_default));
        lockBean.setColor(mContext.getResources().getColor(R.color.actionbar_title_color));
        mDataBinding.setLockWarn(lockBean);
        mCheckView.initLockPatternView();
    }

    @Override
    public void getIntent(Intent intent) {

    }

    @Override public void onResume() {

    }

    @Override public void onStart() {

    }

    @Override public void onPause() {

    }

    @Override public void onStop() {

    }

    @Override public void onDestroy() {

    }

    public void check(List<LockPatternView.Cell> pattern) {
        if (pattern == null) return;

        LockPatternUtils instances = LockPatternUtils.getInstances(mContext);
        if (instances.checkPattern(pattern)) {
            mCheckView.readyGoThenKill(IndexActivity.class);
        } else {
            mCheckView.lockDisplayError();
            lockBean.setColor(mContext.getResources().getColor(R.color.text_red));
            mDataBinding.setLockWarn(lockBean);
            mDataBinding.showText.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.shake_x));
        }
    }
}
