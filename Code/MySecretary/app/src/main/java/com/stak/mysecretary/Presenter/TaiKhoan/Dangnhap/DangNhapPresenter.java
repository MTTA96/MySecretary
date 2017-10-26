package com.stak.mysecretary.Presenter.TaiKhoan.Dangnhap;

import android.app.Activity;
import android.content.Context;

import com.stak.mysecretary.Presenter.Model.DataModel.DataBase.FireBaseHelper;
import com.stak.mysecretary.Presenter.Model.DataModel.DataBase.MyShared;
import com.stak.mysecretary.Presenter.Model.DataModel.TaiKhoan.Dangnhap.DangNhapImpl;
import com.stak.mysecretary.Presenter.Model.DataModel.TaiKhoan.Dangnhap.DangNhapHelperImpl;
import com.stak.mysecretary.Presenter.Model.UiModel.Interfaces.DangNhapUiImpl;
import com.stak.mysecretary.Util.SupportList;

/**
 * Created by Quang Trí on 7/10/2017.
 */

public class DangNhapPresenter implements DangNhapImpl {
    private Context context;
    private MyShared myShared;
    private DangNhapHelperImpl dangNhapHelperImpl = new FireBaseHelper(this);
    private DangNhapUiImpl handlerUiDangNhap;

    public DangNhapPresenter(Context context, DangNhapUiImpl handlerUiDangNhap) {
        this.context = context;
        this.handlerUiDangNhap = handlerUiDangNhap;
        myShared = new MyShared(SupportList.FILE_NAME_SHAREDPREF, (Activity) context);
    }

    //Hướng dẫn xử lý cho Ui
    @Override
    public void XulyDangNhap(String email, String password) {
        dangNhapHelperImpl.DangNhapFireBase(email,password);
    }

    @Override
    public void XuLyDangNhapThanhCong() {
        /*myShared.setUserName();
        myShared.setIsLogin(true);*/
        handlerUiDangNhap.DangNhapThanhCong();
    }

    @Override
    public void XuLyDangNhapThatBai() {
        handlerUiDangNhap.DangNhapThatBai();
    }

    @Override
    public void XuLyLoiDangNhap() {
        handlerUiDangNhap.LoiDangNhap();
    }
}
