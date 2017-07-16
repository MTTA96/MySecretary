package com.stak.mysecretary.Handler.DataHandler.Presenter.Dangnhap;

import com.stak.mysecretary.Handler.DataHandler.Interfaces.PresenterImplDangnhap;
import com.stak.mysecretary.Model.Dangnhap.ModelDangNhap;
import com.stak.mysecretary.Model.Dangnhap.DangNhapImpl;
import com.stak.mysecretary.Handler.UiHandler.Interfaces.DangNhapCallBack;

/**
 * Created by Quang Trí on 7/10/2017.
 */

public class PresenterDangnhap implements PresenterImplDangnhap {
    DangNhapImpl modelipmDn =new ModelDangNhap(this);
    DangNhapCallBack viewDn;

    public PresenterDangnhap(DangNhapCallBack viewDn) {
        this.viewDn = viewDn;
    }

    @Override
    public void xulydangnhap(String email, String password) {
        modelipmDn.dangnhapfirebase(email,password);
    }

    @Override
    public void xulydangnhaptc() {
        viewDn.dangnhapthanhcong();
    }

    @Override
    public void xulydangnhaptb() {
        viewDn.dangnhapthatbai();
    }

    @Override
    public void xulyloidangnhap() {
        viewDn.thongbaoloidangnhap();
    }
}
