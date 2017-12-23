package com.stak.mysecretary.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.stak.mysecretary.model.DaoMaster;
import com.stak.mysecretary.model.DaoSession;
import com.stak.mysecretary.model.HoatDong;
import com.stak.mysecretary.model.HoatDongDao;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ADMIN on 3/24/2017.
 * Lớp tương tác db cho hoạt động
 */
public class XulyHoatdong {
    private DBHelper dbHelper;

    //Test new ORM framework
    private DBOpenHelper dbOpenHelper;
    private DaoSession daoSession;
    private Database database;
    private HoatDongDao hoatDongDao;

    //Khởi tạo
    public XulyHoatdong(Context context) {
//        dbHelper = new DBHelper(context);

        DaoMaster.DevOpenHelper helper = new DBOpenHelper(context.getApplicationContext(), DBOpenHelper.DB_NAME);
        database = helper.getWritableDb();
        daoSession = new DaoMaster(database).newSession();
        hoatDongDao = this.daoSession.getHoatDongDao();
    }

    public XulyHoatdong(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    //Lấy hoạt động
    public HoatDong LayHoatDong(long Id){
        return hoatDongDao.load(Id);
    }
    //Phương thức thêm 1 hoat động
    public void Them(HoatDong hd) {
//
//        SQLiteDatabase db = dbHelper.getWritableDatabase();//Mở kết nối
//
//        ContentValues values = new ContentValues();
//        values.put(DBHelper.KEY_TENHD, hd.getTenhd());
//        values.put(DBHelper.KEY_DIADIEM, hd.getDiadiem());
//        values.put(DBHelper.KEY_TGBD, hd.getTgbd());
//        values.put(DBHelper.KEY_TGKT, hd.getTgkt());
//        values.put(DBHelper.KEY_HDNHOM, hd.getNhom());
//        values.put(DBHelper.KEY_GHICHU, hd.getGhichu());
//
//        //Chay lệnh insert
//        db.insert(DBHelper.TABLE_HOATDONG, null, values);
//        db.close();//Đóng kết nối
      hoatDongDao.insert(hd);
    }

    //Phương thức lấy tất cả dử liệu
    public ArrayList<HoatDong> laytatcahd() {
//        ArrayList<HoatDong> list = new ArrayList<>();//Tạo 1 mảng để chứa các phần tử
//        String selectquery = "SELECT * FROM " + DBHelper.TABLE_HOATDONG;//Câu lenh truy vấn
//        SQLiteDatabase db = dbHelper.getWritableDatabase();//Mở kết nối
//        Cursor cursor = db.rawQuery(selectquery, null);//Chạy câu truy vấn
//        //Đưa con trỏ về dòng đầu tiên
//        if (cursor.moveToFirst()) {
//            do {
//                //Duyệt qua từng dòng lấy và bỏ vào mảng
//                HoatDong hd = new HoatDong();
//                hd.setTenhd(cursor.getString(0));
//                hd.setDiadiem(cursor.getString(1));
//                hd.setTgbd(cursor.getString(2));
//                hd.setTgkt(cursor.getString(3));
//                hd.setNhom(cursor.getString(4));
//                hd.setGhichu(cursor.getString(5));
//
//                // Thêm 1 phần tử vào mảng.
//                list.add(hd);
//            } while (cursor.moveToNext());
//        }
//        return list;
        return (ArrayList<HoatDong>) hoatDongDao.loadAll();
    }

    //Lấy tất cả dữ liệu theo Ngày
    public ArrayList<HoatDong> laydstheongay(String ngay) {
//        ArrayList<HoatDong> list = new ArrayList<>();//Tạo 1 mảng để chứa các phần tử
//        String selectquery = "SELECT * FROM " + DBHelper.TABLE_HOATDONG + " WHERE " + DBHelper.KEY_TGBD + " LIKE '" + ngay + "%'";//Câu lenh truy vấn
//        SQLiteDatabase db = dbHelper.getWritableDatabase();//Mở kết nối
//        Cursor cursor = db.rawQuery(selectquery, null);//Chạy câu truy vấn
//        //Đưa con trỏ về dòng đầu tiên
//        if (cursor.moveToFirst()) {
//            do {
//                //Duyệt qua từng dòng lấy và bỏ vào mảng
//                HoatDong hd = new HoatDong();
//                hd.setTenhd(cursor.getString(0));
//                hd.setDiadiem(cursor.getString(1));
//                hd.setTgbd(cursor.getString(2));
//                hd.setTgkt(cursor.getString(3));
//                hd.setNhom(cursor.getString(4));
//                hd.setGhichu(cursor.getString(5));
//
//                // Thêm 1 phần tử vào mảng.
//                list.add(hd);
//            } while (cursor.moveToNext());
//        }
//        return list;
        return (ArrayList<HoatDong>) hoatDongDao.queryBuilder().where(HoatDongDao.Properties.Tgbd.like(ngay+"%")).list();
    }

    //Phương thức kiểm tra tên hoạt động đã có chưa
    public int kttenhd(String tenhd, String tgbd) {
//        String selectquery = "SELECT COUNT(*) FROM " + DBHelper.TABLE_HOATDONG + " WHERE " + DBHelper.KEY_TENHD + " LIKE '" + tenhd + "' AND " + DBHelper.KEY_TGBD + " LIKE '" + tgbd + "'";
//        SQLiteDatabase db = dbHelper.getWritableDatabase();//Mở kết nối
//        Cursor mCount = db.rawQuery(selectquery, null);
//        mCount.moveToFirst();
//        int count = mCount.getInt(0);
//        mCount.close();
//        return count;
        return hoatDongDao.queryBuilder().where(HoatDongDao.Properties.Tenhd.like(tenhd), HoatDongDao.Properties.Tgbd.like(tgbd)).list().size();
    }

    //Cập nhật dữ liệu
    public void Capnhat(HoatDong hoatDong) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(DBHelper.KEY_DIADIEM, diadiem);
//        contentValues.put(DBHelper.KEY_TGKT, tgkt);
//        db.update(DBHelper.TABLE_HOATDONG, contentValues, DBHelper.KEY_TENHD + " = '" + tenhd + "' and " + DBHelper.KEY_TGBD + " like '" + tgbd + "'", null);
        hoatDongDao.update(hoatDong);
    }

