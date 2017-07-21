package com.stak.mysecretary.Model.ThemHoatDong;

import com.stak.mysecretary.Model.HoatDong;

import java.util.ArrayList;

/**
 * Created by Quang Trí on 7/16/2017.
 */

public interface ThemHoatDongImpl {
    void ThemHoatDongSQLite(HoatDong hoatdong);
    ArrayList<HoatDong> LayDuLieuSQLiteSoSanh();

}
