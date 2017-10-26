package com.stak.mysecretary.Presenter.Model.DataModel.DataBase;

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

    //Khai báo các thành bảng Hoạt động cho thời khóa biểu
    public static final String TABLE_HOATDONG ="HOATDONG";
    public static final String KEY_TENHD = "TENHD";
    public static final String KEY_DIADIEM = "DIADIEM";
    public static final String KEY_TGBD = "TGBD";
    public static final String KEY_TGKT = "TGKT";
    public static final String KEY_THU="THU";
    public static final String KEY_BACKGROUND="BACKGROUND";
//    public static final String KEY_HENGIO="HENGIO";
    public static final String KEY_HDNHOM = "NHOM";
    public static final String KEY_GHICHU = "GHICHU";
    public static final String KEY_UPDATE="KEYUPDATE";

    public static final String TABLE_NHOMHD="NHOMHD";
    public static final String KEY_TENNHOMHD="TENNHOMHD";

    //Hàm khởi tạo
    public DBHelper(Context context) {
        super(context,DB_NAME, null,DB_VERSION);
    }

    //Tạo bảng
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Câu lệnh tạo bảng hoạt động
        String CREATE_HD_TABLE="CREATE TABLE "+TABLE_HOATDONG+" ( "
                + KEY_TENHD + " TEXT PRIMARY KEY, "
                + KEY_DIADIEM + " TEXT, "
                + KEY_TGBD + " TEXT, "
                + KEY_TGKT + " TEXT, "
//                + KEY_HENGIO +" TEXT, "
                + KEY_THU + " TEXT , "
                + KEY_HDNHOM + " TEXT, "
                + KEY_GHICHU + " TEXT, "
                + KEY_BACKGROUND + " TEXT, "
                + KEY_UPDATE + " TEXT )";
        String CREATE_NHOMHD_TABLE="CREATE TABLE "+TABLE_NHOMHD+" ( "
                + KEY_TENNHOMHD +" TEXT PRIMARY KEY ) ";
        db.execSQL(CREATE_HD_TABLE);
        db.execSQL(CREATE_NHOMHD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOATDONG);
        onCreate(db);
    }
}
