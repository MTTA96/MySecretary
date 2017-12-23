package com.stak.mysecretary.database;

import android.content.Context;

import com.stak.mysecretary.model.DaoMaster;

import org.greenrobot.greendao.database.Database;

/**
 * Created by zzzzz on 12/23/2017.
 */

public class DBOpenHelper extends DaoMaster.DevOpenHelper {
    public static final String DB_NAME = "MYSECRETARY.sqlite";

    public DBOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
