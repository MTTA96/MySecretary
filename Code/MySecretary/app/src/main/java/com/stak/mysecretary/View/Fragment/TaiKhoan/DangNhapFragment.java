package com.stak.mysecretary.View.Fragment.TaiKhoan;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.stak.mysecretary.Util.BaseFragment;
import com.stak.mysecretary.Util.SupportList;
import com.stak.mysecretary.View.Activity.MainActivity;
import com.stak.mysecretary.View.Fragment.Home.MainFragment;
import com.stak.mysecretary.R;
import com.stak.mysecretary.Presenter.TaiKhoan.Dangnhap.DangNhapPresenter;
import com.stak.mysecretary.Presenter.Model.UiModel.Interfaces.DangNhapUiImpl;

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 */
public class DangNhapFragment extends Fragment implements View.OnClickListener, DangNhapUiImpl {
    TextView tvDangky;
    TextView tvSkip;
    EditText etEmail;
    EditText etPassword;

    private DangNhapPresenter dangNhapPresenter;
    private BaseFragment baseFragment;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final int RC_SIGN_IN = 1;

    public DangNhapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dangNhapPresenter = new DangNhapPresenter(getActivity(), this);
        baseFragment = new BaseFragment(getActivity(), getActivity().getSupportFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dang_nhap, container, false);
        //Ánh xạ
        etEmail = (EditText) root.findViewById(R.id.edtEmail);
        etPassword = (EditText) root.findViewById(R.id.edtPassword);
        tvDangky= (TextView) root.findViewById(R.id.tvDangky);
        tvSkip = (TextView) root.findViewById(R.id.skip_dang_nhap);

        //Xử lý onclick
        tvDangky.setOnClickListener(this);
        tvSkip.setOnClickListener(this);
        root.findViewById(R.id.btnLogin).setOnClickListener(this);
        root.findViewById(R.id.btnLoginGmail).setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvDangky :
                DangKyDialogFragment dangKyDialogFragment = new DangKyDialogFragment();
                dangKyDialogFragment.show(getActivity().getSupportFragmentManager(), "DangKy");
                break;
            case R.id.btnLogin :
                dangNhapPresenter.XulyDangNhap(etEmail.getText().toString(), etPassword.getText().toString());
                break;
            case R.id.btnLoginGmail :
                khoitaoGmail();
                signIn();
                break;
            case R.id.skip_dang_nhap:
                baseFragment.ChuyenFragment(new MainFragment(), SupportList.TAG_HOME_FRAGMENT, false);
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //Code đăng nhập cho Gmail
    public void khoitaoGmail(){
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    startActivity(new Intent(getActivity(),MainActivity.class));
                }

            }
        });

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

    //Xử lý kết quả
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


    //Kết quả đăng nhập
    @Override
    public void DangNhapThanhCong() {
        Toast.makeText(getActivity(), getString(R.string.dang_nhap_thanh_cong), Toast.LENGTH_SHORT).show();
        baseFragment.ChuyenFragment(new MainFragment(), SupportList.TAG_HOME_FRAGMENT, false);
    }

    @Override
    public void DangNhapThatBai() {
        Toast.makeText(getActivity(), getString(R.string.dang_nhap_that_bai), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void LoiDangNhap() {
        Toast.makeText(getActivity(), getString(R.string.loi_dang_nhap), Toast.LENGTH_SHORT).show();
    }

}
