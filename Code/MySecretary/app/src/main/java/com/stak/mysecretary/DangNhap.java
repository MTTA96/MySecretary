package com.stak.mysecretary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.stak.mysecretary.presenter.Dangky.presenterDangky;
import com.stak.mysecretary.presenter.Dangnhap.presenterDangnhap;
import com.stak.mysecretary.view.viewDangky.viewDangky;
import com.stak.mysecretary.view.viewDangnhap.viewDangnhap;


public class Dangnhap extends AppCompatActivity implements View.OnClickListener,viewDangky,viewDangnhap {
    Button btnHoantat;
    static EditText edtemailDK;
    static EditText edtNewpasswordDK;
    presenterDangky presenterdk;
    presenterDangnhap presenterDn;
    ImageButton ibCancel;
    EditText edtEmail;
    EditText edtNewPassword;
    Button btnDangnhap;
    //Biến xu lý code chodang98 nhập gmail
    SignInButton btnGsignin;
    private static final int RC_SIGN_IN=1;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    SignInButton btnGmail;

    TextView tvDangky;
    public Dialog dialogDangky;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_dangnhap);

        edtEmail= (EditText) findViewById(R.id.edtEmail);
        edtNewPassword= (EditText) findViewById(R.id.edtPassword);

        tvDangky= (TextView) findViewById(R.id.tvDangky);

        btnDangnhap= (Button) findViewById(R.id.btnLogin);
        btnGmail= (SignInButton) findViewById(R.id.btnLoginGmail);

        //Khởi tạo
        presenterdk=new presenterDangky(this);
        presenterDn=new presenterDangnhap(this);

        tvDangky.setOnClickListener(this);
        btnDangnhap.setOnClickListener(this);

        //khởi tạo cho code xử lý button gmail
        khoitaoGmail();
        //click button gmail
        btnGmail.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvDangky :
                dialogDangky();
            case R.id.btnLogin :
                nutDangnhap(edtEmail.getText().toString(),edtNewPassword.getText().toString());
            case R.id.btnLoginGmail :
                signIn();
        }
    }

    @Override
    public void dangkythanhcong() {
        Toast.makeText(this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();
        dialogDangky.dismiss();
    }

    @Override
    public void dangkythatbai() {
        Toast.makeText(this,"Đăng ký thất bại",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void thongbaoloi() {
        Toast.makeText(this,"Không thể bỏ trống email hoặc mật khẩu",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dangnhapthanhcong() {
        Toast.makeText(this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public void dangnhapthatbai() {
        Toast.makeText(this,"Đăng nhập thất bại",Toast.LENGTH_SHORT).show();
        return;
    }

    @Override
    public void thongbaoloidangnhap() {
        Toast.makeText(this,"Không thể bỏ trống email hoặc mật khẩu",Toast.LENGTH_SHORT).show();
    }

    //Dialog đăng ký
    public void dialogDangky(){
        //Khởi tạo và tạo kích thước cho dialog đăng ký
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

        //xử lý cho dialog đăng ký


        btnHoantat= (Button) dialogDangky.findViewById(R.id.btnDangky);
        edtemailDK= (EditText) dialogDangky.findViewById(R.id.edtEmailDK);
        edtNewpasswordDK= (EditText) dialogDangky.findViewById(R.id.edtNewpasswordDK);

        ibCancel= (ImageButton) dialogDangky.findViewById(R.id.ibCancel);
        //xử lý cho nut hoàn tất
        btnHoantat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenterdk.xulydangky(edtemailDK.getText().toString(),edtNewpasswordDK.getText().toString());
            }
        });
        //Xử lý cho nut hủy đăng ký
        ibCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (!edtemailDK.getText().toString().isEmpty() || !edtNewpasswordDK.getText().toString().isEmpty()) {
                        AlertDialog.Builder alartbuilder = new AlertDialog.Builder(Dangnhap.this);
                        alartbuilder.setMessage("Bạn có chắc muốn hủy đăng ký");
                        alartbuilder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialogDangky.dismiss();
                            }
                        });
                        alartbuilder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = alartbuilder.create();
                        dialog.show();
                    }
                    else
                        dialogDangky.dismiss();
                }
        });
    }

    //xử lý cho nut đăng nhập
    public void nutDangnhap(String email,String password){
        presenterDn.xulydangnhap(email,password);
    }

    //Code đăng nhập cho Gmail
    public void khoitaoGmail(){
        mAuth=FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    startActivity(new Intent(Dangnhap.this,MainActivity.class));
                }

            }
        };

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient=new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(Dangnhap.this,"Error",Toast.LENGTH_SHORT).show();

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Đăng nhập thành công
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            //Đăng nhập thất bại hiện thong báo
                            Toast.makeText(Dangnhap.this, "Đăng nhập thất bại",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}
