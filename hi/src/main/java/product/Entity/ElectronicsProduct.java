package product.Entity;

public class ElectronicsProduct extends Product{
    private int thoiGianBaoHanh;
    private double congSuat;

    public ElectronicsProduct( int maHang,String tenHang, int soLuong,double donGia, String loaiHang, int thoiGianBaoHanh, double congSuat) {
        super(maHang,tenHang, soLuong,donGia, "Electrics");
        this.thoiGianBaoHanh = thoiGianBaoHanh;
        this.congSuat = congSuat;
    }

    @Override
    public double tinhTongSoLuong() {
        return soLuong;
    }

    @Override
    public double tinhVAT() {
        return donGia * 0.10;
    }

    public int getThoiGianBaoHanh() {
        return thoiGianBaoHanh;
    }

    public void setThoiGianBaoHanh(int thoiGianBaoHanh) {
        this.thoiGianBaoHanh = thoiGianBaoHanh;
    }

    public double getCongSuat() {
        return congSuat;
    }

    public void setCongSuat(double congSuat) {
        this.congSuat = congSuat;
    }

    


}
