package com.stak.mysecretary;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Dangnhap extends AppCompatActivity implements View.OnClickListener {
    TextView tvDangky;
    public Dialog dialogDangky;
    //Đăng nhập FB
    LoginButton btnLoginFB;
    CallbackManager callbackManager;
    String TAG="Dangnhap";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_dangnhap);

        tvDangky= (TextView) findViewById(R.id.tvDangky);

        tvDangky.setOnClickListener(this);

        //Đăng nhập fb
        callbackManager=CallbackManager.Factory.create();
        mAuth=FirebaseAuth.getInstance();
        btnLoginFB= (LoginButton) findViewById(R.id.btnLoginFB);
        btnLoginFB.setReadPermissions("email", "public_profile");
        btnLoginFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
    }
    //Đăng nhập với fb
    private void handleFacebookAccessToken(AccessToken accessToken){
        Log.d(TAG, "handleFacebookAccessToken:" + accessToken);

        AuthCredential credential= FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(Dangnhap.this,user.getEmail().toString(),Toast.LENGTH_SHORT).show();

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                    Toast.makeText(Dangnhap.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvDangky :
                dialogDangky=new Dialog(Dangnhap.this);
                dialogDangky.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogDangky.setContentView(R.layout.activity_dialog_dangky);
                WindowManager.LayoutParams lp=new WindowManager.LayoutParams();
                lp.copyFrom(dialogDangky.getWindow().getAttributes());
                lp.width=WindowManager.LayoutParams.MATCH_PARENT;
                lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
                dialogDangky.show();
                //Nhấn bên ngoài không mất dialog
                dialogDangky.setCancelable(false);
                dialogDangky.getWindow().setAttributes(lp);
                ImageButton ibCancel= (ImageButton) dialogDangky.findViewById(R.id.ibCancel);
                ibCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogDangky.dismiss();
                    }
                });
        }
    }
}
