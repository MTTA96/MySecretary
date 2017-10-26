package com.stak.mysecretary.Presenter.Model.DataModel.DataBase;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by zzzzz on 4/6/2017.
 */

public class MyShared {
    private Activity activity;
    private SharedPreferences myShared;
    private SharedPreferences.Editor myEditor;

    private String tenFile;
    //Key tài khoản
    private String userName;
    private String isLogin;

    //Key data
    private String cbTKB = "cbtkb";
    private String cbCaNhan = "cbcanhan";

    public MyShared(String tenFile, Activity myActivity) {
        this.tenFile = tenFile;
        this.activity = myActivity;

        //Khởi tạo SharedPreferences
        myShared = activity.getSharedPreferences(this.tenFile, activity.MODE_PRIVATE);

        //Khỏi tạo biến editor
        myEditor = myShared.edit();
    }

    //Key tài khoản
    public String getUserName(){
        return myShared.getString(userName, null);
    }

    public void setUserName(String dataUserName){
        myEditor.putString(userName, dataUserName);
        myEditor.commit();
    }

    public boolean getIsLogin(){
        return myShared.getBoolean(this.isLogin, false);
    }

    public void setIsLogin(boolean isLogin){
        myEditor.putBoolean(this.isLogin, isLogin);
    }
    //Key data
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
