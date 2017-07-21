package com.stak.mysecretary.Fragment.HoatDong;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.stak.mysecretary.Fragment.Dialog.ThongBaoDialogFragment;
import com.stak.mysecretary.Presenter.PresenterThemHoatDong;
import com.stak.mysecretary.Presenter.Model.UiModel.ThemHoatDongCallback;
import com.stak.mysecretary.Activity.MainActivity;
import com.stak.mysecretary.Presenter.Model.DataModel.HoatDong.DataCallBack;
import com.stak.mysecretary.R;
import com.stak.mysecretary.Model.HoatDong;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThemFragment extends Fragment implements View.OnClickListener,DataCallBack,ThemHoatDongCallback{
    Spinner spnnhom;
    Spinner spnnhacnho;

    ImageButton ibHuy;
    ImageButton ibLuu;

    EditText etTenHoatDong;
    EditText etDiaDiem;
    EditText etGhiChu;
    TextView tvDateBD;
    TextView tvDateKT;
    TextView tvTimeBD;
    TextView tvTimeKT;

    Button btnT2;
    Button btnT3;
    Button btnT4;
    Button btnT5;
    Button btnT6;
    Button btnT7;
    Button btnCN;

    boolean pressT2=false;
    boolean pressT3=false;
    boolean pressT4=false;
    boolean pressT5=false;
    boolean pressT6=false;
    boolean pressT7=false;
    boolean pressCN=false;

    Calendar cal;
    Calendar cal1;
    Date date;
    Date date1;

    PresenterThemHoatDong presenterThemHoatDong;
    //Hiển thị thông báo
    public void ThongBao(String strNoiDung){
        Toast.makeText(getActivity(), strNoiDung, Toast.LENGTH_SHORT).show();
    }
    public ThemFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =inflater.inflate(R.layout.fragment_them,container,false);
        spnnhom= (Spinner) root.findViewById(R.id.spinnerNhom);
        spnnhacnho= (Spinner) root.findViewById(R.id.spinnerNhacnho);

        etTenHoatDong= (EditText) root.findViewById(R.id.etTen_ThemHoatDong);
        etDiaDiem= (EditText) root.findViewById(R.id.etDiaDiem_ThemHoatDong);
        etGhiChu= (EditText) root.findViewById(R.id.etGhichu);

        tvDateBD= (TextView) root.findViewById(R.id.Date_BatDau);
        tvDateKT= (TextView) root.findViewById(R.id.Date_KetThuc);
        tvTimeBD= (TextView) root.findViewById(R.id.Time_BatDau);
        tvTimeKT= (TextView) root.findViewById(R.id.Time_KetThuc);

        ibHuy = (ImageButton) root.findViewById(R.id.ibHuy_ThemHoatDong);
        ibLuu = (ImageButton) root.findViewById(R.id.ibLuu_ThemHoatDong);

        btnT2= (Button) root.findViewById(R.id.btnT2_layoutthem);
        btnT3= (Button) root.findViewById(R.id.btnT3_layoutthem);
        btnT4= (Button) root.findViewById(R.id.btnT4_layoutthem);
        btnT5= (Button) root.findViewById(R.id.btnT5_layoutthem);
        btnT6= (Button) root.findViewById(R.id.btnT6_layoutthem);
        btnT7= (Button) root.findViewById(R.id.btnT7_layoutthem);
        btnCN= (Button) root.findViewById(R.id.btnCN_layoutthem);

        //Xử lý sự kiện cho button Hủy và Lưu
        ibHuy.setOnClickListener(this);
        ibLuu.setOnClickListener(this);

        //Xử lý các nut thứ
        btnT2.setOnClickListener(this);
        btnT3.setOnClickListener(this);
        btnT4.setOnClickListener(this);
        btnT5.setOnClickListener(this);
        btnT6.setOnClickListener(this);
        btnT7.setOnClickListener(this);
        btnCN.setOnClickListener(this);

        //Xử lý thời gian bắt đầu
        //Ngày:
        //Hiển thị DatePicker
        tvDateBD.setOnClickListener(showDatePickerBD);
        cal=Calendar.getInstance();
        SimpleDateFormat dft=null;
        //Định dạng kiểu ngày / tháng /năm
        dft=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate=dft.format(cal.getTime());


        //Giờ:
        //Hiển thị TimePicker
        tvTimeBD.setOnClickListener(showTimePickerBD);
//       Định dạng giờ phút am/pm
        dft = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String strTime = dft.format(cal.getTime());
        //hiển thị lên giao diện
        dft = new SimpleDateFormat("HH:mm", Locale.getDefault());
        tvTimeBD.setTag(dft.format(cal.getTime()));

        //Xử lý thời gian kết thúc
        //Ngày:
        //Hiển thị DatePicker
        //Hiện ngày giờ hiện tại khi mới chạy lần đầu
        cal1=Calendar.getInstance();

        //Giờ:
        //Hiển thị TimePicker
        tvTimeKT.setOnClickListener(showTimePickerKT);
        //lấy giờ theo 24 giờ
        dft = new SimpleDateFormat("HH:mm", Locale.getDefault());
        tvTimeKT.setTag(dft.format(cal1.getTime()));

        //hiển thi danh sach nhóm trong spiner
        hienthispinernhom();

        etTenHoatDong.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        presenterThemHoatDong=new PresenterThemHoatDong(this);

        return root;

    }

    @Override
    public void ChuyenHoatDong(HoatDong hoatDong, int position, String key) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibHuy_ThemHoatDong:
                //Nếu có nội dung đang thêm sẽ hiện thông báo cho người dùng, nếu không có sẽ thoát không hiện thông báo
                if(!etTenHoatDong.getText().toString().isEmpty() || !etDiaDiem.getText().toString().isEmpty() || !etGhiChu.getText().toString().isEmpty()) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    ThongBaoDialogFragment dialogFragment = ThongBaoDialogFragment.newInstance("Hủy hoạt động đang thêm?", getActivity());
                    dialogFragment.show(fm, "frag_thoat_them");
                }
                else{
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ibLuu_ThemHoatDong:
                HoatDong hoatdong=new HoatDong();
                hoatdong.setTenhd(etTenHoatDong.getText().toString());
                hoatdong.setDiadiem(etDiaDiem.getText().toString());
                hoatdong.setTgbd(tvDateBD.getText().toString()+" "+tvTimeBD.getText().toString());
                hoatdong.setTgkt(tvDateKT.getText().toString()+" "+tvTimeKT.getText().toString());
                String thu=LayThu(pressT2,pressT3,pressT4,pressT5,pressT6,pressT7,pressCN);
                hoatdong.setThu(thu);
                hoatdong.setNhom(spnnhom.getSelectedItem().toString());
                hoatdong.setGhichu(etGhiChu.getText().toString());
                hoatdong.setKeyupdate("0");
                presenterThemHoatDong.XuLyThemHoatDong(hoatdong);

                //Hiện thông báo lưu thành công và quay lại màn hình chính
                ThongBao("Thêm thành công!");
                Intent intentLuu = new Intent(getActivity(), MainActivity.class);
                startActivity(intentLuu);
                break;
            case R.id.btnT2_layoutthem:
                XuLyNutThu(btnT2,pressT2);
                break;
            case R.id.btnT3_layoutthem:
                XuLyNutThu(btnT3,pressT3);
                break;
            case R.id.btnT4_layoutthem:
                XuLyNutThu(btnT4,pressT4);
                break;
            case R.id.btnT5_layoutthem:
                XuLyNutThu(btnT5,pressT5);
                break;
            case R.id.btnT6_layoutthem:
                XuLyNutThu(btnT6,pressT6);
                break;
            case R.id.btnT7_layoutthem:
                XuLyNutThu(btnT7,pressT7);
                break;
            case R.id.btnCN_layoutthem:
                XuLyNutThu(btnCN,pressCN);
                break;
        }
    }

    public String LayThu(boolean t2,boolean t3,boolean t4,boolean t5,boolean t6,boolean t7,boolean cn){
        ArrayList<String> listthu=new ArrayList<>();
        String thu=null;
        if (t2==true){
            listthu.add("2");
        }
        if (t3==true){
            listthu.add("3");
        }
        if (t4==true){
            listthu.add("4");
        }
        if (t5==true){
            listthu.add("5");
        }
        if (t6==true){
            listthu.add("6");
        }
        if (t7==true){
            listthu.add("7");
        }
        if (cn==true){
            listthu.add("cn");
        }

        for (int i=0;i<listthu.size();i++){
            if (Integer.parseInt(listthu.get(i).toString())==Integer.parseInt(listthu.get(listthu.size()-1).toString()))
                thu=listthu.get(i).toString()+" ";
            else
                thu=listthu.get(i).toString();

        }
        return thu;
    }
    public void XuLyNutThu(Button button,boolean kiemtra){
        if (kiemtra==true){
            button.setBackgroundResource(R.drawable.layoutthem_buttonthu_shape);
            kiemtra=false;
        }
        else {
            button.setBackgroundResource(R.drawable.layoutthem_buttonthunhan_shape);
            kiemtra=true;
        }
    }

    //Xử lý Bắt đầu
    // Phương thức show lịch khi nhấn vào TextView hiển thị lịch
    View.OnClickListener showDatePickerBD = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                //Sự kiện khi click vào nút ok sao khi chọn xong
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
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
                    getActivity(),
                    callback, nam, thang, ngay);
            pic.setTitle("         Chọn ngày bắt đầu");
            pic.show();
        }
    };

    //Phương thức show thời gian khi nhấn vào TextView hiển thị đồng hồ
    View.OnClickListener showTimePickerBD = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
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
            String s = tvTimeBD.getTag() + "";
            String strArr[] = s.split(":");
            int gio = Integer.parseInt(strArr[0]);
            int phut = Integer.parseInt(strArr[1]);
            TimePickerDialog time = new TimePickerDialog(
                    getActivity(),
                    callback, gio, phut, true);
            time.setTitle("         Chọn giờ bắt đầu");
            time.show();
        }
    };

    //Xử lý giờ kết thúc
    View.OnClickListener showDatePickerKT = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                //Sự kiện khi click vào nút ok sao khi chọn xong
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    if (day>9) {
                        if (month>9){
                            // Lấy ngày tháng trong lịch đã chọn đưa ra textview ngoài màn hình
                            tvDateKT.setText(day + "/" + (month + 1) + "/" + year);
                            //Lưu lại ngày mới cập nhật
                            cal1.set(year, month, day);
                            date1 = cal1.getTime();
                        }
                        else{
                            String monthtam=String.valueOf(month+1);
                            monthtam="0"+monthtam;
                            tvDateKT.setText(day + "/" + monthtam + "/" + year);
                            //Lưu lại ngày mới cập nhật
                            cal1.set(year, month, day);
                            date1 = cal1.getTime();

                        }
                    }
                    else{
                        if (month>9){
                            String daytam=String.valueOf(day);
                            daytam="0"+daytam;
                            tvDateKT.setText(daytam+ "/" + month+1 + "/" + year);
                            //Lưu lại ngày mới cập nhật
                            cal1.set(year, month, day);
                            date1 = cal.getTime();
                        }
                        else{
                            String daytam=String.valueOf(day);
                            daytam="0"+daytam;
                            String monthtam=String.valueOf(month+1);
                            monthtam="0"+monthtam;
                            tvDateKT.setText(daytam+ "/" + monthtam + "/" + year);
                            //Lưu lại ngày mới cập nhật
                            cal1.set(year, month, day);
                            date1 = cal1.getTime();
                        }
                    }
                }
            };
            String s = tvDateKT.getText()+"";
            //Lấy chuỗi ngày tháng năm đang ở trong Textview ngoài màn hình hiển thị ngược vào trong Dialog
            String strArrtmp[]=s.split("/");
            int ngay=Integer.parseInt(strArrtmp[0]);
            int thang=Integer.parseInt(strArrtmp[1]) - 1;
            int nam=Integer.parseInt(strArrtmp[2]);
            //Hiển thị ra Dialog
            DatePickerDialog pic=new DatePickerDialog(
                    getActivity(),
                    callback, nam, thang, ngay);
            pic.setTitle("         Chọn ngày kết thúc");
            pic.show();
        }
    };

    //Phương thức show thời gian khi nhấn vào TextView hiển thị đồng hồ
    View.OnClickListener showTimePickerKT = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
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
            String s = tvTimeKT.getTag() + "";
            String strArr[] = s.split(":");
            int gio = Integer.parseInt(strArr[0]);
            int phut = Integer.parseInt(strArr[1]);
            TimePickerDialog time = new TimePickerDialog(
                    getActivity(),
                    callback, gio, phut, true);
            time.setTitle("         Chọn giờ kết thúc");
            time.show();
        }
    };

    //Hiển thị danh sách các nhóm trong spiner Chọn nhóm
    private void hienthispinernhom() {
        String[] arr_group = this.getResources().getStringArray(R.array.arr_group);
        ArrayAdapter<String> adapterTime = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arr_group);
        spnnhom.setAdapter(adapterTime);
    }

    @Override
    public void ThemHoatDongThanhCong() {

    }

    @Override
    public void ThongBaoThemSuKienDaCo() {

    }

    @Override
    public void ThongBaoThemLoi() {

    }

    @Override
    public void ThongBaoNhapLoi() {

    }
}
