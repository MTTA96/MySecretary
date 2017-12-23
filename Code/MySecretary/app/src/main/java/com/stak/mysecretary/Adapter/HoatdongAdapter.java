package com.stak.mysecretary.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stak.mysecretary.View.Activity.CapNhatActivity;
import com.stak.mysecretary.View.Activity.MainActivity;
import com.stak.mysecretary.R;
import com.stak.mysecretary.database.DBHelper;
import com.stak.mysecretary.database.XulyHoatdong;
import com.stak.mysecretary.model.HoatDong;
import com.stak.mysecretary.util.SupportList;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ADMIN on 3/29/2017.
 */

public class HoatdongAdapter extends ArrayAdapter{
    MainActivity con;
    TextView tvTenHoatDong;
    TextView tvDiaDiem;
    TextView tvNgay;
    TextView tvGio;
    ImageView ivEdit;
    ImageView ivXoa;

    private int layoutItem;
    private ArrayList<HoatDong> listHoatDong;
    private XulyHoatdong xulyHoatdong;

    public HoatdongAdapter(MainActivity context, int resource, ArrayList<HoatDong> objects) {
        super(context, resource, objects);

        con = context;
        layoutItem = resource;
        listHoatDong = objects;
        xulyHoatdong = new XulyHoatdong(con);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = con.getLayoutInflater();
        convertView = inflater.inflate(layoutItem, null); //Ánh xạ
        final HoatDong hoatDong = listHoatDong.get(position);

        tvTenHoatDong = (TextView) convertView.findViewById(R.id.tvTenHD_lvitem);
        tvDiaDiem = (TextView) convertView.findViewById(R.id.tvDiaDiem_lvitem);
        tvGio=(TextView) convertView.findViewById(R.id.tvGio_lvitem);
        ivEdit = (ImageView) convertView.findViewById(R.id.ivEdit_lvitem);
        ivXoa = (ImageView) convertView.findViewById(R.id.ivDelete_lvitem);

        tvTenHoatDong.setText(hoatDong.getTenhd());
        tvDiaDiem.setText(hoatDong.getDiadiem());

        //Chỉ lấy thời gian đưa lên lsitview
        String[] tachtgbd= hoatDong.getTgbd().split(" ");
        String[] tachtgkt= hoatDong.getTgkt().split(" ");
        //String ngay=tachtgbd[0];
        String giophutbd=tachtgbd[1];
        String giophutkt=tachtgkt[1];
        //tvNgay.setText(ngay);
        tvGio.setText(giophutbd + " - " + giophutkt);

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(con, CapNhatActivity.class);
                intent.putExtra(SupportList.KEY_HOATDONG, hoatDong.getId());
                intent.putExtra(SupportList.KEY_POSITION, position);
                con.startActivity(intent);
            }
        });

        ivXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(con);
                builder.setTitle("Bạn có chắc muốn xóa?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        DBHelper db = new DBHelper(con);
//                        XulyHoatdong xulyhd = new XulyHoatdong(db);
//                        xulyhd.XoaHd(hoatDong.getTenhd(), hoatDong.getTgbd());
                        xulyHoatdong.XoaHd(hoatDong.getId());
                        con.loadDuLieu();
                        con.xoaHoatDong();
                        con.loadHoatDong();
                        con.loadDulieuListView(con.KEY_NGAY_DUOC_CHON);
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
        }
        });
        return convertView;
    }
}
