package product.Entity;

import java.util.Date;



public class FoodProduct extends Product{
    private Date ngaySanXuat;
    private Date ngayHetHan;
    private String nhaCungCap;

    public FoodProduct(int maHang, String tenHang, int soLuong, double donGia, String loaiHang,Date ngayHetHan, Date ngaySanXuat, String nhaCungCap ) {
        super(maHang, tenHang, soLuong, donGia, "Food");
        this.ngayHetHan = ngayHetHan;
        this.ngaySanXuat = ngaySanXuat;
        this.nhaCungCap = nhaCungCap;
    }

    

    @Override
    public double tinhTongSoLuong() {
        return soLuong;
    }

    @Override
    public double tinhVAT() {
        return donGia * 0.05;
    }

    public Date getNgaySanXuat() {
        return ngaySanXuat;
    }

    public void setNgaySanXuat(Date ngaySanXuat) {
        this.ngaySanXuat = ngaySanXuat;
    }

    public Date getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(Date ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public String getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(String nhaSanXuat) {
        this.nhaCungCap = nhaSanXuat;
    }
    
}
