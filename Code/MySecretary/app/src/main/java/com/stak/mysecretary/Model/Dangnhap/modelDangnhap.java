package com.stak.mysecretary.Model.Dangnhap;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.stak.mysecretary.Handler.DataHandler.Interfaces.PresenterImplDangnhap;
import com.stak.mysecretary.Model.Dangnhap.DangNhapImpl;

/**
 * Created by Quang Trí on 7/10/2017.
 */

public class ModelDangNhap implements DangNhapImpl {
    PresenterImplDangnhap presenterimpDn;

    public ModelDangNhap(PresenterImplDangnhap presenterimpDn) {
        this.presenterimpDn = presenterimpDn;
    }

    @Override
    public void dangnhapfirebase(final String email,final String password) {
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        if (email.isEmpty() && password.isEmpty()){
            presenterimpDn.xulyloidangnhap();
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        presenterimpDn.xulydangnhaptc();
                    } else {
                        presenterimpDn.xulydangnhaptb();
                    }
                }
            });
        }
    }
}
