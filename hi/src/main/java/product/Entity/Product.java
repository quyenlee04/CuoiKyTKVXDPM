package product.Entity;

public abstract class Product {
    protected int maHang;
    protected String tenHang;
    protected int soLuong;
    protected double donGia;
    protected String loaiHang;

    
    

    public Product(int maHang, String tenHang,  int soLuong, double donGia, String loaiHang) {
        this.donGia = donGia;
        this.loaiHang = loaiHang;
        this.maHang = maHang;
        this.soLuong = soLuong;
        this.tenHang = tenHang;
        
    }

   
    public abstract double tinhTongSoLuong();
    public abstract double tinhVAT();

    


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

}