    //Xóa hoạt động
    public void XoaHd(long Id) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.delete(DBHelper.TABLE_HOATDONG, DBHelper.KEY_TENHD + " = '" + tenhd + "' and " + DBHelper.KEY_TGBD + " like '" + tgbd + "'", null);
        hoatDongDao.delete(hoatDongDao.load(Id));
    }

    //Lấy danh sách hoạt động theo nhóm
    public ArrayList<HoatDong> LayDStheonhom(String nhom) {
//        ArrayList<HoatDong> list = new ArrayList<>();//Tạo 1 mảng để chứa các phần tử
//        String selectquery = "SELECT * FROM " + DBHelper.TABLE_HOATDONG + " WHERE " + DBHelper.KEY_HDNHOM + " LIKE '" + nhom + "'";//Câu lenh truy vấn
//        SQLiteDatabase db = dbHelper.getWritableDatabase();//Mở kết nối
//        Cursor cursor = db.rawQuery(selectquery, null);//Chạy câu truy vấn
//        //Đưa con trỏ về dòng đầu tiên
//        if (cursor.moveToFirst()) {
//            do {
//                //Duyệt qua từng dòng lấy và bỏ vào mảng
//                HoatDong hd = new HoatDong();
//                hd.setTenhd(cursor.getString(0));
//                hd.setDiadiem(cursor.getString(1));
//                hd.setTgbd(cursor.getString(2));
//                hd.setTgkt(cursor.getString(3));
//                hd.setNhom(cursor.getString(4));
//                hd.setGhichu(cursor.getString(5));
//
//                // Thêm 1 phần tử vào mảng.
//                list.add(hd);
//            } while (cursor.moveToNext());
//        }
//        return list;
        return (ArrayList<HoatDong>) hoatDongDao.queryBuilder().where(HoatDongDao.Properties.Nhom.eq(nhom)).list();
    }

    //Lấy danh sách theo ngày và nhóm
    public ArrayList<HoatDong> laydstheongaynhom(String ngay, String nhom) {
//        ArrayList<HoatDong> list = new ArrayList<>();//Tạo 1 mảng để chứa các phần tử
//        String selectquery = "SELECT * FROM " + DBHelper.TABLE_HOATDONG + " WHERE " + DBHelper.KEY_TGBD + " LIKE '" + ngay + "%' AND " + DBHelper.KEY_HDNHOM + " LIKE '" + nhom + "'";//Câu lenh truy vấn
//        SQLiteDatabase db = dbHelper.getWritableDatabase();//Mở kết nối
//        Cursor cursor = db.rawQuery(selectquery, null);//Chạy câu truy vấn
//        //Đưa con trỏ về dòng đầu tiên
//        if (cursor.moveToFirst()) {
//            do {
//                //Duyệt qua từng dòng lấy và bỏ vào mảng
//                HoatDong hd = new HoatDong();
//                hd.setTenhd(cursor.getString(0));
//                hd.setDiadiem(cursor.getString(1));
//                hd.setTgbd(cursor.getString(2));
//                hd.setTgkt(cursor.getString(3));
//                hd.setNhom(cursor.getString(4));
//                hd.setGhichu(cursor.getString(5));
//
//                // Thêm 1 phần tử vào mảng.
//                list.add(hd);
//            } while (cursor.moveToNext());
//        }
//        return list;
        return (ArrayList<HoatDong>) hoatDongDao.queryBuilder().where(HoatDongDao.Properties.Nhom.eq(nhom),HoatDongDao.Properties.Tgbd.like(ngay)).list();
    }
}
