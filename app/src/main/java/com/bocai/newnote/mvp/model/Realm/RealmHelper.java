package com.bocai.newnote.mvp.model.Realm;

import android.content.Context;


import com.bocai.newnote.mvp.model.bean.God;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmHelper {
    private static RealmHelper instances;
    private Context mContext;

    private RealmHelper(Context context){
        mContext = context;
    }
    public static RealmHelper getInstances(Context context){//单例
        if (instances == null) {
            instances = new RealmHelper(context);
        }
        return instances;
    }
    //和数据库一样都需要close
    private static void closeConnect(Realm realm) {
        if (null != realm) {
            try {
                realm.close();
                realm = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //安全
//    private static RealmConfiguration secure(Context context) {
//        byte[] key = new byte[64];
//        new SecureRandom().nextBytes(key);
//        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context)
//                .encryptionKey(key)
//                .build();
//
//        // Start with a clean slate every time
//        Realm.deleteRealm(realmConfiguration);
//        return realmConfiguration;
//    }

    public static ArrayList<God> selector(Context context, int godType){
        Realm realm = Realm.getInstance(context);
        RealmQuery<God> realmQuery = realm.where(God.class);
        RealmQuery<God> godRealmQuery = realmQuery.equalTo("godType", godType);
        RealmResults<God> realmResults = godRealmQuery.findAll();
        if (realmResults != null && realmResults.size() > 0) {
            ArrayList<God> godList = new ArrayList<>();
            for (God god : realmResults) {
                godList.add(god);
            }
            Collections.reverse(godList);
            return godList;
        }
        return null;
    }

    public static boolean save(Context context, God god) {
        if (check(context, god)) {
            return true;
        }
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        realm.copyToRealm(god);
        realm.commitTransaction();
        return false;
    }
    //检查是否重复添加一个数据
    private static boolean check(Context context, God god) {
        int godType = god.getGodType();
        Realm realm = Realm.getInstance(context);
        RealmQuery<God> realmQuery = realm.where(God.class);
        RealmQuery<God> godRealmQuery = realmQuery.equalTo("godType", godType);
        RealmResults<God> title = godRealmQuery.contains("title", god.getTitle()).findAll();
        if (title!=null&&title.size()>0) {
            return true;
        }
        return false;
    }
    //更新数据库
    public static boolean update(Context context, God god) {
        if (check(context, god)) {
            return true;
        }
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(god);
        realm.commitTransaction();
        return false;
    }

    //删除
    public static void delete(Context context, God god, int position) {
        Realm realm = Realm.getInstance(context);
        RealmQuery<God> realmQuery = realm.where(God.class);
        RealmQuery<God> godRealmQuery = realmQuery.equalTo("godType", god.getGodType());
        RealmResults<God> realmResults = godRealmQuery.findAll();
        if (realmResults != null ) {
            int size = realmResults.size() - 1;
            int i = size - position;
            realm.beginTransaction();
            realmResults.remove(i);
            realm.commitTransaction();
        }
    }
}
