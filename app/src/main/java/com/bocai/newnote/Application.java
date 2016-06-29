package com.bocai.newnote;

import com.bocai.newnote.mvp.model.ConstanTs;
import com.bocai.newnote.mvp.model.Realm.RealmMigration;
import cn.jpush.android.api.JPushInterface;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();
        initJPush();
        initBmob();
    }

    private void initBmob() {
//        Bmob.initialize(this, "17ba7f686586db41ef070d2f2bee3abf");
    }
    //初始化极光推送
    private void initJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
    //初始化Realm
    private void initRealm() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("newnote.realm")
                .migration(new RealmMigration())
                .schemaVersion(ConstanTs.REALM_VERSION)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
