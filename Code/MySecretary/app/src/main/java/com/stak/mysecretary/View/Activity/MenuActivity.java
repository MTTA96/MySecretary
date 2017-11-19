package com.stak.mysecretary.View.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;

import com.stak.mysecretary.Presenter.Model.DataModel.DataBase.MyShared;
import com.stak.mysecretary.R;

public class MenuActivity extends AppCompatActivity {
    ImageButton ibThoat;

    CheckBox cbCanhan;
    CheckBox cbTkb;

    MyShared myShared;
    public static String KEY_TEN_FILE = "appstatus";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Ánh xạ
        cbCanhan= (CheckBox) findViewById(R.id.checkBoxCaNhan);
        cbTkb= (CheckBox) findViewById(R.id.checkBoxTKB);

        //Đặt lựa chọn mặt định cho checkbox
        myShared = new MyShared(KEY_TEN_FILE, this);
        cbCanhan.setChecked(myShared.getCbCaNhan());
        cbTkb.setChecked(myShared.getCbTKB());

//        ibThoat = (ImageButton) findViewById(R.id.ibThoat_Menu);
//
//        //Xủ lý sự kiện thoát
//        ibThoat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Lựa chọn checkBox truyền sang MainActivity
//                int tinhieu=0;
//                Intent intentThoat = new Intent(MenuActivity.this, MainActivity.class);
//
//                if (cbTkb.isChecked() && cbCanhan.isChecked()) {
//                    myShared.setCbTKB(true);
//                    myShared.setCbCaNhan(true);
//                    tinhieu = 12;
//                }
//                if(!cbTkb.isChecked() && !cbCanhan.isChecked()){
//                    myShared.setCbTKB(false);
//                    myShared.setCbCaNhan(false);
//                }
//                if (cbTkb.isChecked() && !cbCanhan.isChecked()) {
//                    myShared.setCbTKB(true);
//                    myShared.setCbCaNhan(false);
//                    tinhieu = 1;
//                }
//                if (cbCanhan.isChecked() && !cbTkb.isChecked()) {
//                    myShared.setCbTKB(false);
//                    myShared.setCbCaNhan(true);
//                    tinhieu = 2;
//                }
//                Bundle bundle=new Bundle();
//                bundle.putInt("Chon",tinhieu);
//
//                intentThoat.putExtra("Menu",bundle);
//                startActivity(intentThoat);
//                finish();
//            }
//        });
    }
}
