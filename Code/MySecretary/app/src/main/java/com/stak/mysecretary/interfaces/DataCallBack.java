package com.stak.mysecretary.Interfaces;

import com.stak.mysecretary.Model.Data.HoatDong;

/**
 * Created by Quang Trí on 6/30/2017.
 */

public interface DataCallBack {
    void ChuyenHoatDong(HoatDong hoatDong, int position, String key);
    void HoanThanhDangKy(String name, String email, String password);
}
