package com.stak.mysecretary.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.stak.mysecretary.MainActivity;

/**
 * Created by zzzzz on 4/6/2017.
 */

public class ThongBaoDialogFragment extends DialogFragment {
    Activity activity;
    private static ThongBaoDialogFragment instance = null;
    String Title;

    //Hàm khởi tạo cho class ThongBaoDialogFragment
    public static ThongBaoDialogFragment newInstance(String title, Activity activity) {
        if(instance == null){
            instance = new ThongBaoDialogFragment();
        }

        instance.Title = title;
        instance.activity = activity;
        return instance;
    }

    //Tạo Dialog

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(Title)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(activity, MainActivity.class);
                        startActivity(intent);
                        activity.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                })
                .create();
    }
}
