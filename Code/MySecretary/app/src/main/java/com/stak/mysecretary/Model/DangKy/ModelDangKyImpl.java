package com.stak.mysecretary.Model.DangKy;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.stak.mysecretary.Handler.DataHandler.Presenter.Dangky.PresenterDangKy;
import com.stak.mysecretary.Handler.DataHandler.Interfaces.PresenterImplDangKy;

/**
 * Created by Quang Trí on 7/9/2017.
 */

public class ModelDangKyImpl implements DangKyImpl {
    //Khai báo đối tượng
    private FirebaseAuth mAuth;
    private PresenterImplDangKy presenterImplDangKy;

    public ModelDangKyImpl(PresenterDangKy presenterImplDangKy) {
        this.presenterImplDangKy = presenterImplDangKy;
}

    @Override
    public void DangKyFireBase(final String email, final String password) {
        mAuth=FirebaseAuth.getInstance();
        if (email.isEmpty() && password.isEmpty())
            presenterImplDangKy.xulythongbaoloi();
        else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                        presenterImplDangKy.xulydangkythanhcong();
                    else
                        presenterImplDangKy.xulydangkythatbai();
                }
            });
        }
    }
}
