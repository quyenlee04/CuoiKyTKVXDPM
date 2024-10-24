package com.product.Entity;

public class Product {
    protected int maHang;
    protected String tenHang;
    protected int soLuong;
    protected double donGia;
    protected String loaiHang;

    protected double tinhTongSoLuong;
    protected double tinhVAT;

    public Product(double donGia, String loaiHang, int maHang, int soLuong, String tenHang, double tinhTongSoLuong, double tinhVAT) {
        this.donGia = donGia;
        this.loaiHang = loaiHang;
        this.maHang = maHang;
        this.soLuong = soLuong;
        this.tenHang = tenHang;
        this.tinhTongSoLuong = tinhTongSoLuong;
        this.tinhVAT = tinhVAT;
    }

   



    public int getMaHang() {
        return maHang;
    }

    public String getTenHang() {
        return tenHang;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public String getLoaiHang() {
        return loaiHang;
    }


    public double getTinhTongSoLuong() {
        return tinhTongSoLuong;
    }

    public double getTinhVAT() {
        return tinhVAT;
    }

    

}
