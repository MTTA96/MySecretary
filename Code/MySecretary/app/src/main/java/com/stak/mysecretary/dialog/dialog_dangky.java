package com.stak.mysecretary.dialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.stak.mysecretary.R;

import java.util.ArrayList;
import java.util.Calendar;

public class dialog_dangky extends AppCompatActivity implements View.OnClickListener {
    Spinner spnDate,spnMonth,spnYear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_dangky);

        //ánh xạ
        spnDate= (Spinner) findViewById(R.id.spnDate);
        spnMonth= (Spinner) findViewById(R.id.spnMonth);
        spnYear= (Spinner) findViewById(R.id.spnYear);

        //Khởi tạo
    }

    //đặt dữ liêu cho spinner ngay tháng năm sinh
    private void setSpinner(){
        ArrayList<String> date =new ArrayList<>();
        ArrayList<String> month =new ArrayList<>();
        ArrayList<String> year =new ArrayList<>();

        int thisyear= Calendar.getInstance().get(Calendar.YEAR);

        for (int i=1900;i<= thisyear;i++){
            year.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,year);
        spnYear.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
}
