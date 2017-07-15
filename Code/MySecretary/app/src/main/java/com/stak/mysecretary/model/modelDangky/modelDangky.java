package com.stak.mysecretary.model.modelDangky;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.stak.mysecretary.presenter.Dangky.presenterDangky;
import com.stak.mysecretary.presenter.Dangky.presenterimpDangky;

/**
 * Created by Quang Trí on 7/9/2017.
 */

public class modelDangky implements modelimpDangky{
    //Khai báo đối tượng
    private FirebaseAuth mAuth;
    presenterimpDangky presenterdk;

    public modelDangky(presenterDangky presenterdk) {
        this.presenterdk = presenterdk;
}


    @Override
    public void dangkyfirebase(final String email, final String password) {
        mAuth=FirebaseAuth.getInstance();
        if (email.isEmpty() && password.isEmpty())
            presenterdk.xulythongbaoloi();
        else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                        presenterdk.xulydangkythanhcong();
                    else
                        presenterdk.xulydangkythatbai();
                }
            });
        }
    }
}
