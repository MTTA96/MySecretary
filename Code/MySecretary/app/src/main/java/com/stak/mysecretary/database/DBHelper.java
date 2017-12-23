package com.stak.mysecretary.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zzzzz on 3/26/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    //Khai báo tên và phiên bản database
    public static final int DB_VERSION=1;
    public static final String DB_NAME = "MYSECRETARY.sqlite";

    //Khai báo các thành bảng Hoạt động
    public static final String TABLE_HOATDONG = "HOATDONG";
    public static final String KEY_TENHD = "TENHD";
    public static final String KEY_DIADIEM = "DIADIEM";
    public static final String KEY_TGBD = "TGBD";
    public static final String KEY_TGKT = "TGKT";
    public static final String KEY_HDNHOM = "NHOM";
    public static final String KEY_GHICHU = "GHICHU";

    //Hàm khởi tạo
    public DBHelper(Context context) {
        super(context,DB_NAME, null,DB_VERSION);
    }

    //Tạo bảng
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Câu lệnh tạo bảng hoạt động
        String CREATE_HD_TABLE="CREATE TABLE "+TABLE_HOATDONG+" ( "
                + KEY_TENHD + " TEXT PRIMARYKEY, "
                + KEY_DIADIEM + " TEXT, "
                + KEY_TGBD + " TEXT, "
                + KEY_TGKT + " TEXT, "
                + KEY_HDNHOM + " TEXT,"
                + KEY_GHICHU + " TEXT)";

        db.execSQL(CREATE_HD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOATDONG);
        onCreate(db);
    }
}
