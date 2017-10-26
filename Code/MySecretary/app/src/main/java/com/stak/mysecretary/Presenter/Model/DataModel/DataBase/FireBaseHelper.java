package com.stak.mysecretary.Presenter.Model.DataModel.DataBase;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.stak.mysecretary.Presenter.Model.DataModel.TaiKhoan.DangKy.DangKyImpl;
import com.stak.mysecretary.Presenter.Model.DataModel.TaiKhoan.Dangnhap.DangNhapImpl;
import com.stak.mysecretary.Presenter.Model.DataModel.TaiKhoan.DangKy.DangKyHelperImpl;
import com.stak.mysecretary.Presenter.Model.DataModel.TaiKhoan.Dangnhap.DangNhapHelperImpl;

/**
 * Created by zzzzz on 7/17/2017.
 */

public class FireBaseHelper implements DangNhapHelperImpl, DangKyHelperImpl {
    private FirebaseAuth mAuth;
    private DangNhapImpl dangNhapImpl;
    private DangKyImpl dangKyImpl;

    //Constructor Đăng Nhập
    public FireBaseHelper(DangNhapImpl dangNhapImpl){
        mAuth=FirebaseAuth.getInstance();
        this.dangNhapImpl = dangNhapImpl;
    }

    //Constructor Đăng Ký
    public FireBaseHelper(DangKyImpl dangKyImpl){
        mAuth=FirebaseAuth.getInstance();
        this.dangKyImpl = dangKyImpl;
    }

    //Xử lý Đăng Nhập
    @Override
    public void DangNhapFireBase(final String email, final String password) {
        if (email.isEmpty() && password.isEmpty()){
            dangNhapImpl.XuLyLoiDangNhap();
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        dangNhapImpl.XuLyDangNhapThanhCong();
                    } else {
                        dangNhapImpl.XuLyDangNhapThatBai();
                    }
                }
            });
        }
    }

    //Xử lý Đăng Ký
    @Override
    public void DangKyFireBase(final String email, final String password) {
        mAuth=FirebaseAuth.getInstance();
        if (email.isEmpty() && password.isEmpty())
            dangKyImpl.XuLyThongBaoLoi();
        else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                        dangKyImpl.XuLyDangKyThanhCong();
                    else
                        dangKyImpl.XuLyDangKyThatBai();
                }
            });
        }
    }
}
