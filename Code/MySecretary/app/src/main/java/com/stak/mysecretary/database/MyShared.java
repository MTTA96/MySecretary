package com.stak.mysecretary.DataBase;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by zzzzz on 4/6/2017.
 */

public class MyShared {
    //Khai báo biến SharedPreferences
    SharedPreferences myShared;

    //Khai báo biến Editor để ghi và xóa
    SharedPreferences.Editor myEditor;

    //Khai báo key cbTKB và cbCaNhan
    String cbTKB = "cbtkb";
    String cbCaNhan = "cbcanhan";
    //Khai báo biến dựng để lưu trữ tên file
    String strShared;

    //Khai báo biến Activity
    Activity activity;

    //Tạo hàm dựng cho class MyShared
    public MyShared(String strTenFile, Activity myActivity) {
        this.strShared = strTenFile;
        this.activity = myActivity;

        //Khởi tạo SharedPreferences
        myShared = activity.getSharedPreferences(strShared, activity.MODE_PRIVATE);

        //Khỏi tạo biến editor
        myEditor = myShared.edit();
    }

    //Tạo get, set cho cbTKB và cbCaNhan
    public boolean getCbTKB() {
        return myShared.getBoolean(cbTKB, true);
    }

    public void setCbTKB(boolean cbTKB) {
        myEditor = myEditor.putBoolean(this.cbTKB, cbTKB);
        myEditor.commit();
    }

    public boolean getCbCaNhan() {
        return myShared.getBoolean(cbCaNhan, true);
    }

    public void setCbCaNhan(boolean cbCaNhan) {
        myEditor = myEditor.putBoolean(this.cbCaNhan, cbCaNhan);
        myEditor.commit();
    }
}
