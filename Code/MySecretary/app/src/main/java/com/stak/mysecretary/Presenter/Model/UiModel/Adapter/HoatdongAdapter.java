package com.stak.mysecretary.Presenter.Model.UiModel.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stak.mysecretary.R;
import com.stak.mysecretary.Presenter.Model.DataModel.HoatDong.DataCallBack;
import com.stak.mysecretary.Model.HoatDong;

import java.util.ArrayList;

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
    private ArrayList<HoatDong> listHoatDong;
    private DataCallBack dataCallBack;

    public HoatdongAdapter(Activity context, int resource, ArrayList<HoatDong> objects, View.OnClickListener onClickListener, DataCallBack dataCallBack) {
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
        final HoatDong hoatDong = listHoatDong.get(position);

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
