package com.stak.mysecretary.View.Activity;

import android.graphics.Color;
import android.opengl.Visibility;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.stak.mysecretary.Util.BaseFragment;
import com.stak.mysecretary.Util.SupportList;
import com.stak.mysecretary.View.Fragment.HoatDong.ThemFragment;
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

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        toggle.syncState();

        baseFragment = new BaseFragment(this, getSupportFragmentManager());
        baseFragment.ChuyenFragment(new DangNhapFragment(), SupportList.TAG_DANG_NHAP_FRAGMENT, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_main);
        if (fragment instanceof ThemFragment){
            menu.findItem(R.id.tvNgayHienTai_Main).setVisible(false);
            menu.findItem(R.id.ibLuu_ThemHoatDong).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== R.id.ibThem_Main){
            baseFragment.ChuyenFragment(new ThemFragment(), SupportList.TAG_THEM_SU_KIEN_FRAGMENT, true);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() <= 0)
            super.onBackPressed();
        else
            baseFragment.XoaFragment();
    }
}
