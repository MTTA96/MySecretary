package com.stak.mysecretary.presenter.Dangky;

import com.stak.mysecretary.model.modelDangky.modelDangky;
import com.stak.mysecretary.model.modelDangky.modelimpDangky;
import com.stak.mysecretary.view.viewDangky.viewDangky;

/**
 * Created by Quang Trí on 7/9/2017.
 */

public class presenterDangky implements presenterimpDangky {
    viewDangky viewdk;
    modelimpDangky modeldk =new modelDangky(this);

    public presenterDangky(viewDangky viewdk) {
        this.viewdk = viewdk;
    }

    @Override
    public void xulydangky(String email, String password) {
        modeldk.dangkyfirebase(email,password);
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
