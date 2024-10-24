package com.product.UI;

public class GetProductListViewModel {
    public String maHang;
    public String tenHang;
    public String soLuong;
    public String donGia;
    public String loaiHang;
    public String tinhVAT;
   

    public GetProductListViewModel(String donGia, String loaiHang, String maHang, String soLuong, String tenHang, String tinhVAT) {
        this.donGia = donGia;
        this.loaiHang = loaiHang;
        this.maHang = maHang;
        this.soLuong = soLuong;
        this.tenHang = tenHang;
        this.tinhVAT = tinhVAT;
    }


}
