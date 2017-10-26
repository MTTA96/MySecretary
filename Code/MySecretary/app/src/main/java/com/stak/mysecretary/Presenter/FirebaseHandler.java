package com.stak.mysecretary.Presenter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.firebase.database.Transaction;
import com.stak.mysecretary.R;

/**
 * Created by zzzzz on 10/8/2017.
 */

public class FirebaseHandler {
    private Context context;
    private FragmentManager fragmentManager;

    public FirebaseHandler(Context context, FragmentManager fm){
        this.context = context;
        fragmentManager = fm;
    }

    public void ChuyenFragment(Fragment toFragment, String tag, boolean toBackStack){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (toBackStack)
            transaction.replace(R.id.content_main, toFragment).addToBackStack(tag);
        else
            transaction.replace(R.id.content_main, toFragment);
    }
}
