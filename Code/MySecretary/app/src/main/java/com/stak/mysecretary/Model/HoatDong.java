package com.stak.mysecretary.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ADMIN on 3/24/2017.
 */

@Entity(nameInDb = "HOATDONG")
public class HoatDong{
    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "TENHOATDONG")
    private String tenhd;

    @Property(nameInDb = "DIADIEM")
    private String diadiem;

    @Property(nameInDb = "THOIGIANBATDAU")
    private String tgbd;

    @Property(nameInDb = "THOIGIANKETTHUC")
    private String tgkt;

    @Property(nameInDb = "NHOM")
    private String nhom;

    @Property(nameInDb = "THU")
    private String thu;

    @Property(nameInDb = "GHICHU")
    private String ghichu;

//    public HoatDong(String tenhd, String diadiem, String tgbd, String tgkt, String nhom, String thu, String ghichu) {
//        this.tenhd = tenhd;
//        this.diadiem = diadiem;
//        this.tgbd = tgbd;
//        this.tgkt = tgkt;
//        this.nhom = nhom;
//        this.thu = thu;
//        this.ghichu = ghichu;
//    }

    public HoatDong() {

    }

    @Generated(hash = 1194060535)
    public HoatDong(Long id, String tenhd, String diadiem, String tgbd, String tgkt, String nhom, String thu, String ghichu) {
        this.id = id;
        this.tenhd = tenhd;
        this.diadiem = diadiem;
        this.tgbd = tgbd;
        this.tgkt = tgkt;
        this.nhom = nhom;
        this.thu = thu;
        this.ghichu = ghichu;
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

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
