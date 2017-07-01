package com.stak.mysecretary;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stak.mysecretary.Adapter.HoatdongAdapter;
import com.stak.mysecretary.database.DBHelper;
import com.stak.mysecretary.database.MyShared;
import com.stak.mysecretary.database.XulyHoatdong;
import com.stak.mysecretary.fragment.GhiChuDialogFragment;
import com.stak.mysecretary.fragment.MainFragment;
import com.stak.mysecretary.model.Hoatdong;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout= (DrawerLayout) findViewById(R.id.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);



        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new MainFragment()).commit();

    }

}
