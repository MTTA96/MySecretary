package com.stak.mysecretary.Fragment;


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

import com.stak.mysecretary.MainActivity;
import com.stak.mysecretary.R;
import com.stak.mysecretary.DataBase.DBHelper;
import com.stak.mysecretary.DataBase.XulyHoatdong;
import com.stak.mysecretary.Interfaces.DataCallBack;
import com.stak.mysecretary.Model.Data.HoatDong;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThemFragment extends Fragment implements View.OnClickListener,DataCallBack{
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


    DBHelper db;
    Calendar cal;
    Calendar cal1;
    Date date;
    Date date1;
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

        db=new DBHelper(getActivity());

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
        boolean pressT2=false;
        boolean pressT3=false;
        boolean pressT4=false;
        boolean pressT5=false;
        boolean pressT6=false;
        boolean pressT7=false;
        boolean pressCN=false;

        //Xử lý sự kiện cho button Hủy và Lưu
        ibHuy.setOnClickListener(this);
        ibLuu.setOnClickListener(this);

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
                //Kiểm tra người dùng đã nhập đủ thông tin cần thiết chưa
                if(etTenHoatDong.getText().toString().isEmpty() && tvDateBD.getText().toString().isEmpty() && tvTimeBD.getText().toString().isEmpty() && tvTimeKT.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Chưa nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Kiểm tra giờ bắt đầu có bé hơn kết thúc hay không
                if (ktgiophut(tvTimeBD.getText().toString(),tvTimeKT.getText().toString())==0)
                {
                    Toast.makeText(getActivity(),"Thời gian không hợp lý !",Toast.LENGTH_LONG).show();
                    return;
                }

                //Kiểm tra khoảng thời gian này đã có hoạt động chưa
                if (ktKhungTG(tvDateBD.getText().toString(),tvTimeBD.getText().toString(),tvTimeKT.getText().toString())==0)
                {
                    Toast.makeText(getActivity(),"Khoảng thời gian này đã có hoạt động !",Toast.LENGTH_LONG).show();
                    return;
                }

                //Kiểm tra hoạt động này đã có chưa
                XulyHoatdong xulyhd=new XulyHoatdong(db);
                int kt=xulyhd.kttenhd(etTenHoatDong.getText().toString(),tvDateBD.getText().toString()+" "+tvTimeBD.getText().toString());
                if(kt>0){
                    Toast.makeText(getActivity(),"Hoạt động này đã có !",Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    //Thêm hoạt động vào database khi nhấn lưu
                    HoatDong hd = new HoatDong();
                    hd.setTenhd(etTenHoatDong.getText().toString());
                    hd.setDiadiem(etDiaDiem.getText().toString());
                    hd.setTgbd(tvDateBD.getText().toString() + " " + tvTimeBD.getText().toString());
                    hd.setTgkt(tvDateBD.getText().toString() + " " + tvTimeKT.getText().toString());
                    hd.setNhom(spnnhom.getSelectedItem().toString());
                    hd.setGhichu(etGhiChu.getText().toString());
                    XulyHoatdong them = new XulyHoatdong(db);
                    them.Them(hd);
                    //Hiện thông báo lưu thành công và quay lại màn hình chính
                    ThongBao("Thêm thành công!");
                    Intent intentLuu = new Intent(getActivity(), MainActivity.class);
                    startActivity(intentLuu);
                }

                break;
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

    //Kiểm tra Tgbd > Tgkt
    public int kiemtrabdkt(String ngaythangbd,String ngaythangkt,String giophutbd,String giophutkt )
    {
        String[] tachtgbd=ngaythangbd.split("/");
        String[] tachtgkt=ngaythangkt.split("/");
        String[] tachgiobd=giophutbd.split(":");
        String[] tachgiokt=giophutkt.split(":");
        int ngaybd=Integer.parseInt(tachtgbd[0].toString());
        int ngaykt=Integer.parseInt(tachtgkt[0].toString());
        int thangbd=Integer.parseInt(tachtgbd[1].toString());
        int thangkt=Integer.parseInt(tachtgkt[1].toString());
        int nambd=Integer.parseInt(tachtgbd[2].toString());
        int namkt=Integer.parseInt(tachtgkt[2].toString());

        if (nambd>namkt)
            return 0;
        else{
            if (thangbd>thangkt)
                return 0;
            else
            if (ngaybd>ngaykt)
                return 0;
        }
        return 1;
    }

    //Kiểm tra giờ bắt đầu và giờ kết thúc
    public int ktgiophut(String giophutbd,String giophutkt){
        if(giophutbd.isEmpty() && giophutkt.isEmpty()){
            return 0;
        }
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
            if (giobd==giokt)
            {
                if (phutbd>phutkt)
                    return 0;
            }
        }
        return 1;
    }

    //Kiểm tra khung thời gian này đã có hoạt động chưa
    public int ktKhungTG(String ngay, String giophutbd, String giophutkt)
    {
        //Tach giờ phút của hoạt động người dùng nhập
        String[] tachgiophutbd=giophutbd.split(":");
        String[] tachgiophutkt=giophutkt.split(":");
        int giobd=Integer.parseInt(tachgiophutbd[0]);
        int phutbd=Integer.parseInt(tachgiophutbd[1]);
        int giokt=Integer.parseInt(tachgiophutkt[0]);
        int phutkt=Integer.parseInt(tachgiophutkt[1]);

        XulyHoatdong xulyhd=new XulyHoatdong(db);
        ArrayList<HoatDong> listhd=new ArrayList<HoatDong>();
        listhd=xulyhd.laydstheongay(ngay);

        if(listhd.size() == 0){
            return 1;
        }
        else {
            for (int i = 0; i < listhd.size(); i++) {

                //Tách ngày với giờ của dữ liệu lấy từ Database
                String[] tachTGBDDB = listhd.get(i).getTgbd().split(" ");
                String[] tachTGKTDB = listhd.get(i).getTgkt().split(" ");
                //Tách giờ với phút sau từ dữ liệu của Database
                String[] tachgiophutbddb = tachTGBDDB[1].split(":");
                String[] tachgiophutktdb = tachTGKTDB[1].split(":");

                int giobddb = Integer.parseInt(tachgiophutbddb[0]);
                int phutbddb = Integer.parseInt(tachgiophutbddb[1]);
                int gioktdb = Integer.parseInt(tachgiophutktdb[0]);
                int phutktdb = Integer.parseInt(tachgiophutktdb[1]);

                if(giobd*60+phutbd > giobddb*60+phutbddb &&  giokt*60+phutkt < gioktdb*60+phutktdb)
                    return 0;
                if(giobd*60+phutbd < giobddb*60+phutbddb &&  giokt*60+phutkt > gioktdb*60+phutktdb)
                    return 0;
                if(giobd*60+phutbd > giobddb*60+phutbddb && giobd*60+phutbd< gioktdb*60+phutktdb || giokt*60+phutkt > giobddb*60+phutbddb && giokt*60+phutkt< gioktdb*60+phutktdb)
                    return 0;
            }
        }
        return 1;
    }
}
