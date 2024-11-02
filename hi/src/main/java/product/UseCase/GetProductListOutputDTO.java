package product.UseCase;

public class GetProductListOutputDTO {

    protected int maHang;
    protected String tenHang;
    protected int soLuong;
    protected double donGia;
    protected String loaiHang;

    protected double tinhTongSoLuong;
    protected double tinhVAT;

    public GetProductListOutputDTO(int maHang, String tenHang,int soLuong, double donGia, String loaiHang,double tinhTongSoLuong, double tinhVAT) {
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
