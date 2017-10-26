package com.stak.mysecretary.View.Activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.stak.mysecretary.Util.BaseFragment;
import com.stak.mysecretary.Util.SupportList;
import com.stak.mysecretary.View.Fragment.TaiKhoan.DangNhapFragment;
import com.stak.mysecretary.R;


public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    private BaseFragment baseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout= (DrawerLayout) findViewById(R.id.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        baseFragment = new BaseFragment(this, getSupportFragmentManager());
        baseFragment.ChuyenFragment(new DangNhapFragment(), SupportList.TAG_DANG_NHAP_FRAGMENT, false);
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() <= 0)
            super.onBackPressed();
        else
            baseFragment.XoaFragment();
    }
}
