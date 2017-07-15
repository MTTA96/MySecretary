package com.stak.mysecretary.model.modelDangnhap;

import android.support.annotation.NonNull;
import android.system.ErrnoException;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.stak.mysecretary.presenter.Dangnhap.presenterDangnhap;
import com.stak.mysecretary.presenter.Dangnhap.presenterimpDangnhap;

/**
 * Created by Quang Trí on 7/10/2017.
 */

public class modelDangnhap implements modelipmDangnhap {
    presenterimpDangnhap presenterimpDn;

    public modelDangnhap(presenterimpDangnhap presenterimpDn) {
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
