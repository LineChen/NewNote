package com.bocai.newnote.mvp.presenter;

import android.content.Intent;
import android.os.Bundle;


public interface ActivityPresenter {

    void onCreate(Bundle savedInstanceState);

    void getIntent(Intent intent);

    void onResume();

    void onStart();

    void onPause();

    void onStop();

    void onDestroy();
}
