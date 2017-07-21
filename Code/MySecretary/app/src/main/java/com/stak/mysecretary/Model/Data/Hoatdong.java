package com.stak.mysecretary.Model.Data;

import java.io.Serializable;

/**
 * Created by ADMIN on 3/24/2017.
 */

public class HoatDong implements Serializable{
    private String tenhd;
    private String diadiem;
    private String tgbd;
    private String tgkt;
    private String thu;
    private String nhom;
    private String ghichu;
    private String background;
    private String keyupdate;

    public String getKeyupdate() {
        return keyupdate;
    }

    public void setKeyupdate(String keyupdate) {
        this.keyupdate = keyupdate;
    }
    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getTenhd() {
        return tenhd;
    }

    public void setTenhd(String tenhd) {
        this.tenhd = tenhd;
    }

    public String getDiadiem() {
        return diadiem;
    }

    public void setDiadiem(String diadiem) {
        this.diadiem = diadiem;
    }

    public String getTgbd() {
        return tgbd;
    }

    public void setTgbd(String tgbd) {
        this.tgbd = tgbd;
    }

    public String getTgkt() {
        return tgkt;
    }

    public void setTgkt(String tgkt) {
        this.tgkt = tgkt;
    }

    public String getNhom() {
        return nhom;
    }

    public void setNhom(String nhom) {
        this.nhom = nhom;
    }

    public String getThu() {
        return thu;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

}
