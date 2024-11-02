package product.UI;

public class GetProductListViewModel  {
    public int maHang;
    public String tenHang;
    public int soLuong;  
    public double donGia; 
    public String loaiHang;
    public double tinhVAT;
   

    public GetProductListViewModel(int maHang, String tenHang, int soLuong, double donGia, String loaiHang, double tinhVAT) {
        this.donGia = donGia;
        this.loaiHang = loaiHang;
        this.maHang = maHang;
        this.soLuong = soLuong;
        this.tenHang = tenHang;
        this.tinhVAT = tinhVAT;
    }


}
