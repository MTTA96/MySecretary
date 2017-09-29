package com.stak.mysecretary.Presenter;

import com.stak.mysecretary.Presenter.Model.DataModel.PresenterImpThemHoatDong;
import com.stak.mysecretary.Presenter.Model.UiModel.ThemHoatDongCallback;
import com.stak.mysecretary.Model.HoatDong;
import com.stak.mysecretary.Model.ThemHoatDong.ModelThemHoatDong;


import java.util.ArrayList;

/**
 * Created by Quang Trí on 7/16/2017.
 */

public class PresenterThemHoatDong implements PresenterImpThemHoatDong {
    ThemHoatDongCallback themHoatDongCallback;
    ModelThemHoatDong modelThemHoatDong;
    ArrayList<HoatDong> listhoatdong;
    int dem;

    public PresenterThemHoatDong(ThemHoatDongCallback themHoatDongCallback) {
        this.themHoatDongCallback = themHoatDongCallback;
    }

    @Override
    public void XuLyThemHoatDong(HoatDong hoatdong) {
        if (hoatdong.getTenhd().isEmpty() && hoatdong.getTgbd().isEmpty() && hoatdong.getTgkt().isEmpty()){
            themHoatDongCallback.ThongBaoThemLoi();
        }
        else {
            if (kiemtrangaythanggiophut(hoatdong.getTgbd(),hoatdong.getTgkt())==false){
                themHoatDongCallback.ThongBaoNhapLoi();
                return;
            }
            else {
                dem=0;
                listhoatdong=new ArrayList<>();
                listhoatdong=modelThemHoatDong.LayDuLieuSQLiteSoSanh();
                for (int i=0;i<listhoatdong.size();i++){
                    if (KiemTraTrung(hoatdong.getTgbd(),hoatdong.getTgkt(),listhoatdong.get(i).getTgbd(),listhoatdong.get(i).getTgkt(),listhoatdong.get(i).getTgkt(),listhoatdong.get(i).getThu())==false){
                        themHoatDongCallback.ThongBaoThemSuKienDaCo();
                        dem=1;
                        return;
                    }
                }
                if (dem==0){
                    modelThemHoatDong.ThemHoatDongSQLite(hoatdong);
                    themHoatDongCallback.ThemHoatDongThanhCong();
                    return;
                }
            }
        }

    }
    //Kiểm tra thời gian thêm có hợp lý không
    public boolean kiemtrangaythanggiophut(String thoigianbatdau,String thoigianketthuc){
        String[] tachngaygiothoigianbatdau=thoigianbatdau.split(" ");
        String[] tachngaygiothoigianketthuc=thoigianketthuc.split(" ");
        //Kiểm tra ngày tháng năm giờ phút
        String[] tachngaythangnambatdau=tachngaygiothoigianbatdau[0].split("/");
        String[] tachngaythangnamketthuc=tachngaygiothoigianketthuc[0].split("/");

        if (Integer.parseInt(tachngaythangnambatdau[2])>Integer.parseInt(tachngaythangnamketthuc[2])){
            return false;
        }
        else{
            //Kiểm tra tháng
            if (Integer.parseInt(tachngaythangnambatdau[2])==Integer.parseInt(tachngaythangnamketthuc[2])){
                if (Integer.parseInt(tachngaythangnambatdau[1])>Integer.parseInt(tachngaythangnamketthuc[1])){
                    return false;
                }
                else {
                    if (Integer.parseInt(tachngaythangnambatdau[1])==Integer.parseInt(tachngaythangnamketthuc[1])){
                        if (Integer.parseInt(tachngaythangnambatdau[0])>Integer.parseInt(tachngaythangnamketthuc[0])){
                            return false;
                        }
                        else {
                            String[] tachgiophutgiaybatdau=tachngaygiothoigianbatdau[1].split(":");
                            String[] tachgiophutgiayketthuc=tachngaygiothoigianketthuc[1].split(":");
                            if (Integer.parseInt(tachgiophutgiaybatdau[0])>Integer.parseInt(tachgiophutgiayketthuc[0])){
                                return false;
                            }
                            else {
                                if (Integer.parseInt(tachgiophutgiaybatdau[0])==Integer.parseInt(tachgiophutgiayketthuc[0])){
                                    if (Integer.parseInt(tachgiophutgiaybatdau[1])>Integer.parseInt(tachgiophutgiayketthuc[1])){
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    //kiểm tra hoạt động đã có
    public boolean KiemTraTrung(String thoigiandatbatdau,String thoigiandatketthuc,String thudat,String batdau,String ketthuc,String thu){
        String[] tachngaythangvagiodatbatdau=thoigiandatbatdau.split(" ");
        String[] tachngaythangnamdatbatdau=tachngaythangvagiodatbatdau[0].split("/");
        int ngaydatbatdau=Integer.parseInt(tachngaythangnamdatbatdau[0]);
        int thangdatbatdau=Integer.parseInt(tachngaythangnamdatbatdau[1]);
        int namdatbatdau=Integer.parseInt(tachngaythangnamdatbatdau[2]);
        String[] tachgiophutdatbatdau=tachngaythangvagiodatbatdau[1].split(":");
        int giodatbatdau=Integer.parseInt(tachgiophutdatbatdau[0]);
        int phutdatbatdau= Integer.parseInt(tachgiophutdatbatdau[1]);

        String[] tachngaythangvagiodatketthuc=thoigiandatketthuc.split(" ");
        String[] tachngaythangnamdatketthuc=tachngaythangvagiodatketthuc[0].split("/");
        int ngaydatketthuc=Integer.parseInt(tachngaythangnamdatketthuc[0]);
        int thangdatketthuc=Integer.parseInt(tachngaythangnamdatketthuc[1]);
        int namdatketthuc=Integer.parseInt(tachngaythangnamdatketthuc[2]);
        String[] tachgiophutdatketthuc=tachngaythangvagiodatketthuc[1].split(":");
        int giodatketthuc=Integer.parseInt(tachgiophutdatketthuc[0]);
        int phutdatketthuc= Integer.parseInt(tachgiophutdatketthuc[1]);

        String tachthudat[]=thudat.split(" ");

        String[] tachngaythangvagiobatdau=batdau.split(" ");
        String[] tachngaythangnambatdau=tachngaythangvagiobatdau[0].split("/");
        int ngaybatdau=Integer.parseInt(tachngaythangnambatdau[0]);
        int thangbatdau=Integer.parseInt(tachngaythangnambatdau[1]);
        int nambatdau=Integer.parseInt(tachngaythangnambatdau[2]);
        String[] tachgiophutbatdau=tachngaythangvagiobatdau[1].split(":");
        int giobatdau=Integer.parseInt(tachgiophutbatdau[0]);
        int phutbatdau= Integer.parseInt(tachgiophutbatdau[1]);

        String[] tachngaythangvagioketthuc=ketthuc.split(" ");
        String[] tachngaythangnamketthuc=tachngaythangvagioketthuc[0].split("/");
        int ngayketthuc=Integer.parseInt(tachngaythangnamketthuc[0]);
        int thangketthuc=Integer.parseInt(tachngaythangnamketthuc[1]);
        int namketthuc=Integer.parseInt(tachngaythangnamketthuc[2]);
        String[] tachgiophutketthuc=tachngaythangvagioketthuc[1].split(":");
        int giokethuc=Integer.parseInt(tachgiophutketthuc[0]);
        int phutketthuc= Integer.parseInt(tachgiophutketthuc[1]);

        String tachthu[] =thu.split(" ");

        if (namdatbatdau>=nambatdau && namdatketthuc<=namketthuc) {
            KiemTraThang(thangdatbatdau,thangdatketthuc,thangbatdau,thangketthuc,ngaydatbatdau,ngaydatketthuc,ngaybatdau,ngayketthuc,giodatbatdau,phutdatbatdau,tachthudat,giobatdau,phutbatdau,giokethuc,phutdatketthuc,phutketthuc,tachthu);
        }
        if (namdatbatdau<nambatdau && namdatketthuc<namketthuc) {
            KiemTraThang(thangdatbatdau,thangdatketthuc,thangbatdau,thangketthuc,ngaydatbatdau,ngaydatketthuc,ngaybatdau,ngayketthuc,giodatbatdau,phutdatbatdau,tachthudat,giobatdau,phutbatdau,giokethuc,phutdatketthuc,phutketthuc,tachthu);
        }
        if (namdatbatdau>nambatdau && namdatketthuc>namketthuc) {
            KiemTraThang(thangdatbatdau,thangdatketthuc,thangbatdau,thangketthuc,ngaydatbatdau,ngaydatketthuc,ngaybatdau,ngayketthuc,giodatbatdau,phutdatbatdau,tachthudat,giobatdau,phutbatdau,giokethuc,phutdatketthuc,phutketthuc,tachthu);
        }
        if (namdatbatdau<nambatdau && namdatketthuc>namketthuc) {
            KiemTraThang(thangdatbatdau,thangdatketthuc,thangbatdau,thangketthuc,ngaydatbatdau,ngaydatketthuc,ngaybatdau,ngayketthuc,giodatbatdau,phutdatbatdau,tachthudat,giobatdau,phutbatdau,giokethuc,phutdatketthuc,phutketthuc,tachthu);
        }
        return true;
    }

    //Kiểm tra trùng thứ giờ phut
    public Boolean KiemGioPhut(int giodatbatdau,int phutdatbatdau,String[] tachthudat,int giobatdau,int phutbatdau,int giokethuc,int phutdatketthuc,int phutketthuc,String[] tachthu){
        for (int i = 0; i < tachthudat.length; i++) {
            for (int j = 0; j < tachthu.length; j++) {
                if (Integer.parseInt(tachthudat[i]) == Integer.parseInt(tachthu[j])) {
                    if (giodatbatdau * 60 + phutdatbatdau > giobatdau * 60 + phutbatdau && giokethuc * 60 + phutdatketthuc < giokethuc * 60 + phutketthuc)
                        return false;
                    if (giodatbatdau * 60 + phutdatbatdau < giobatdau * 60 + phutbatdau && giokethuc * 60 + phutdatketthuc > giokethuc * 60 + phutketthuc)
                        return false;
                    if (giodatbatdau * 60 + phutdatbatdau > giobatdau * 60 + phutbatdau && giodatbatdau * 60 + phutdatbatdau < giokethuc * 60 + phutketthuc || giokethuc * 60 + phutdatketthuc > giobatdau * 60 + phutbatdau && giokethuc * 60 + phutdatketthuc < giokethuc * 60 + phutketthuc)
                        return false;
                }
            }
        }

        return true;
    }

    //kiểm tra trùng ngày
    public boolean KiemTraNgay(int ngaydatbatdau,int ngaydatketthuc,int ngaybatdau,int ngayketthuc,int giodatbatdau,int phutdatbatdau,String[] tachthudat,int giobatdau,int phutbatdau,int giokethuc,int phutdatketthuc,int phutketthuc,String[] tachthu) {
        if (ngaydatbatdau >= ngaybatdau && ngaydatketthuc <= ngayketthuc) {
            KiemGioPhut(giodatbatdau, phutdatbatdau, tachthudat, giobatdau, phutbatdau, giokethuc, phutdatketthuc, phutketthuc, tachthu);
        }
        if (ngaydatbatdau < ngaybatdau && ngaydatketthuc < ngayketthuc) {
            KiemGioPhut(giodatbatdau, phutdatbatdau, tachthudat, giobatdau, phutbatdau, giokethuc, phutdatketthuc, phutketthuc, tachthu);
        }
        if (ngaydatbatdau > ngaybatdau && ngaydatketthuc > ngayketthuc) {
            KiemGioPhut(giodatbatdau, phutdatbatdau, tachthudat, giobatdau, phutbatdau, giokethuc, phutdatketthuc, phutketthuc, tachthu);
        }
        if (ngaydatbatdau < ngaybatdau && ngaydatketthuc > ngayketthuc) {
            KiemGioPhut(giodatbatdau, phutdatbatdau, tachthudat, giobatdau, phutbatdau, giokethuc, phutdatketthuc, phutketthuc, tachthu);
        }
        return true;
    }

    //Kiểm tra trùng tháng
    public boolean KiemTraThang(int thangdatbatdau,int thangdatketthuc,int thangbatdau,int thangketthuc,int ngaydatbatdau,int ngaydatketthuc,int ngaybatdau,int ngayketthuc,int giodatbatdau,int phutdatbatdau,String[] tachthudat,int giobatdau,int phutbatdau,int giokethuc,int phutdatketthuc,int phutketthuc,String[] tachthu){
        if (thangdatbatdau >= thangbatdau && thangdatketthuc <= thangketthuc) {
            KiemTraNgay(ngaydatbatdau,ngaydatketthuc,ngaybatdau,ngayketthuc,giodatbatdau,phutdatbatdau,tachthudat,giobatdau,phutbatdau,giokethuc,phutdatketthuc,phutketthuc,tachthu);
        }
        if (thangdatbatdau < thangbatdau && thangdatketthuc < thangketthuc) {
            KiemTraNgay(ngaydatbatdau,ngaydatketthuc,ngaybatdau,ngayketthuc,giodatbatdau,phutdatbatdau,tachthudat,giobatdau,phutbatdau,giokethuc,phutdatketthuc,phutketthuc,tachthu);
        }
        if (thangdatbatdau > thangbatdau && thangdatketthuc > thangketthuc) {
            KiemTraNgay(ngaydatbatdau,ngaydatketthuc,ngaybatdau,ngayketthuc,giodatbatdau,phutdatbatdau,tachthudat,giobatdau,phutbatdau,giokethuc,phutdatketthuc,phutketthuc,tachthu);
        }
        if (thangdatbatdau < thangbatdau && thangdatketthuc > thangketthuc) {
            KiemTraNgay(ngaydatbatdau,ngaydatketthuc,ngaybatdau,ngayketthuc,giodatbatdau,phutdatbatdau,tachthudat,giobatdau,phutbatdau,giokethuc,phutdatketthuc,phutketthuc,tachthu);
        }
        return true;
    }
}
