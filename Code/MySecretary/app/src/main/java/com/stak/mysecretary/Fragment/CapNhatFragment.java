package com.stak.mysecretary.Fragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.stak.mysecretary.DataBase.DBHelper;
import com.stak.mysecretary.DataBase.XulyHoatdong;
import com.stak.mysecretary.MainActivity;
import com.stak.mysecretary.Model.Data.HoatDong;
import com.stak.mysecretary.R;
import com.stak.mysecretary.Util.SupportList;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class CapNhatFragment extends Fragment {
    Spinner spnNhacNho;
    ImageButton ibHuy;
    ImageButton ibLuu;
    EditText etTenHoatDong;
    EditText etDiaDiem;
    EditText etGhiChu;
    TextView tvDateBD;
    TextView tvTimeBD;
    TextView tvTimeKT;

    private Date date;
    private Calendar cal;
    private Calendar cal1;
    private HoatDong hoatDong;
    private HoatDong tempHoatDong;
    private int position;
    private String strNgay;
    private String strGio;
    private String strNgayKT;
    private String strGioKT;
    private DBHelper dbHelper = new DBHelper(getActivity());

    public CapNhatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cap_nhat, container, false);

        tvDateBD= (TextView) root.findViewById(R.id.Date_BatDau_CapNhat);
        tvTimeBD= (TextView) root.findViewById(R.id.Time_BatDau_CapNhat);
        tvTimeKT= (TextView) root.findViewById(R.id.Time_KetThuc_CapNhat);
        etTenHoatDong= (EditText) root.findViewById(R.id.etTen_CapNhat);
        etDiaDiem= (EditText) root.findViewById(R.id.etDiaDiem_CapNhat);
        etGhiChu= (EditText) root.findViewById(R.id.etGhichu_CapNhat);
        spnNhacNho= (Spinner) root.findViewById(R.id.spinnerNhacnho_CapNhat);
        ibHuy = (ImageButton) root.findViewById(R.id.ibHuy_CapNhat);
        ibLuu = (ImageButton) root.findViewById(R.id.ibLuu_CapNhat);


        tvDateBD.setOnClickListener(showDatePickerBD);
        tvTimeBD.setOnClickListener(showTimePickerBD);
        tvTimeKT.setOnClickListener(showTimePickerKT);
        //Xử lý sụ kiện khi nhấn Hủy
        ibHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(CapNhatActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
            }
        });

        //Xử lý lý sự kiện khi nhấn Lưu
        ibLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Kiểm tra giờ bắt đầu có bé hơn kết thúc hay không
                if (KiemTraGioPhut(tvTimeBD.getText().toString(),tvTimeKT.getText().toString())==0)
                {
//                    Toast.makeText(CapNhatActivity.this,"Thời gian không hợp lệ !",Toast.LENGTH_LONG).show();
//                    return;
                }
                XulyHoatdong xulyhd=new XulyHoatdong(dbHelper);
                if(!etTenHoatDong.getText().toString().isEmpty() || tvDateBD.getText().toString().compareTo(strNgay) != 0 || tvTimeBD.getText().toString().compareTo(strGio) != 0){
                    //Xóa, insert
                    xulyhd.XoaHd(hoatDong.getTenhd(), hoatDong.getTgbd());
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
                    if(!etGhiChu.getText().toString().isEmpty())
                        tempHoatDong.setGhichu(etGhiChu.getText().toString());
                    else
                        tempHoatDong.setGhichu(etGhiChu.getHint().toString());
                    xulyhd.Them(tempHoatDong);
                }
                else{
                    xulyhd.XoaHd(hoatDong.getTenhd(), hoatDong.getTgbd());
                    //Cập nhật lại
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
                    if(!etGhiChu.getText().toString().isEmpty())
                        tempHoatDong.setGhichu(etGhiChu.getText().toString());
                    else
                        tempHoatDong.setGhichu(etGhiChu.getHint().toString());
                    xulyhd.Them(tempHoatDong);
                }
