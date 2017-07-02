package com.stak.mysecretary.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;


/**
 * Created by ADMIN on 4/6/2017.
 */

public class GhiChuDialogFragment extends DialogFragment {
    private static GhiChuDialogFragment instance = null;
    String Title;
    String strNoiDungGhiChu;

    Activity myActivity;

    public static GhiChuDialogFragment newInstance(String title, String ndGhiChu, Activity activity) {
        if(instance == null){
            instance = new GhiChuDialogFragment();
        }

        instance.Title = title;
        instance.strNoiDungGhiChu = ndGhiChu;
        instance.myActivity = activity;
        return instance;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(Title)
                .setMessage(strNoiDungGhiChu)
                .create();
    }
}
