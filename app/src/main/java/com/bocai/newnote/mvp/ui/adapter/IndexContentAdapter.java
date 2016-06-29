package com.bocai.newnote.mvp.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bocai.newnote.mvp.ui.fragment.IndexFragment;


public class IndexContentAdapter extends FragmentPagerAdapter {

    public IndexContentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override public Fragment getItem(int position) {
        Fragment fragment = new IndexFragment();
        Bundle argus = new Bundle();
        argus.putInt("position", position);
        fragment.setArguments(argus);
        return fragment;
    }

    @Override public int getCount() {
        return 3;
    }
}
