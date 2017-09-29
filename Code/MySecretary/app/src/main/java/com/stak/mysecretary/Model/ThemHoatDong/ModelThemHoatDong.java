package com.stak.mysecretary.Model.ThemHoatDong;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.stak.mysecretary.DataBase.DBHelper;
import com.stak.mysecretary.DataBase.XulyHoatdong;
import com.stak.mysecretary.Model.HoatDong;

import java.util.ArrayList;

/**
 * Created by Quang Trí on 7/16/2017.
 */

public class ModelThemHoatDong implements ThemHoatDongImpl{
    private DBHelper dbHelper;
    //Khởi tạo
    public ModelThemHoatDong(Context context){
        dbHelper=new DBHelper(context);
    }

    public ModelThemHoatDong(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }


    @Override
    public void ThemHoatDongSQLite(HoatDong hoatdong) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();//Mở kết nối

        ContentValues values=new ContentValues();
        values.put(DBHelper.KEY_TENHD,hoatdong.getTenhd());
        values.put(DBHelper.KEY_DIADIEM,hoatdong.getDiadiem());
        values.put(DBHelper.KEY_TGBD,hoatdong.getTgbd());
        values.put(DBHelper.KEY_TGKT,hoatdong.getTgkt());
        values.put(DBHelper.KEY_THU,hoatdong.getThu());
        values.put(DBHelper.KEY_UPDATE,hoatdong.getKeyupdate());
        values.put(DBHelper.KEY_HDNHOM,hoatdong.getNhom());
        values.put(DBHelper.KEY_GHICHU,hoatdong.getGhichu());
        //Chay lệnh insert
        db.insert(DBHelper.TABLE_HOATDONG,null,values);
        db.close();//Đóng kết nối
    }

    @Override
    public ArrayList<HoatDong> LayDuLieuSQLiteSoSanh() {

        ArrayList<HoatDong> list =new ArrayList<>();//Tạo 1 mảng để chứa các phần tử
        String selectquery="SELECT * FROM "+DBHelper.TABLE_HOATDONG;//Câu lenh truy vấn
        SQLiteDatabase db=dbHelper.getWritableDatabase();//Mở kết nối
        Cursor cursor=db.rawQuery(selectquery,null);//Chạy câu truy vấn
        //Đưa con trỏ về dòng đầu tiên
        if(cursor.moveToFirst()) {
            do {
                //Duyệt qua từng dòng lấy và bỏ vào mảng
                HoatDong hd = new HoatDong();
                hd.setTenhd(cursor.getString(0));
                hd.setDiadiem(cursor.getString(1));
                hd.setTgbd(cursor.getString(2));
                hd.setTgkt(cursor.getString(3));
                hd.setThu(cursor.getString(4));
                hd.setNhom(cursor.getString(5));
                hd.setGhichu(cursor.getString(6));
                hd.setBackground(cursor.getString(7));
                hd.setKeyupdate(cursor.getString(8));

                // Thêm 1 phần tử vào mảng.
                list.add(hd);
            } while (cursor.moveToNext());
        }
        return list;
    }

}
