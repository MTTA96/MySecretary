package com.stak.mysecretary.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.stak.mysecretary.model.Hoatdong;

import java.util.ArrayList;

/**
 * Created by ADMIN on 3/24/2017.
 */
//Tạo lớp xửa lý cho hoạt động
public class XulyHoatdong {
    private DBHelper dbHelper;
//Khởi tạo
    public XulyHoatdong(Context context){
    dbHelper=new DBHelper(context);
}

    public XulyHoatdong(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    //Phương thức thêm 1 hoat động
    public void Them(Hoatdong hd){

        SQLiteDatabase db=dbHelper.getWritableDatabase();//Mở kết nối

        ContentValues values=new ContentValues();
        values.put(DBHelper.KEY_TENHD,hd.getTenhd());
        values.put(DBHelper.KEY_DIADIEM,hd.getDiadiem());
        values.put(DBHelper.KEY_TGBD,hd.getTgbd());
        values.put(DBHelper.KEY_TGKT,hd.getTgkt());
        values.put(DBHelper.KEY_HDNHOM,hd.getNhom());
        values.put(DBHelper.KEY_GHICHU,hd.getGhichu());

        //Chay lệnh insert
        db.insert(DBHelper.TABLE_HOATDONG,null,values);
        db.close();//Đóng kết nối
    }
    public Cursor laydongcuoi()
    {
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query(DBHelper.TABLE_HOATDONG,new String[]{DBHelper.KEY_TENHD,DBHelper.KEY_DIADIEM,DBHelper.KEY_TGBD,DBHelper.KEY_TGKT,DBHelper.KEY_HDNHOM,DBHelper.KEY_GHICHU},null,null,null,null,null);
        if (cursor != null){
            cursor.moveToLast();
        }
        return cursor;
    }

    //Lấy dong đầu trong csdl
    public Cursor laydongdau()
    {
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query(DBHelper.TABLE_HOATDONG,new String[]{DBHelper.KEY_TENHD,DBHelper.KEY_DIADIEM,DBHelper.KEY_TGBD,DBHelper.KEY_TGKT,DBHelper.KEY_HDNHOM,DBHelper.KEY_GHICHU},null,null,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    //Phương thức lấy tất cả dử liệu
    public ArrayList<Hoatdong> laytatcahd()
    {
        ArrayList<Hoatdong> list =new ArrayList<>();//Tạo 1 mảng để chứa các phần tử
        String selectquery="SELECT * FROM "+DBHelper.TABLE_HOATDONG;//Câu lenh truy vấn
        SQLiteDatabase db=dbHelper.getWritableDatabase();//Mở kết nối
        Cursor cursor=db.rawQuery(selectquery,null);//Chạy câu truy vấn
        //Đưa con trỏ về dòng đầu tiên
        if(cursor.moveToFirst()) {
            do {
                //Duyệt qua từng dòng lấy và bỏ vào mảng
                Hoatdong hd = new Hoatdong();
                hd.setTenhd(cursor.getString(0));
                hd.setDiadiem(cursor.getString(1));
                hd.setTgbd(cursor.getString(2));
                hd.setTgkt(cursor.getString(3));
                hd.setNhom(cursor.getString(4));
                hd.setGhichu(cursor.getString(5));

                // Thêm 1 phần tử vào mảng.
                list.add(hd);
            } while (cursor.moveToNext());
        }
        return list;
    }
    //Lấy tất cả dữ liệu theo Ngày
    public ArrayList<Hoatdong> laydstheongay(String ngay)
    {
        ArrayList<Hoatdong> list =new ArrayList<>();//Tạo 1 mảng để chứa các phần tử
        String selectquery="SELECT * FROM "+DBHelper.TABLE_HOATDONG+" WHERE "+DBHelper.KEY_TGBD+" LIKE '"+ngay+"%'";//Câu lenh truy vấn
        SQLiteDatabase db = dbHelper.getWritableDatabase();//Mở kết nối
        Cursor cursor=db.rawQuery(selectquery,null);//Chạy câu truy vấn
        //Đưa con trỏ về dòng đầu tiên
        if(cursor.moveToFirst()) {
            do {
                //Duyệt qua từng dòng lấy và bỏ vào mảng
                Hoatdong hd = new Hoatdong();
                hd.setTenhd(cursor.getString(0));
                hd.setDiadiem(cursor.getString(1));
                hd.setTgbd(cursor.getString(2));
                hd.setTgkt(cursor.getString(3));
                hd.setNhom(cursor.getString(4));
                hd.setGhichu(cursor.getString(5));

                // Thêm 1 phần tử vào mảng.
                list.add(hd);
            } while (cursor.moveToNext());
        }
        return list;
    }
    //Phương thức kiểm tra tên hoạt động đã có chưa
    public int kttenhd(String tenhd,String tgbd)
    {
        String selectquery="SELECT COUNT(*) FROM "+DBHelper.TABLE_HOATDONG+" WHERE "+DBHelper.KEY_TENHD+" LIKE '"+tenhd+"' AND "+DBHelper.KEY_TGBD+" LIKE '"+tgbd+"'";
        SQLiteDatabase db=dbHelper.getWritableDatabase();//Mở kết nối
        Cursor mCount=db.rawQuery(selectquery, null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        return count;
    }

    //Cập nhật dữ liệu
    public void Capnhat(String tenhd,String diadiem,String tgbd,String tgkt)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_DIADIEM, diadiem);
        contentValues.put(DBHelper.KEY_TGKT,tgkt);
        db.update(DBHelper.TABLE_HOATDONG, contentValues, DBHelper.KEY_TENHD+ " = '" + tenhd +"' and "+DBHelper.KEY_TGBD+" like '"+tgbd + "'", null);
    }

    //Xóa hoạt động
    public void XoaHd (String tenhd,String tgbd){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABLE_HOATDONG, DBHelper.KEY_TENHD + " = '" + tenhd + "' and " + DBHelper.KEY_TGBD + " like '" + tgbd + "'", null) ;
    }

    //Lấy danh sách hoạt động theo nhóm
    public ArrayList<Hoatdong> LayDStheonhom(String nhom)
    {
        ArrayList<Hoatdong> list =new ArrayList<>();//Tạo 1 mảng để chứa các phần tử
        String selectquery="SELECT * FROM "+DBHelper.TABLE_HOATDONG+" WHERE "+DBHelper.KEY_HDNHOM+" LIKE '" + nhom + "'";//Câu lenh truy vấn
        SQLiteDatabase db=dbHelper.getWritableDatabase();//Mở kết nối
        Cursor cursor=db.rawQuery(selectquery,null);//Chạy câu truy vấn
        //Đưa con trỏ về dòng đầu tiên
        if(cursor.moveToFirst()) {
            do {
                //Duyệt qua từng dòng lấy và bỏ vào mảng
                Hoatdong hd = new Hoatdong();
                hd.setTenhd(cursor.getString(0));
                hd.setDiadiem(cursor.getString(1));
                hd.setTgbd(cursor.getString(2));
                hd.setTgkt(cursor.getString(3));
                hd.setNhom(cursor.getString(4));
                hd.setGhichu(cursor.getString(5));

                // Thêm 1 phần tử vào mảng.
                list.add(hd);
            } while (cursor.moveToNext());
        }
        return list;
    }

    //Lấy danh sách theo ngày và nhóm
    public ArrayList<Hoatdong> laydstheongaynhom(String ngay,String nhom)
    {
        ArrayList<Hoatdong> list =new ArrayList<>();//Tạo 1 mảng để chứa các phần tử
        String selectquery="SELECT * FROM "+DBHelper.TABLE_HOATDONG+" WHERE "+DBHelper.KEY_TGBD+" LIKE '"+ngay+"%' AND "+DBHelper.KEY_HDNHOM+" LIKE '"+nhom+"'";//Câu lenh truy vấn
        SQLiteDatabase db=dbHelper.getWritableDatabase();//Mở kết nối
        Cursor cursor=db.rawQuery(selectquery,null);//Chạy câu truy vấn
        //Đưa con trỏ về dòng đầu tiên
        if(cursor.moveToFirst()) {
            do {
                //Duyệt qua từng dòng lấy và bỏ vào mảng
                Hoatdong hd = new Hoatdong();
                hd.setTenhd(cursor.getString(0));
                hd.setDiadiem(cursor.getString(1));
                hd.setTgbd(cursor.getString(2));
                hd.setTgkt(cursor.getString(3));
                hd.setNhom(cursor.getString(4));
                hd.setGhichu(cursor.getString(5));

                // Thêm 1 phần tử vào mảng.
                list.add(hd);
            } while (cursor.moveToNext());
        }
        return list;
    }
}
