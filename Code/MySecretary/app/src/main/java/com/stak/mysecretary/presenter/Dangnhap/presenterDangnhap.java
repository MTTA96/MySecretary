package com.stak.mysecretary.presenter.Dangnhap;

import com.stak.mysecretary.model.modelDangnhap.modelDangnhap;
import com.stak.mysecretary.model.modelDangnhap.modelipmDangnhap;
import com.stak.mysecretary.view.viewDangnhap.viewDangnhap;

/**
 * Created by Quang Trí on 7/10/2017.
 */

public class presenterDangnhap implements presenterimpDangnhap {
    modelipmDangnhap modelipmDn =new modelDangnhap(this);
    viewDangnhap viewDn;

    public presenterDangnhap(viewDangnhap viewDn) {
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
