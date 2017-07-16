package com.stak.mysecretary.Handler.DataHandler.Presenter.Dangky;

import com.stak.mysecretary.Handler.DataHandler.Interfaces.PresenterImplDangKy;
import com.stak.mysecretary.Model.DangKy.ModelDangKyImpl;
import com.stak.mysecretary.Model.DangKy.DangKyImpl;
import com.stak.mysecretary.Handler.UiHandler.Interfaces.DangKyCallBack;

/**
 * Created by Quang Trí on 7/9/2017.
 */

public class PresenterDangKy implements PresenterImplDangKy {
    DangKyCallBack viewdk;
    DangKyImpl modeldk =new ModelDangKyImpl(this);

    public PresenterDangKy(DangKyCallBack viewdk) {
        this.viewdk = viewdk;
    }

    @Override
    public void xulydangky(String email, String password) {
        modeldk.DangKyFireBase(email,password);
    }

    @Override
    public void xulydangkythanhcong() {
        viewdk.dangkythanhcong();
    }

    @Override
    public void xulydangkythatbai() {
        viewdk.dangkythatbai();
    }

    @Override
    public void xulythongbaoloi() {
        viewdk.thongbaoloi();
    }


}
