package com.stak.mysecretary.Presenter.Model.DataModel.TaiKhoan.Dangnhap;

/**
 * Created by Quang Trí on 7/10/2017.
 */

public interface DangNhapImpl {
    void XulyDangNhap(String email, String password);
    void XuLyDangNhapThanhCong();
    void XuLyDangNhapThatBai();
    void XuLyLoiDangNhap();
}
