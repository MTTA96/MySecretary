package com.stak.mysecretary;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.stak.mysecretary.database.DBHelper;
import com.stak.mysecretary.database.XulyHoatdong;
import com.stak.mysecretary.model.Hoatdong;
import com.stak.mysecretary.util.SupportList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CapNhatActivity extends AppCompatActivity {
    Spinner spnNhacNho;

    ImageButton ibHuy;
    ImageButton ibLuu;

    DBHelper db=new DBHelper(this);

    EditText etTenHoatDong;
    EditText etDiaDiem;
    EditText etGhiChu;
    TextView tvDateBD;
    TextView tvTimeBD;
    TextView tvTimeKT;

    Date date;
    Calendar cal;
    Calendar cal1;

    Hoatdong hoatdong;
    Hoatdong tempHoatDong;
    int position;
    String strNgay;
    String strGio;
    String strNgayKT;
    String strGioKT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat);

        spnNhacNho= (Spinner) findViewById(R.id.spinnerNhacnho_CapNhat);

        etTenHoatDong= (EditText) findViewById(R.id.etTen_CapNhat);
        etDiaDiem= (EditText) findViewById(R.id.etDiaDiem_CapNhat);
        etGhiChu= (EditText) findViewById(R.id.etGhichu_CapNhat);

        ibHuy = (ImageButton) findViewById(R.id.ibHuy_CapNhat);
        ibLuu = (ImageButton) findViewById(R.id.ibLuu_CapNhat);

        tvDateBD= (TextView) findViewById(R.id.Date_BatDau_CapNhat);
        tvTimeBD= (TextView) findViewById(R.id.Time_BatDau_CapNhat);
        tvTimeKT= (TextView) findViewById(R.id.Time_KetThuc_CapNhat);

        //Xử lý sụ kiện khi nhấn Hủy
        ibHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CapNhatActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Xử lý lý sự kiện khi nhấn Lưu
        ibLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Kiểm tra giờ bắt đầu có bé hơn kết thúc hay không
                if (ktgiophut(tvTimeBD.getText().toString(),tvTimeKT.getText().toString())==0)
                {
                    Toast.makeText(CapNhatActivity.this,"Thời gian không hợp lệ !",Toast.LENGTH_LONG).show();
                    return;
                }
                XulyHoatdong xulyhd=new XulyHoatdong(db);
                if(!etTenHoatDong.getText().toString().isEmpty() || tvDateBD.getText().toString().compareTo(strNgay) != 0 || tvTimeBD.getText().toString().compareTo(strGio) != 0){
                    //Xóa, insert
                    xulyhd.XoaHd(hoatdong.getTenhd(),hoatdong.getTgbd());
                    if(!etTenHoatDong.getText().toString().isEmpty()){
                        tempHoatDong.setTenhd(etTenHoatDong.getText().toString());
                    }
                    else
                        tempHoatDong.setTenhd(etTenHoatDong.getHint().toString());

                    if(!etDiaDiem.getText().toString().isEmpty()){
                        tempHoatDong.setDiadiem(etDiaDiem.getText().toString());
                    }
                    else
                        tempHoatDong.setDiadiem(etDiaDiem.getHint().toString());

                    tempHoatDong.setTgbd(tvDateBD.getText().toString() + " " + tvTimeBD.getText().toString());
                    tempHoatDong.setTgkt(strNgayKT + " " + tvTimeKT.getText().toString());
                    tempHoatDong.setGhichu(etGhiChu.getText().toString());
                    xulyhd.Them(tempHoatDong);
                }
                else{
                    xulyhd.XoaHd(hoatdong.getTenhd(),hoatdong.getTgbd());
                    //Cập nhật lại
                    tempHoatDong.setTenhd(etTenHoatDong.getHint().toString());
                    tempHoatDong.setDiadiem(etDiaDiem.getText().toString());
                    tempHoatDong.setTgbd(tvDateBD.getText().toString()+" "+tvTimeBD.getText().toString());
                    tempHoatDong.setTgkt(strNgayKT + " " + tvTimeKT.getText().toString());
                    tempHoatDong.setGhichu(etGhiChu.getText().toString());
                    xulyhd.Them(tempHoatDong);
                }
                Toast.makeText(CapNhatActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CapNhatActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //Load dữ liệu
        Intent intent = getIntent();
        hoatdong = (Hoatdong) intent.getSerializableExtra(SupportList.KEY_HOATDONG);
        position = intent.getIntExtra(SupportList.KEY_POSITION, 0);

        tempHoatDong = hoatdong;

        etTenHoatDong.setHint(hoatdong.getTenhd());
        etDiaDiem.setHint(hoatdong.getDiadiem());
        String test = hoatdong.getGhichu();
        if(!hoatdong.getGhichu().isEmpty())
            etGhiChu.setHint(hoatdong.getGhichu());

        String[] tachTG = hoatdong.getTgbd().split(" ");
        tvDateBD.setText(tachTG[0]);
        tvTimeBD.setText(tachTG[1]);
        strNgay = tachTG[0];
        strGio = tachTG[1];

        tachTG = hoatdong.getTgkt().split(" ");
        tvTimeKT.setText(tachTG[1]);
        strNgayKT = tachTG[0];
        strGioKT = tachTG[1];

        tvDateBD.setOnClickListener(showDatePickerBD);
        tvTimeBD.setOnClickListener(showTimePickerBD);
        tvTimeKT.setOnClickListener(showTimePickerKT);
    }

    //Hiện Datepicker khi nhấn vào chọn ngày
    View.OnClickListener showDatePickerBD = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                //Sự kiện khi click vào nút ok sao khi chọn xong
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    cal=Calendar.getInstance();
                    if (day>9) {
                        if (month>9){
                            // Lấy ngày tháng trong lịch đã chọn đưa ra textview ngoài màn hình
                            tvDateBD.setText(day + "/" + (month + 1) + "/" + year);
                            //Lưu lại ngày mới cập nhật
                            cal.set(year, month, day);
                            date = cal.getTime();
                        }
                        else{
                            String monthtam=String.valueOf(month+1);
                            monthtam="0"+monthtam;
                            tvDateBD.setText(day + "/" + monthtam + "/" + year);
                            //Lưu lại ngày mới cập nhật
                            cal.set(year, month, day);
                            date = cal.getTime();

                        }
                    }
                    else{
                        if (month>9){
                            String daytam=String.valueOf(day);


                            daytam="0"+daytam;
                            tvDateBD.setText(daytam+ "/" + month+1 + "/" + year);
                            //Lưu lại ngày mới cập nhật
                            cal.set(year, month, day);
                            date = cal.getTime();
                        }
                        else{
                            String daytam=String.valueOf(day);
                            daytam="0"+daytam;
                            String monthtam=String.valueOf(month+1);
                            monthtam="0"+monthtam;
                            tvDateBD.setText(daytam+ "/" + monthtam + "/" + year);
                            //Lưu lại ngày mới cập nhật
                            cal.set(year, month, day);
                            date = cal.getTime();
                        }

                    }
                }
            };
            String s=tvDateBD.getText()+"";
            //Lấy chuỗi ngày tháng năm đang ở trong Textview ngoài màn hình hiển thị ngược vào trong Dialog
            String strArrtmp[]=s.split("/");
            int ngay=Integer.parseInt(strArrtmp[0]);
            int thang=Integer.parseInt(strArrtmp[1]) - 1;
            int nam=Integer.parseInt(strArrtmp[2]);
            //Hiển thị ra Dialog
            DatePickerDialog pic=new DatePickerDialog(
                    CapNhatActivity.this,
                    callback, nam, thang, ngay);
            pic.setTitle("Chọn ngày bắt đầu");
            pic.show();

        }
    };

    //Hiện Timepicker khi nhấn vào giờ bắt đầu
    View.OnClickListener showTimePickerBD = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    cal=Calendar.getInstance();
                    //Xử lý lưu giờ và AM,PM
                    String s = hour + ":" + minute;
                    tvTimeBD.setText(s);
                    //lưu giờ thực
                    tvTimeBD.setTag(s);
                    //lưu lại giờ
                    cal.set(Calendar.HOUR_OF_DAY, hour);
                    cal.set(Calendar.MINUTE, minute);
                    //gio = cal.getTime();
                }
            };
            String s = tvTimeBD.getText() + "";
            String[] strArr = s.split(":");
            int gio = Integer.parseInt(strArr[0]);
            strArr = strArr[1].split(" ");
            int phut = Integer.parseInt(strArr[0]);
            TimePickerDialog time = new TimePickerDialog(
                    CapNhatActivity.this,
                    callback, gio, phut, true);
            time.setTitle("Chọn giờ bắt đầu");
            time.show();
        }
    };

    //Hiện Timepicker khi nhấn vào ngày kết thúc
    View.OnClickListener showTimePickerKT = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    cal1=Calendar.getInstance();
                    //Xử lý lưu giờ và AM,PM
                    String s = hour + ":" + minute;
                    tvTimeKT.setText(s);
                    //lưu giờ thực
                    tvTimeKT.setTag(s);
                    //lưu lại giờ
                    cal1.set(Calendar.HOUR_OF_DAY, hour);
                    cal1.set(Calendar.MINUTE, minute);
                    //gio = cal.getTime();
                }
            };
            String s = tvTimeKT.getText() + "";
            String[] strArr = s.split(":");
            int gio = Integer.parseInt(strArr[0]);
            strArr = strArr[1].split(" ");
            int phut = Integer.parseInt(strArr[0]);
            TimePickerDialog time = new TimePickerDialog(
                    CapNhatActivity.this,
                    callback, gio, phut, true);
            time.setTitle("Chọn giờ kết thúc");
            time.show();
        }
    };

    //Kiểm tra giờ bắt đầu và giờ kết thúc
    public int ktgiophut(String giophutbd,String giophutkt){
        String[] tachgiophutbd=giophutbd.split(":");
        String[] tachgiophutkt=giophutkt.split(":");
        int giobd=Integer.parseInt(tachgiophutbd[0].toString());
        int phutbd=Integer.parseInt(tachgiophutbd[1].toString());
        int giokt=Integer.parseInt(tachgiophutkt[0].toString());
        int phutkt=Integer.parseInt(tachgiophutkt[1].toString());

        if (giobd>giokt)
        {
            return 0;
        }
        else{
            if (phutbd>phutkt)
                return 0;
        }
        return 1;
    }
}
