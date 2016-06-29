package com.bocai.newnote.mvp.model.Realm;

import io.realm.DynamicRealm;
import io.realm.RealmSchema;

public class RealmMigration implements io.realm.RealmMigration {

    @Override
    public void migrate(DynamicRealm dynamicRealm, long oldVersion, long newVersion) {
        RealmSchema schema = dynamicRealm.getSchema();

        if (oldVersion == 0) {
            schema.get("God")
                    .addField("noteInfo", String.class);
            oldVersion++;
        }
    }


}