//                Toast.makeText(CapNhatActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(CapNhatActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
            }
        });


        //Load dữ liệu
        Intent intent = getActivity().getIntent();
        hoatDong = (HoatDong) intent.getSerializableExtra(SupportList.KEY_HOATDONG);
        position = intent.getIntExtra(SupportList.KEY_POSITION, 0);

        tempHoatDong = hoatDong;

        etTenHoatDong.setHint(hoatDong.getTenhd());
        etDiaDiem.setHint(hoatDong.getDiadiem());
        String test = hoatDong.getGhichu();
        if(!hoatDong.getGhichu().isEmpty())
            etGhiChu.setHint(hoatDong.getGhichu());

        String[] tachTG = hoatDong.getTgbd().split(" ");
        tvDateBD.setText(tachTG[0]);
        tvTimeBD.setText(tachTG[1]);
        strNgay = tachTG[0];
        strGio = tachTG[1];

        tachTG = hoatDong.getTgkt().split(" ");
        tvTimeKT.setText(tachTG[1]);
        strNgayKT = tachTG[0];
        strGioKT = tachTG[1];

        return root;
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
//            DatePickerDialog pic=new DatePickerDialog(
//                    CapNhatActivity.this,
//                    callback, nam, thang, ngay);
//            pic.setTitle("Chọn ngày bắt đầu");
//            pic.show();

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
                    if (hour>9) {
                        if (minute>9) {
                            String s = hour + ":" + minute;
                            tvTimeBD.setText(s);
                            //lưu giờ thực
                            tvTimeBD.setTag(s);
                            //lưu lại giờ
                            cal.set(Calendar.HOUR_OF_DAY, hour);
                            cal.set(Calendar.MINUTE, minute);
                            //gio = cal.getTime();
                        }
                        else {
                            String s = hour + ":" + "0" + minute;
                            tvTimeBD.setText(s);
                            //lưu giờ thực
                            tvTimeBD.setTag(s);
                            //lưu lại giờ
                            cal.set(Calendar.HOUR_OF_DAY, hour);
                            cal.set(Calendar.MINUTE, minute);
                            //gio = cal.getTime();
                        }
                    }
                    else{
                        if (minute>9) {
                            String s = "0" + hour + ":" + minute;
                            tvTimeBD.setText(s);
                            //lưu giờ thực
                            tvTimeBD.setTag(s);
                            //lưu lại giờ
                            cal.set(Calendar.HOUR_OF_DAY, hour);
                            cal.set(Calendar.MINUTE, minute);
                            //gio = cal.getTime();
                        }
                        else{
                            String s = "0"+hour + ":" + "0" + minute;
                            tvTimeBD.setText(s);
                            //lưu giờ thực
                            tvTimeBD.setTag(s);
                            //lưu lại giờ
                            cal.set(Calendar.HOUR_OF_DAY, hour);
                            cal.set(Calendar.MINUTE, minute);
                            //gio = cal.getTime();
                        }
                    }
                }
            };
            String s = tvTimeBD.getText() + "";
            String[] strArr = s.split(":");
            int gio = Integer.parseInt(strArr[0]);
            strArr = strArr[1].split(" ");
            int phut = Integer.parseInt(strArr[0]);
//            TimePickerDialog time = new TimePickerDialog(
//                    CapNhatActivity.this,
//                    callback, gio, phut, true);
//            time.setTitle("Chọn giờ bắt đầu");
//            time.show();
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
                    if (hour>9) {
                        if (minute>9) {
                            String s = hour + ":" + minute;
                            tvTimeKT.setText(s);
                            //lưu giờ thực
                            tvTimeKT.setTag(s);
                            //lưu lại giờ
                            cal1.set(Calendar.HOUR_OF_DAY, hour);
                            cal1.set(Calendar.MINUTE, minute);
                            //gio = cal.getTime();
                        }
                        else {
                            String s = hour + ":" + "0" + minute;
                            tvTimeKT.setText(s);
                            //lưu giờ thực
                            tvTimeKT.setTag(s);
                            //lưu lại giờ
                            cal1.set(Calendar.HOUR_OF_DAY, hour);
                            cal1.set(Calendar.MINUTE, minute);
                            //gio = cal.getTime();
                        }
                    }
                    else{
                        if (minute>9) {
                            String s = "0" + hour + ":" + minute;
                            tvTimeKT.setText(s);
                            //lưu giờ thực
                            tvTimeKT.setTag(s);
                            //lưu lại giờ
                            cal1.set(Calendar.HOUR_OF_DAY, hour);
                            cal1.set(Calendar.MINUTE, minute);
                            //gio = cal.getTime();
                        }
                        else{
                            String s = "0"+hour + ":" + "0" + minute;
                            tvTimeKT.setText(s);
                            //lưu giờ thực
                            tvTimeKT.setTag(s);
                            //lưu lại giờ
                            cal.set(Calendar.HOUR_OF_DAY, hour);
                            cal.set(Calendar.MINUTE, minute);
                            //gio = cal.getTime();
                        }
                    }
                }
            };
            String s = tvTimeKT.getText() + "";
            String[] strArr = s.split(":");
            int gio = Integer.parseInt(strArr[0]);
            strArr = strArr[1].split(" ");
            int phut = Integer.parseInt(strArr[0]);
//            TimePickerDialog time = new TimePickerDialog(
//                    CapNhatActivity.this,
//                    callback, gio, phut, true);
//            time.setTitle("Chọn giờ kết thúc");
//            time.show();
        }
    };

    //Kiểm tra giờ bắt đầu và giờ kết thúc
    public int KiemTraGioPhut(String timeBatDau, String timeKetThuc){
        String[] tachgiophutbd=timeBatDau.split(":");
        String[] tachgiophutkt=timeKetThuc.split(":");
        int giobd=Integer.parseInt(tachgiophutbd[0].toString());
        int phutbd=Integer.parseInt(tachgiophutbd[1].toString());
        int giokt=Integer.parseInt(tachgiophutkt[0].toString());
        int phutkt=Integer.parseInt(tachgiophutkt[1].toString());

        if (giobd>giokt)
        {
            return 0;
        }
        else{
            if (giobd==giokt)
            {
                if (phutbd>phutkt)
                    return 0;
            }
        }
        return 1;
    }
}
