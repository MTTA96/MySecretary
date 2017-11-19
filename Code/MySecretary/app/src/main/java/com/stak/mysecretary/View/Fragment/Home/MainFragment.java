package com.stak.mysecretary.View.Fragment.Home;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stak.mysecretary.Presenter.HomePresenter;
import com.stak.mysecretary.Util.BaseFragment;
import com.stak.mysecretary.Util.SupportList;
import com.stak.mysecretary.View.Fragment.Dialog.GhiChuDialogFragment;
import com.stak.mysecretary.View.Fragment.HoatDong.ThemFragment;
import com.stak.mysecretary.Presenter.Model.UiModel.Adapter.HoatdongAdapter;
import com.stak.mysecretary.View.Activity.MainActivity;
import com.stak.mysecretary.View.Activity.MenuActivity;
import com.stak.mysecretary.R;
import com.stak.mysecretary.Presenter.Model.DataModel.DataBase.DBHelper;
import com.stak.mysecretary.Presenter.Model.DataModel.DataBase.MyShared;
import com.stak.mysecretary.Presenter.Model.DataModel.DataBase.XulyHoatdong;
import com.stak.mysecretary.Presenter.Model.DataModel.HoatDong.DataCallBack;
import com.stak.mysecretary.Model.HoatDong;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener, DataCallBack {
    ListView lvHoatDong;
    LinearLayout menu;

    //Các cột ngày trong tuần
    RelativeLayout cotThu2, cotThu3, cotThu4, cotThu5, cotThu6, cotThu7, cotChuNhat;
    LinearLayout cotThu2Top, cotThu3Top, cotThu4Top, cotThu5Top, cotThu6Top, cotThu7Top, cotChuNhatTop;

    //Các nút có trong chương trình
    ImageButton ibMenu;
    ImageButton ibThem;
    ImageButton ibHomNay;
    ImageButton ibTuanTruoc;
    ImageButton ibTuanSau;

    //Các TextView hiển thị nội dung trên lịch
    TextView tvNgayHienTai;
    TextView tvNgayDuocChon;
    TextView tvNgayThu2, tvNgayThu3, tvNgayThu4, tvNgayThu5, tvNgayThu6, tvNgayThu7, tvNgayChuNhat;

    MainActivity mainActivity;
    private HomePresenter homePresenter;
    private Calendar lich = Calendar.getInstance();
    private Date dateHienTai;
    private ArrayList<HoatDong> listHoatDong;
    private MyShared myShared;
    private int eventIndex;
    private int countIndex;
    //Biến đếm để set màu cho cột thú
    private int count = 0;
    private BaseFragment baseFragment;
    private DBHelper db;
    private HoatDong hoatDongDuocChon;
    private int posHoatDongDuocChon;

    public static String KEY_NGAY_DUOC_CHON = "";


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePresenter = new HomePresenter(getActivity());
        baseFragment = new BaseFragment(getActivity(), getActivity().getSupportFragmentManager());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        //2. Ánh xạ
        cotThu2 = (RelativeLayout) root.findViewById(R.id.event_column_thu2);
        cotThu3 = (RelativeLayout) root.findViewById(R.id.event_column_thu3);
        cotThu4 = (RelativeLayout) root.findViewById(R.id.event_column_thu4);
        cotThu5 = (RelativeLayout) root.findViewById(R.id.event_column_thu5);
        cotThu6 = (RelativeLayout) root.findViewById(R.id.event_column_thu6);
        cotThu7 = (RelativeLayout) root.findViewById(R.id.event_column_thu7);
        cotChuNhat = (RelativeLayout) root.findViewById(R.id.event_column_cn);

        cotThu2Top = (LinearLayout) root.findViewById(R.id.event_column_thu2_top);
        cotThu3Top = (LinearLayout) root.findViewById(R.id.event_column_thu3_top);
        cotThu4Top = (LinearLayout) root.findViewById(R.id.event_column_thu4_top);
        cotThu5Top = (LinearLayout) root.findViewById(R.id.event_column_thu5_top);
        cotThu6Top = (LinearLayout) root.findViewById(R.id.event_column_thu6_top);
        cotThu7Top = (LinearLayout) root.findViewById(R.id.event_column_thu7_top);
        cotChuNhatTop = (LinearLayout) root.findViewById(R.id.event_column_cn_top);

        ibThem = (ImageButton) root.findViewById(R.id.ibThem_Main);
        ibHomNay = (ImageButton) root.findViewById(R.id.ibHomNay_Main);
        ibTuanSau = (ImageButton) root.findViewById(R.id.ibTuanSau_Main);
        ibTuanTruoc = (ImageButton) root.findViewById(R.id.ibTuanTruoc_Main);

        tvNgayHienTai = (TextView) root.findViewById(R.id.tvNgayHienTai_Main);
        tvNgayDuocChon = (TextView) root.findViewById(R.id.tvNgayDuocChon_Main);
        tvNgayThu2 = (TextView) root.findViewById(R.id.tvNgayThu2);
        tvNgayThu3 = (TextView) root.findViewById(R.id.tvNgayThu3);
        tvNgayThu4 = (TextView) root.findViewById(R.id.tvNgayThu4);
        tvNgayThu5 = (TextView) root.findViewById(R.id.tvNgayThu5);
        tvNgayThu6 = (TextView) root.findViewById(R.id.tvNgayThu6);
        tvNgayThu7 = (TextView) root.findViewById(R.id.tvNgayThu7);
        tvNgayChuNhat = (TextView) root.findViewById(R.id.tvNgayChuNhat);

        lvHoatDong = (ListView) root.findViewById(R.id.listHoatDongMain);

        db=new DBHelper(getContext());
        mainActivity=new MainActivity();

        //3. Xử lý sự kiện
        lvHoatDong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Lấy ghi chú từ hoạt động được chọn
                XulyHoatdong xulyHD=new XulyHoatdong(db);
                ArrayList<HoatDong> listHDDuocChon=new ArrayList<HoatDong>();
                if (myShared.getCbCaNhan() && myShared.getCbTKB()) {
                    listHDDuocChon = xulyHD.laydstheongay(KEY_NGAY_DUOC_CHON);
                    SapXepMang(listHDDuocChon);
                }
                else{
                    if (myShared.getCbCaNhan())
                        listHDDuocChon=listHoatDong;
                    if (myShared.getCbTKB())
                        listHDDuocChon=listHoatDong;
                }
                String strTenHoatDong = listHDDuocChon.get(position).getTenhd();
                String strNoiDungGhiChu = listHDDuocChon.get(position).getGhichu();

                //Tạo dialog hiển thị ghi chú
                FragmentManager fm = getActivity().getSupportFragmentManager();
                GhiChuDialogFragment dialog = GhiChuDialogFragment.newInstance("Ghi chú:",strNoiDungGhiChu, getActivity());
                dialog.show(fm, "frag_ghi_chu");
            }
        });

        //ibThem.setOnClickListener(this);
        ibHomNay.setOnClickListener(this);
        ibTuanSau.setOnClickListener(this);
        ibTuanTruoc.setOnClickListener(this);
        cotThu2.setOnClickListener(this);
        cotThu3.setOnClickListener(this);
        cotThu4.setOnClickListener(this);
        cotThu5.setOnClickListener(this);
        cotThu6.setOnClickListener(this);
        cotThu7.setOnClickListener(this);
        cotChuNhat.setOnClickListener(this);
        cotThu2Top.setOnClickListener(this);
        cotThu3Top.setOnClickListener(this);
        cotThu4Top.setOnClickListener(this);
        cotThu5Top.setOnClickListener(this);
        cotThu6Top.setOnClickListener(this);
        cotThu7Top.setOnClickListener(this);
        cotChuNhatTop.setOnClickListener(this);

        //4. Load dữ liệu khi chạy chương trình
        KEY_NGAY_DUOC_CHON = displayNgayHienTai(lich.getTime());
        //tvNgayHienTai.setText(displayNgayHienTai(lich.getTime()));
        dateHienTai = lich.getTime();
        tvNgayDuocChon.setText("Today");
        loadNgayCuaThu(0);
        loadDuLieu();
        //Load các hoat động của ngày hôm nay lên lv khi bật ứng dụng
        //loadDulieuListView(tvNgayHienTai.getText().toString());
        return root;
    }

    @Override
    public void onClick(View view) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        switch (view.getId()){
            case R.id.ibThem_Main:
                baseFragment.ChuyenFragment(new ThemFragment(), SupportList.TAG_THEM_SU_KIEN_FRAGMENT,true);
            //Khi ấn chuyển tuần sẽ load lại các ngày của tuần, xóa hoạt động cũ và cập nhật các hoạt động trong tuần mới
            case R.id.ibHomNay_Main:
                loadNgayCuaThu(0);
                xoaHoatDong();
                loadHoatDong();
                KEY_NGAY_DUOC_CHON = formatter.format(layNgayDuocChon(0));
                tvNgayDuocChon.setText("Today");
                loadDulieuListView(tvNgayHienTai.getText().toString());
                break;
            case R.id.ibTuanTruoc_Main:
                loadNgayCuaThu(-1);
                xoaHoatDong();
                loadHoatDong();
                break;
            case R.id.ibTuanSau_Main:
                loadNgayCuaThu(1);
                xoaHoatDong();
                loadHoatDong();
                break;
            //Event trong list hoat động
            case R.id.ivEdit_lvitem:
//                Intent intent = new Intent(getActivity(), CapNhatActivity.class);
//                intent.putExtra(SupportList.KEY_HOATDONG, hoatDongDuocChon);
//                intent.putExtra(SupportList.KEY_POSITION, posHoatDongDuocChon);
//                getActivity().startActivity(intent);
                break;
            case R.id.ivDelete_lvitem:
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Bạn có chắc muốn xóa?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper db = new DBHelper(getActivity());
                        XulyHoatdong xulyhd = new XulyHoatdong(db);
                        xulyhd.XoaHd(hoatDongDuocChon.getTenhd(),hoatDongDuocChon.getTgbd());

                        loadDuLieu();
                        xoaHoatDong();
                        loadHoatDong();
                        loadDulieuListView(KEY_NGAY_DUOC_CHON);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                AlertDialog thongbao = builder.create();
                thongbao.show();
                break;
            //3.2 Xử lý khi ấn vào cột ngày trên lich để xem các hoạt động có trong ngày đó
            case R.id.event_column_thu2:
                KEY_NGAY_DUOC_CHON = formatter.format(layNgayDuocChon(0));
                if(KEY_NGAY_DUOC_CHON.equals(tvNgayHienTai.getText().toString()))
                    tvNgayDuocChon.setText("Today");
                else
                    tvNgayDuocChon.setText(KEY_NGAY_DUOC_CHON);
                loadDulieuListView(KEY_NGAY_DUOC_CHON);
                break;
            case R.id.event_column_thu3:
                KEY_NGAY_DUOC_CHON = formatter.format(layNgayDuocChon(1));
                if(KEY_NGAY_DUOC_CHON.equals(tvNgayHienTai.getText().toString()))
                    tvNgayDuocChon.setText("Today");
                else
                    tvNgayDuocChon.setText(KEY_NGAY_DUOC_CHON);
                loadDulieuListView(KEY_NGAY_DUOC_CHON);
                break;
            case R.id.event_column_thu4:
                KEY_NGAY_DUOC_CHON = formatter.format(layNgayDuocChon(2));
                if(KEY_NGAY_DUOC_CHON.equals(tvNgayHienTai.getText().toString()))
                    tvNgayDuocChon.setText("Today");
                else
                    tvNgayDuocChon.setText(KEY_NGAY_DUOC_CHON);
                loadDulieuListView(KEY_NGAY_DUOC_CHON);
                break;
            case R.id.event_column_thu5:
                KEY_NGAY_DUOC_CHON = formatter.format(layNgayDuocChon(3));
                if(KEY_NGAY_DUOC_CHON.equals(tvNgayHienTai.getText().toString()))
                    tvNgayDuocChon.setText("Today");
                else
                    tvNgayDuocChon.setText(KEY_NGAY_DUOC_CHON);
                loadDulieuListView(KEY_NGAY_DUOC_CHON);
                break;
            case R.id.event_column_thu6:
                KEY_NGAY_DUOC_CHON = formatter.format(layNgayDuocChon(4));
                if(KEY_NGAY_DUOC_CHON.equals(tvNgayHienTai.getText().toString()))
                    tvNgayDuocChon.setText("Today");
                else
                    tvNgayDuocChon.setText(KEY_NGAY_DUOC_CHON);
                loadDulieuListView(KEY_NGAY_DUOC_CHON);
                break;
            case R.id.event_column_thu7:
                KEY_NGAY_DUOC_CHON = formatter.format(layNgayDuocChon(5));
                if(KEY_NGAY_DUOC_CHON.equals(tvNgayHienTai.getText().toString()))
                    tvNgayDuocChon.setText("Today");
                else
                    tvNgayDuocChon.setText(KEY_NGAY_DUOC_CHON);
                loadDulieuListView(KEY_NGAY_DUOC_CHON);
                break;
            case R.id.event_column_cn:
                KEY_NGAY_DUOC_CHON = formatter.format(layNgayDuocChon(6));
                if(KEY_NGAY_DUOC_CHON.equals(tvNgayHienTai.getText().toString()))
                    tvNgayDuocChon.setText("Today");
                else
                    tvNgayDuocChon.setText(KEY_NGAY_DUOC_CHON);
                loadDulieuListView(KEY_NGAY_DUOC_CHON);
                break;

            //2.2 Xử lý cột hiển thị ngày trên lịch
            case R.id.event_column_thu2_top:
                KEY_NGAY_DUOC_CHON = formatter.format(layNgayDuocChon(0));
                if(KEY_NGAY_DUOC_CHON.equals(tvNgayHienTai.getText().toString()))
                    tvNgayDuocChon.setText("Today");
                else
                    tvNgayDuocChon.setText(KEY_NGAY_DUOC_CHON);
                break;
            case R.id.event_column_thu3_top:
                KEY_NGAY_DUOC_CHON = formatter.format(layNgayDuocChon(1));
                if(KEY_NGAY_DUOC_CHON.equals(tvNgayHienTai.getText().toString()))
                    tvNgayDuocChon.setText("Today");
                else
                    tvNgayDuocChon.setText(KEY_NGAY_DUOC_CHON);
                loadDulieuListView(KEY_NGAY_DUOC_CHON);
                break;
            case R.id.event_column_thu4_top:
                KEY_NGAY_DUOC_CHON = formatter.format(layNgayDuocChon(2));
                if(KEY_NGAY_DUOC_CHON.equals(tvNgayHienTai.getText().toString()))
                    tvNgayDuocChon.setText("Today");
                else
                    tvNgayDuocChon.setText(KEY_NGAY_DUOC_CHON);
                loadDulieuListView(KEY_NGAY_DUOC_CHON);
                break;
            case R.id.event_column_thu5_top:
                KEY_NGAY_DUOC_CHON = formatter.format(layNgayDuocChon(3));
                if(KEY_NGAY_DUOC_CHON.equals(tvNgayHienTai.getText().toString()))
                    tvNgayDuocChon.setText("Today");
                else
                    tvNgayDuocChon.setText(KEY_NGAY_DUOC_CHON);
                loadDulieuListView(KEY_NGAY_DUOC_CHON);
                break;
            case R.id.event_column_thu6_top:
                KEY_NGAY_DUOC_CHON = formatter.format(layNgayDuocChon(4));
                if(KEY_NGAY_DUOC_CHON.equals(tvNgayHienTai.getText().toString()))
                    tvNgayDuocChon.setText("Today");
                else
                    tvNgayDuocChon.setText(KEY_NGAY_DUOC_CHON);
                loadDulieuListView(KEY_NGAY_DUOC_CHON);
                break;
            case R.id.event_column_thu7_top:
                KEY_NGAY_DUOC_CHON = formatter.format(layNgayDuocChon(5));
                if(KEY_NGAY_DUOC_CHON.equals(tvNgayHienTai.getText().toString()))
                    tvNgayDuocChon.setText("Today");
                else
                    tvNgayDuocChon.setText(KEY_NGAY_DUOC_CHON);
                loadDulieuListView(KEY_NGAY_DUOC_CHON);
                break;
            case R.id.event_column_cn_top:
                KEY_NGAY_DUOC_CHON = formatter.format(layNgayDuocChon(6));
                if(KEY_NGAY_DUOC_CHON.equals(tvNgayHienTai.getText().toString()))
                    tvNgayDuocChon.setText("Today");
                else
                    tvNgayDuocChon.setText(KEY_NGAY_DUOC_CHON);
                loadDulieuListView(KEY_NGAY_DUOC_CHON);
                break;
        }
    }

    //Hiển thị ngày hiện tại
    private String displayNgayHienTai(Date mDate){
        //Tạo định dạng hiển thị
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        return formatter.format(mDate);
    }

    //Hiển thị các ngày của tuần người dùng đang xem
    private void loadNgayCuaThu(int now) {
        Calendar tempCal = lich;
        //Tạo định dạng ngày
        DateFormat df = new SimpleDateFormat("d MMM");
        //Tạo chuỗi lưu ngày và tháng để gắn cho textview hiển thị
        String[] days = new String[14];
        //Trả về thú 2 để khi lưu vào chuỗi sẽ bắt đầu lưu từ thứ 2 cho tới chủ nhật
        lich.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        //Nếu now = 0 trả về ngày hiện tại, now = 1 trả về tuần sau, now = -1 trả về tuần trước
        if(now == 0){
            lich = lich.getInstance();
            lich.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            //tempCal = lich;
            for (int i = 0; i < 14; i++) {
                //Tách chuỗi để lưu ngày và tháng
                String[] tachNgayThang = df.format(lich.getTime()).split(" ");
                days[i] = tachNgayThang[0];
                i++;
                days[i] = tachNgayThang[1];
                //Tăng ngày lên 1
                lich.add(Calendar.DATE, 1);
            }
            count = 0;
        }
        else{
            if(now == -1) {
                //Lùi tuần cho lịch
                lich.add(Calendar.WEEK_OF_MONTH, -2);
                //tempCal = lich;
                for (int i = 0; i < 14; i++) {
                    String[] tachNgayThang = df.format(lich.getTime()).split(" ");
                    days[i] = tachNgayThang[0];
                    i++;
                    days[i] = tachNgayThang[1];
                    lich.add(Calendar.DATE, 1);
                }
                count -= 1;
            }
            else {
                //tempCal = lich;
                for (int i = 0; i < 14; i++) {
                    String[] tachNgayThang = df.format(lich.getTime()).split(" ");
                    days[i] = tachNgayThang[0];
                    i++;
                    days[i] = tachNgayThang[1];
                    lich.add(Calendar.DATE, 1);
                }
                count += 1;
            }
        }

        //Set màu cho cột thứ đã qua
        if(count == 0){
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Calendar tempCalHienTai = Calendar.getInstance();
            String[] tachNgayThangHienTai = dateFormat.format(tempCalHienTai.getTime()).split("/");
            int ngayHienTai = Integer.parseInt(tachNgayThangHienTai[0]);
            int thangHienTai = Integer.parseInt(tachNgayThangHienTai[1]);
            int namHienTai = Integer.parseInt(tachNgayThangHienTai[2]);


            //tempCal.add(Calendar.WEEK_OF_MONTH, -1);
            tempCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            for (int i = 0; i < 7; i++) {
                //Set màu cho cột ngày đã qua
                String[] tachNgayThang = dateFormat.format(layNgayDuocChon(i)).split("/");
                int tempNam = Integer.parseInt(tachNgayThang[2]);
                int tempThang = Integer.parseInt(tachNgayThang[1]);
                int tempNgay = Integer.parseInt(tachNgayThang[0]);

                if(tempNam <= namHienTai){
                    if(tempNam == namHienTai) {
                        if (tempThang <= thangHienTai) {
                            if (tempThang == thangHienTai) {
                                if (tempNgay < ngayHienTai) {
                                    switch (i) {
                                        case 0:
                                            cotThu2.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                                            break;
                                        case 1:
                                            cotThu3.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                                            break;
                                        case 2:
                                            cotThu4.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                                            break;
                                        case 3:
                                            cotThu5.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                                            break;
                                        case 4:
                                            cotThu6.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                                            break;
                                        case 5:
                                            cotThu7.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                                            break;
                                        case 6:
                                            cotChuNhat.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                                            break;
                                    }
                                }
                                else{
                                    switch (i){
                                        case 0:
                                            cotThu2.setBackgroundColor(Color.WHITE);
                                            break;
                                        case 1:
                                            cotThu3.setBackgroundColor(Color.WHITE);
                                            break;
                                        case 2:
                                            cotThu4.setBackgroundColor(Color.WHITE);
                                            break;
                                        case 3:
                                            cotThu5.setBackgroundColor(Color.WHITE);
                                            break;
                                        case 4:
                                            cotThu6.setBackgroundColor(Color.WHITE);
                                            break;
                                        case 5:
                                            cotThu7.setBackgroundColor(Color.WHITE);
                                            break;
                                        case 6:
                                            cotChuNhat.setBackgroundColor(Color.WHITE);
                                            break;
                                    }
                                }
                            }
                            else{
                                switch (i){
                                    case 0:
                                        cotThu2.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                                        break;
                                    case 1:
                                        cotThu3.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                                        break;
                                    case 2:
                                        cotThu4.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                                        break;
                                    case 3:
                                        cotThu5.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                                        break;
                                    case 4:
                                        cotThu6.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                                        break;
                                    case 5:
                                        cotThu7.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                                        break;
                                    case 6:
                                        cotChuNhat.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                                        break;
                                }
                            }
                        }
                    }
                    else{
                        switch (i){
                            case 0:
                                cotThu2.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                                break;
                            case 1:
                                cotThu3.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                                break;
                            case 2:
                                cotThu4.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                                break;
                            case 3:
                                cotThu5.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                                break;
                            case 4:
                                cotThu6.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                                break;
                            case 5:
                                cotThu7.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                                break;
                            case 6:
                                cotChuNhat.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                                break;
                        }
                    }
                }
                //Tăng ngày lên 1
                //tempCal.add(Calendar.DATE, 1);
            }

            //tempCal.add(Calendar.WEEK_OF_MONTH, 1);
        }
        else{
            if(count == -1){
                //Set màu cho cột ngày đã qua
                cotThu2.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                cotThu3.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                cotThu4.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                cotThu5.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                cotThu6.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                cotThu7.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
                cotChuNhat.setBackgroundColor(Color.parseColor("#0ab1d8e5"));
            }
            if(count == 1){
                //Set màu cho cột ngày đã qua
                cotThu2.setBackgroundColor(Color.WHITE);
                cotThu3.setBackgroundColor(Color.WHITE);
                cotThu4.setBackgroundColor(Color.WHITE);
                cotThu5.setBackgroundColor(Color.WHITE);
                cotThu6.setBackgroundColor(Color.WHITE);
                cotThu7.setBackgroundColor(Color.WHITE);
                cotChuNhat.setBackgroundColor(Color.WHITE);
            }
        }

        //Hiện ngày lên lịch
        tvNgayThu2.setText(days[0] + "\n" + days[1]);
        tvNgayThu3.setText(days[2] + "\n" + days[3]);
        tvNgayThu4.setText(days[4] + "\n" + days[5]);
        tvNgayThu5.setText(days[6] + "\n" + days[7]);
        tvNgayThu6.setText(days[8] + "\n" + days[9]);
        tvNgayThu7.setText(days[10] + "\n" + days[11]);
        tvNgayChuNhat.setText(days[12] + "\n" + days[13]);
    }

    private void initialData(String nhom) {
        XulyHoatdong xulyhd=new XulyHoatdong(db);
        if(nhom.equals("all")){
            listHoatDong = new ArrayList<HoatDong>();
            listHoatDong = xulyhd.laytatcahd();
            SapXepMang(listHoatDong);

        }
        else {
            listHoatDong = new ArrayList<HoatDong>();
            listHoatDong = xulyhd.LayDStheonhom(nhom);
            SapXepMang(listHoatDong);
        }
    }

    //load dữ liệu
    public void loadDuLieu() {
        myShared = new MyShared(MenuActivity.KEY_TEN_FILE,getActivity());
//        if(myShared.getCbTKB() && myShared.getCbCaNhan()){
//            initialData("all");
//        }
//        else {
//            if (myShared.getCbTKB()) {
//                initialData("TKB");
//            }
//            if (myShared.getCbCaNhan()) {
//                initialData("Ca nhan");
//            }
//        }
//        loadHoatDong();
    }

    // Truyền dữ liệu các hoạt động vào listview
    public void loadDulieuListView(String ngay)
    {
        ArrayList<HoatDong> tempList = new ArrayList<>();
        XulyHoatdong x = new XulyHoatdong(db);
        if(myShared.getCbTKB() && myShared.getCbCaNhan()){
            tempList = x.laydstheongay(ngay);
        } else {
            if (myShared.getCbTKB()) {
                tempList = x.laydstheongaynhom(ngay,"TKB");
            }
            if (myShared.getCbCaNhan()) {
                tempList = x.laydstheongaynhom(ngay,"Ca nhan");
            }
        }
        //Sắp xếp
        SapXepMang(tempList);

        //Hiển thị dữ liệu đươc truyền vào ra màn hình
        HoatdongAdapter adapter=new HoatdongAdapter(getActivity(), R.layout.item_hoat_dong, tempList, MainFragment.this, MainFragment.this);
        lvHoatDong.setAdapter(adapter);
    }

    //Lấy ngày người dùng chọn để xem hoạt động
    public Date layNgayDuocChon(int thuDuocChon) {
        Date tempDate;
        lich.add(Calendar.WEEK_OF_MONTH, -1);
        if(thuDuocChon != 0){
            lich.add(Calendar.DATE, thuDuocChon);
            tempDate = lich.getTime();
        }
        else {
            tempDate = lich.getTime();
        }

        lich.add(Calendar.WEEK_OF_MONTH, 1);
        if (thuDuocChon != 0){
            lich.add(Calendar.DATE, -thuDuocChon);
        }
        return tempDate;
    }

    public void xoaHoatDong() {
        //Đếm số view của cột thứ nếu view nhiều hơn số view mặc định của view cha chứa nó sẽ tiến hành xóa view đồng nghĩa với việc xóa hoạt động
        while ((countIndex = cotThu2.getChildCount()) > 23)
            cotThu2.removeViewAt(countIndex - 2);
        tvNgayThu2.setTextColor(Color.parseColor("#FF727272"));

        while ((countIndex = cotThu3.getChildCount()) > 23)
            cotThu3.removeViewAt(countIndex - 2);
        tvNgayThu3.setTextColor(Color.parseColor("#FF727272"));

        while ((countIndex = cotThu4.getChildCount()) > 23)
            cotThu4.removeViewAt(countIndex - 2);
        tvNgayThu4.setTextColor(Color.parseColor("#FF727272"));

        while ((countIndex = cotThu5.getChildCount()) > 23)
            cotThu5.removeViewAt(countIndex - 2);
        tvNgayThu5.setTextColor(Color.parseColor("#FF727272"));

        while ((countIndex = cotThu6.getChildCount()) > 23)
            cotThu6.removeViewAt(countIndex - 2);
        tvNgayThu6.setTextColor(Color.parseColor("#FF727272"));

        while ((countIndex = cotThu7.getChildCount()) > 23)
            cotThu7.removeViewAt(countIndex - 2);
        tvNgayThu7.setTextColor(Color.parseColor("#FF727272"));

        while ((countIndex = cotChuNhat.getChildCount()) > 23)
            cotChuNhat.removeViewAt(countIndex - 2);
        tvNgayChuNhat.setTextColor(Color.parseColor("#FF727272"));
    }

    /*Xử lý hoạt động:
    * 1. Lấy dữ liệu của hoạt động
    * 2. Tạo view để hiện thị hoạt động đó lên lịch
    * 3. Tính toán kích thước view để hiển thị đúng khung giờ hoạt động cần dựa trên thời gian bắt đầu và thời gian kết thúc
    * */
    //Lấy dữ liệu từ csdl
    public void loadHoatDong() {
        //Khai báo dữ liệu
        for (int i = 0; i < listHoatDong.size(); i++) {
            int ChieuCaoKhungHoatDong = layKhungThoiGian(listHoatDong.get(i).getTgbd(), listHoatDong.get(i).getTgkt());
            displayHoatDong(listHoatDong.get(i).getTgbd(), ChieuCaoKhungHoatDong, listHoatDong.get(i).getTenhd(), listHoatDong.get(i).getNhom());
        }
    }

    private int layKhungThoiGian(String ngayBatDau, String ngayKetThuc) {
        //Xác định vị trí view dựa trên giờ bắt đầu và kết thúc

        //Xử lý giờ bắt đầu
        String[] tachNgayGioBatDau = ngayBatDau.split(" ");
        String giobd = tachNgayGioBatDau[1];
        String[] luuGioBatDau = giobd.split(":");    // Tạo chuỗi để lưu giờ và phút
        int gioBatDau = Integer.parseInt(luuGioBatDau[0]);
        int phutBatDau = Integer.parseInt(luuGioBatDau[1]);

        //Xử lý giờ kết thúc
        String[] tachNgayGioKetThuc = ngayKetThuc.split(" ");
        String giokt = tachNgayGioKetThuc[1];

        String[] luuGioKetThuc = giokt.split(":");
        int gioKetThuc = Integer.parseInt(luuGioKetThuc[0]);
        int phutKetThuc = Integer.parseInt(luuGioKetThuc[1]);

        //Trả về giá trị chiều cao của view hoạt động
        return ((gioKetThuc * 60) + phutKetThuc) - ((gioBatDau * 60) + phutBatDau);
    }
    //Hiển thị hoạt động lên lịch

    private void displayHoatDong(String ngayBatDau, int chieuCaoKhungHoatDong, String tenHoatDong, String nhom) {
        String[] tachNgayGioBatDau = ngayBatDau.split(" ");
        //String ngaybd = tachNgayGioBatDau[0];
        String giobd = tachNgayGioBatDau[1];
        String[] hourMinutes = giobd.split(":");
        int hours = Integer.parseInt(hourMinutes[0]);
        int minutes = Integer.parseInt(hourMinutes[1]);

        int topViewMargin = (hours * 60) + minutes;

        taoViewHoatDong(topViewMargin, chieuCaoKhungHoatDong, tenHoatDong, ngayBatDau, nhom);
    }

    //Tạo view cho hoạt động
    private void taoViewHoatDong(int topViewMargin, int chieuCaoKhungHoatDong, String tenHoatDong, String ngayBatDau, String nhom) {
        //Lấy giá trị kich thước màn hình
        float level = getContext().getResources().getDisplayMetrics().density;
        //Button btnViewHoatDong = new Button(MainActivity.this);
        TextView btnViewHoatDong;
        //LinearLayout lLayout = new LinearLayout(MainActivity.this);
        RelativeLayout.LayoutParams lParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

//        LinearLayout.LayoutParams lParambtn = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
//        lLayout.setLayoutParams(lParam);
//        lLayout.setOrientation(LinearLayout.HORIZONTAL);
        lParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        btnViewHoatDong = new TextView(getActivity());
        if(level == 2.0) {
            lParam.topMargin = topViewMargin * 2;
            btnViewHoatDong.setHeight(chieuCaoKhungHoatDong * 2);
        }
        if(level == 3.0) {
            lParam.topMargin = topViewMargin * 3;
            btnViewHoatDong.setHeight(chieuCaoKhungHoatDong * 3);
        }

        //btnViewHoatDong.setLayoutParams(lParam);
        //mEventView.setPadding(24, 0, 24, 0);
        btnViewHoatDong.setLayoutParams(lParam);
        btnViewHoatDong.setGravity(0x11);
        btnViewHoatDong.setTextColor(Color.parseColor("#ffffff"));
        btnViewHoatDong.setText(tenHoatDong);
        if(nhom.compareTo("TKB") == 0){
            btnViewHoatDong.setBackgroundColor(Color.parseColor("#ff33b5e5"));
        }
        else
            btnViewHoatDong.setBackgroundColor(Color.parseColor("#FF4081"));

        //lLayout.addView(btnViewHoatDong, i);
        //Xác định thứ để hiển thị view lên lịch
        int thu = layCotThuCuaHoatDong(ngayBatDau);
        switch (thu){
            case 0:
                eventIndex = cotThu2.getChildCount();
                cotThu2.addView(btnViewHoatDong, eventIndex - 1);
                tvNgayThu2.setTextColor(Color.parseColor("#B1D8E5"));
                break;
            case 1:
                eventIndex = cotThu3.getChildCount();
                cotThu3.addView(btnViewHoatDong, eventIndex - 1);
                tvNgayThu3.setTextColor(Color.parseColor("#B1D8E5"));
                break;
            case 2:
                eventIndex = cotThu4.getChildCount();
                cotThu4.addView(btnViewHoatDong, eventIndex - 1);
                tvNgayThu4.setTextColor(Color.parseColor("#B1D8E5"));
                break;
            case 3:
                eventIndex = cotThu5.getChildCount();
                cotThu5.addView(btnViewHoatDong, eventIndex - 1);
                tvNgayThu5.setTextColor(Color.parseColor("#B1D8E5"));
                break;
            case 4:
                eventIndex = cotThu6.getChildCount();
                cotThu6.addView(btnViewHoatDong, eventIndex - 1);
                tvNgayThu6.setTextColor(Color.parseColor("#B1D8E5"));
                break;
            case 5:
                eventIndex = cotThu7.getChildCount();
                cotThu7.addView(btnViewHoatDong, eventIndex - 1);
                tvNgayThu7.setTextColor(Color.parseColor("#B1D8E5"));
                break;
            case 6:
                eventIndex = cotChuNhat.getChildCount();
                cotChuNhat.addView(btnViewHoatDong, eventIndex - 1);
                tvNgayChuNhat.setTextColor(Color.parseColor("#B1D8E5"));
                break;
            default:
                break;
        }
    }

    //Xác định cột thứ để hiển thị lên lịch
    public int layCotThuCuaHoatDong(String ngayBatDau){
        /*Tạo biến lưu ngày và tháng của ngày bắt đầu hoạt động và ngày hiện tại để so sánh với nhau
        * nếu giống thì trả về ngày đó để hiện thị view hoạt động lên*/

        SimpleDateFormat dinhdangNgayThang = new SimpleDateFormat("d/MM", Locale.ENGLISH);
        int ngay1, thang1;
        int ngay2, thang2;
        //String displayNgayThang = dinhdangNgayThang.format(ngayBatDau);

        String[] tachNgayThang = ngayBatDau.split("/");
        ngay2 = Integer.parseInt(tachNgayThang[0]);
        thang2 = Integer.parseInt(tachNgayThang[1]);

        for (int i = 0; i < 7; i++) {
            //So sánh ngày tháng bắt đầu với ngày thàng từ t2 -> cn
            String displayNgayThang = dinhdangNgayThang.format(layNgayDuocChon(i));
            tachNgayThang = displayNgayThang.split("/");
            ngay1 = Integer.parseInt(tachNgayThang[0]);
            thang1 = Integer.parseInt(tachNgayThang[1]);
            if(ngay1 == ngay2 && thang1 == thang2){
                return i;
            }
        }
        return -1;
    }

    //Xóa các hoạt động đã qua
    public void XoaHDDQ() {
        //Lấy tất cả hd từ database
        XulyHoatdong xulyhd = new XulyHoatdong(db);
        ArrayList<HoatDong> listhd = new ArrayList<HoatDong>();
        listhd = xulyhd.laytatcahd();

        //Lấy ngày hiện tại để so sánh
        String ngaythanght = tvNgayHienTai.getText().toString();
        //Tách ngày tháng năm hiện tại để so sánh
        String[] tachngaythanght = ngaythanght.split("/");
        int ngayht = Integer.parseInt(tachngaythanght[0]);
        int thanght = Integer.parseInt(tachngaythanght[1]);
        int namht = Integer.parseInt(tachngaythanght[2]);

        //Duyệt từng phần tử trong Database kiểm tra hoạt đọng nào đã qua thì xóa
        for (int i = 0; i < listhd.size(); i++) {

            //Tách ngày với giờ của dữ liệu lấy từ Database
            String[] tachTGBDDB = listhd.get(i).getTgbd().split(" ");
            //Tách giờ với phút sau từ dữ liệu của Database
            String[] tachngaydb = tachTGBDDB[0].split("/");

            int ngaydb = Integer.parseInt(tachngaydb[0]);
            int thangdb = Integer.parseInt(tachngaydb[1]);
            int namdb = Integer.parseInt(tachngaydb[2]);

            //So sánh với ngày hiện tại để xóa các ngày đã qua
            if (namdb==namht){
                if (thangdb==thanght){
                    if (ngaydb<ngayht){
                        xulyhd.XoaHd(listhd.get(i).getTenhd().toString(), listhd.get(i).getTgbd().toString());
                    }
                }
                else {
                    if (thangdb<thanght)
                        xulyhd.XoaHd(listhd.get(i).getTenhd().toString(), listhd.get(i).getTgbd().toString());
                }
            }
            else {
                if (namdb<namht)
                    xulyhd.XoaHd(listhd.get(i).getTenhd().toString(), listhd.get(i).getTgbd().toString());
            }
        }
    }

    //Sắp sếp các hoat động trong listview theo giờ
    public void SapXepMang(ArrayList<HoatDong> manghd ) {
        int i;
        int j;

        String[] tachTgbd;
        String[] tachThoigianbd;
        int giobd;
        int phutbd;

        String[] tachTgbd1;
        String[] tachThoigianbd1;
        int giobd1;
        int phutbd1;
        for (i=0;i<manghd.size()-1;i++) {
            tachTgbd = manghd.get(i).getTgbd().split(" ");
            tachThoigianbd = tachTgbd[1].split(":");
            giobd = Integer.parseInt(tachThoigianbd[0]);
            phutbd = Integer.parseInt(tachThoigianbd[1]);

            for (j = i + 1; j < manghd.size(); j++)
            {
                tachTgbd1 = manghd.get(j).getTgbd().split(" ");
                tachThoigianbd1 = tachTgbd1[1].split(":");
                giobd1 = Integer.parseInt(tachThoigianbd1[0]);
                phutbd1 = Integer.parseInt(tachThoigianbd1[1]);

                //So sánh giờ để sắp xếp
                if (giobd * 60 + phutbd > giobd1 * 60 + phutbd1) {
                    HoatDong temphd = new HoatDong();

                    temphd = manghd.get(i);
                    manghd.set(i,manghd.get(j));
                    manghd.set(j,temphd);
                }
            }
        }
    }

    @Override
    public void ChuyenHoatDong(HoatDong hoatDong, int position, String key) {
        switch (key){
            case "dataHoatDongAdapter":
                hoatDongDuocChon = hoatDong;
                posHoatDongDuocChon = position;
                break;
        }
    }
}
