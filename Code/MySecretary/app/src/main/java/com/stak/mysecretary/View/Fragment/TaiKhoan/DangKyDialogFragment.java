package com.stak.mysecretary.View.Fragment.TaiKhoan;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stak.mysecretary.Presenter.Model.UiModel.Interfaces.DangKyUiImpl;
import com.stak.mysecretary.Presenter.TaiKhoan.Dangky.DangKyPresenter;
import com.stak.mysecretary.R;

/**
 * Created by zzzzz on 7/16/2017.
 */

public class DangKyDialogFragment extends DialogFragment implements DangKyUiImpl, View.OnClickListener, View.OnTouchListener {
    TextView tvTitleVaHuy;
    EditText etEmail;
    EditText etPassword;

    private DangKyPresenter dangKyPresenter;

    public DangKyDialogFragment(){
        dangKyPresenter = new DangKyPresenter(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.fragment_dang_ky, null);
        tvTitleVaHuy = (TextView) root.findViewById(R.id.title_text_fragment_dang_ky);
        etEmail = (EditText) root.findViewById(R.id.email_input_fragment_dang_ky);
        etPassword = (EditText) root.findViewById(R.id.password_input_fragment_dang_ky);

        root.findViewById(R.id.btnDangky).setOnClickListener(this);
        tvTitleVaHuy.setOnTouchListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(root);

        AlertDialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        return dialog;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDangky:
                dangKyPresenter.XuLyDangKy(etEmail.getText().toString(), etPassword.getText().toString());
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getRawX() >= (tvTitleVaHuy.getRight() - tvTitleVaHuy.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                // your action here
                if (!etEmail.getText().toString().isEmpty() || !etPassword.getText().toString().isEmpty()) {
                    AlertDialog.Builder alartbuilder = new AlertDialog.Builder(getActivity());
                    alartbuilder.setMessage("Bạn có chắc muốn hủy đăng ký");
                    alartbuilder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dismiss();
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
                } else
                    dismiss();
                return true;
            }
        }
        return false;
    }

    //Kết quả đăng ký
    @Override
    public void DangKyThanhCong() {
        Toast.makeText(getActivity(), getString(R.string.dang_ky_thanh_cong), Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void DangKyThatBai() {
        Toast.makeText(getActivity(), getString(R.string.dang_ky_that_bai), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void LoiDangKy() {
        Toast.makeText(getActivity(), getString(R.string.loi_dang_ky), Toast.LENGTH_SHORT).show();
    }
}
