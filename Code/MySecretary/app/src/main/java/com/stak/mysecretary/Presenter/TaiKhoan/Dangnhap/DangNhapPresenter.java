package com.stak.mysecretary.Presenter.TaiKhoan.Dangnhap;

import com.stak.mysecretary.DataBase.FireBaseHelper;
import com.stak.mysecretary.Presenter.Model.DataModel.TaiKhoan.Dangnhap.DangNhapImpl;
import com.stak.mysecretary.Presenter.Model.DataModel.TaiKhoan.Dangnhap.DangNhapHelperImpl;
import com.stak.mysecretary.Presenter.Model.UiModel.Interfaces.DangNhapUiImpl;

/**
 * Created by Quang Trí on 7/10/2017.
 */

public class DangNhapPresenter implements DangNhapImpl {
    DangNhapHelperImpl dangNhapHelperImpl = new FireBaseHelper(this);
    DangNhapUiImpl handlerUiDangNhap;

    public DangNhapPresenter(DangNhapUiImpl handlerUiDangNhap) {
        this.handlerUiDangNhap = handlerUiDangNhap;
    }

    //Hướng dẫn xử lý cho Ui
    @Override
    public void XulyDangNhap(String email, String password) {
        dangNhapHelperImpl.DangNhapFireBase(email,password);
    }

    @Override
    public void XuLyDangNhapThanhCong() {
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
