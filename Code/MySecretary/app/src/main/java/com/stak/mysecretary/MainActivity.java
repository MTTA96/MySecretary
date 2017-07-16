package com.stak.mysecretary;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.stak.mysecretary.Fragment.DangNhapFragment;
import com.stak.mysecretary.Fragment.MainFragment;


public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout= (DrawerLayout) findViewById(R.id.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new DangNhapFragment()).commit();

    }

}
