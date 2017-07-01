package com.stak.mysecretary.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v4.app.FragmentManager;
import android.support.v7.util.AsyncListUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stak.mysecretary.CapNhatActivity;
import com.stak.mysecretary.MainActivity;
import com.stak.mysecretary.R;
import com.stak.mysecretary.ThemHoatDongActivity;
import com.stak.mysecretary.database.DBHelper;
import com.stak.mysecretary.database.XulyHoatdong;
import com.stak.mysecretary.interfaces.DataCallBack;
import com.stak.mysecretary.model.Hoatdong;
import com.stak.mysecretary.util.SupportList;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ADMIN on 3/29/2017.
 */

public class HoatdongAdapter extends ArrayAdapter{
    Activity con;

    TextView tvTenHoatDong;
    TextView tvDiaDiem;
    TextView tvNgay;
    TextView tvGio;
    ImageView ivEdit;
    ImageView ivXoa;
    View.OnClickListener onClickListener;

    private int layoutItem;
    private ArrayList<Hoatdong> listHoatDong;
    private DataCallBack dataCallBack;

    public HoatdongAdapter(Activity context, int resource, ArrayList<Hoatdong> objects, View.OnClickListener onClickListener, DataCallBack dataCallBack) {
        super(context, resource, objects);

        con = context;
        layoutItem = resource;
        listHoatDong = objects;
        this.onClickListener = onClickListener;
        this.dataCallBack = dataCallBack;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = con.getLayoutInflater();
        convertView = inflater.inflate(layoutItem, null); //Ánh xạ
        final Hoatdong hoatDong = listHoatDong.get(position);

        tvTenHoatDong = (TextView) convertView.findViewById(R.id.tvTenHD_lvitem);
        tvDiaDiem = (TextView) convertView.findViewById(R.id.tvDiaDiem_lvitem);
        tvGio=(TextView) convertView.findViewById(R.id.tvGio_lvitem);
        ivEdit = (ImageView) convertView.findViewById(R.id.ivEdit_lvitem);
        ivXoa = (ImageView) convertView.findViewById(R.id.ivDelete_lvitem);

        tvTenHoatDong.setText(hoatDong.getTenhd());
        tvDiaDiem.setText(hoatDong.getDiadiem());

        //Chỉ lấy thời gian đưa lên lsitview
        String[] tachtgbd=hoatDong.getTgbd().split(" ");
        String[] tachtgkt=hoatDong.getTgkt().split(" ");
        String giophutbd=tachtgbd[1];
        String giophutkt=tachtgkt[1];
        tvGio.setText(giophutbd + " - " + giophutkt);

        ivEdit.setOnClickListener(onClickListener);
        ivXoa.setOnClickListener(onClickListener);

        //Chuyền hoạt động được chọn sang main fragment
        dataCallBack.ChuyenHoatDong(hoatDong, position, "dataHoatDongAdapter");

        return convertView;
    }
}
