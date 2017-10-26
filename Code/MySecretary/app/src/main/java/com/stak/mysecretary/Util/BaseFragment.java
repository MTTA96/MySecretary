package com.stak.mysecretary.Util;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.stak.mysecretary.R;

/**
 * Created by zzzzz on 10/26/2017.
 */

public class BaseFragment {
    private Context context;
    private FragmentManager fm;

    public BaseFragment(Context context, FragmentManager fm) {
        this.context = context;
        this.fm = fm;
    }

    public void ChuyenFragment(Fragment toFragment, String tag, boolean toBackStack){
        FragmentTransaction transaction = fm.beginTransaction();

        if(toBackStack)
            transaction.replace(R.id.content_main, toFragment).addToBackStack(tag).commit();
        else
            transaction.replace(R.id.content_main, toFragment).commit();
    }

    public void XoaFragment(){
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment fragment = fm.findFragmentById(R.id.content_main);
        if (fragment != null){
            transaction.remove(fragment).commit();
            fm.popBackStack();
        }
    }
}
