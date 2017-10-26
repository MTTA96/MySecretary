package com.stak.mysecretary.Presenter.TaiKhoan.Dangky;

import com.stak.mysecretary.Presenter.Model.DataModel.DataBase.FireBaseHelper;
import com.stak.mysecretary.Presenter.Model.DataModel.TaiKhoan.DangKy.DangKyImpl;
import com.stak.mysecretary.Presenter.Model.DataModel.TaiKhoan.DangKy.DangKyHelperImpl;
import com.stak.mysecretary.Presenter.Model.UiModel.Interfaces.DangKyUiImpl;

/**
 * Created by Quang Trí on 7/9/2017.
 */

public class DangKyPresenter implements DangKyImpl {
    DangKyHelperImpl dangKyHelperImpl = new FireBaseHelper(this);
    DangKyUiImpl handleUi;

    public DangKyPresenter(DangKyUiImpl handleUi) {
        this.handleUi = handleUi;
    }

    @Override
    public void XuLyDangKy(String email, String password) {
        dangKyHelperImpl.DangKyFireBase(email,password);
    }

    @Override
    public void XuLyDangKyThanhCong() {
        handleUi.DangKyThanhCong();
    }

    @Override
    public void XuLyDangKyThatBai() {
        handleUi.DangKyThatBai();
    }

    @Override
    public void XuLyThongBaoLoi() {
        handleUi.LoiDangKy();
    }


}
