package com.stak.mysecretary.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.stak.mysecretary.Fragment.Dialog.DangKyDialog;
import com.stak.mysecretary.MainActivity;
import com.stak.mysecretary.R;
import com.stak.mysecretary.Handler.DataHandler.Presenter.Dangky.PresenterDangKy;
import com.stak.mysecretary.Handler.DataHandler.Presenter.Dangnhap.PresenterDangnhap;
import com.stak.mysecretary.Handler.UiHandler.Interfaces.DangKyCallBack;
import com.stak.mysecretary.Handler.UiHandler.Interfaces.DangNhapCallBack;

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 */
public class DangNhapFragment extends Fragment implements View.OnClickListener,DangKyCallBack,DangNhapCallBack {
    TextView tvDangky;
    EditText edtEmail;
    EditText edtNewPassword;
    EditText etEmailDangKy;
    EditText etNewPasswordDangKy;
    Button btnHoanTat;
    Button btnDangNhap;
    SignInButton btnGmail;
    ImageButton ibCancel;

    public Dialog dialogDangky;
    private PresenterDangKy presenterDangKy;
    private PresenterDangnhap presenterDangNhap;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final int RC_SIGN_IN=1;

    public DangNhapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //mAuth.addAuthStateListener(mAuthListener);

        View root = inflater.inflate(R.layout.fragment_dang_nhap, container, false);
        //Ánh xạ
        edtEmail= (EditText) root.findViewById(R.id.edtEmail);
        edtNewPassword= (EditText) root.findViewById(R.id.edtPassword);
        tvDangky= (TextView) root.findViewById(R.id.tvDangky);
        btnDangNhap = (Button) root.findViewById(R.id.btnLogin);
        btnGmail= (SignInButton) root.findViewById(R.id.btnLoginGmail);

        //Xử lý onclick
        tvDangky.setOnClickListener(this);
        btnDangNhap.setOnClickListener(this);
        btnGmail.setOnClickListener(this);

        //Khởi tạo giá trị
        presenterDangKy=new PresenterDangKy(this);
        presenterDangNhap=new PresenterDangnhap(this);
        khoitaoGmail();

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvDangky :
//                dialogDangky();
                DangKyDialog dangKyDialog = new DangKyDialog();
                dangKyDialog.show(getActivity().getSupportFragmentManager(), "DangKy");
            case R.id.btnLogin :
                nutDangnhap(edtEmail.getText().toString(),edtNewPassword.getText().toString());
            case R.id.btnLoginGmail :
                signIn();
        }
    }

    @Override
    public void dangkythanhcong() {
        Toast.makeText(getActivity(),"Đăng ký thành công",Toast.LENGTH_SHORT).show();
        dialogDangky.dismiss();
    }

    @Override
    public void dangkythatbai() {
        Toast.makeText(getActivity(),"Đăng ký thất bại",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void thongbaoloi() {
        Toast.makeText(getActivity(),"Không thể bỏ trống email hoặc mật khẩu",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dangnhapthanhcong() {
        Toast.makeText(getActivity(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), MainActivity.class));
    }

    @Override
    public void dangnhapthatbai() {
        Toast.makeText(getActivity(),"Đăng nhập thất bại",Toast.LENGTH_SHORT).show();
        return;
    }

    @Override
    public void thongbaoloidangnhap() {
        Toast.makeText(getActivity(),"Không thể bỏ trống email hoặc mật khẩu",Toast.LENGTH_SHORT).show();
    }

    //Dialog đăng ký
    public void dialogDangky(){
        //Khởi tạo và tạo kích thước cho dialog đăng ký
        dialogDangky=new Dialog(getActivity());
        dialogDangky.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogDangky.setContentView(R.layout.fragment_dang_ky);
        WindowManager.LayoutParams lp=new WindowManager.LayoutParams();
        lp.copyFrom(dialogDangky.getWindow().getAttributes());
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
        dialogDangky.show();
        //Nhấn bên ngoài không mất dialog
        dialogDangky.setCancelable(false);
        dialogDangky.getWindow().setAttributes(lp);

        //xử lý cho dialog đăng ký


        btnHoanTat = (Button) dialogDangky.findViewById(R.id.btnDangky);
        etEmailDangKy = (EditText) dialogDangky.findViewById(R.id.edtEmailDK);
        etNewPasswordDangKy = (EditText) dialogDangky.findViewById(R.id.edtNewpasswordDK);

        ibCancel= (ImageButton) dialogDangky.findViewById(R.id.ibCancel);
        //xử lý cho nut hoàn tất
        btnHoanTat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenterDangKy.xulydangky(etEmailDangKy.getText().toString(), etNewPasswordDangKy.getText().toString());
            }
        });
        //Xử lý cho nut hủy đăng ký
        ibCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etEmailDangKy.getText().toString().isEmpty() || !etNewPasswordDangKy.getText().toString().isEmpty()) {
                    AlertDialog.Builder alartbuilder = new AlertDialog.Builder(getActivity());
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
        presenterDangNhap.xulydangnhap(email,password);
    }

    //Code đăng nhập cho Gmail
    public void khoitaoGmail(){
        mAuth=FirebaseAuth.getInstance();
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    startActivity(new Intent(getActivity(),MainActivity.class));
                }

            }
        };

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient=new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();

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
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Đăng nhập thành công
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            //Đăng nhập thất bại hiện thong báo
                            Toast.makeText(getActivity(), "Đăng nhập thất bại",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
