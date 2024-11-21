    package product.Entity;

    import java.util.Date;

    public class CeramicsProduct extends Product {

        private String nhaSanXuat;
        private Date ngayNhapKho;

        public CeramicsProduct(int maHang, String tenHang, int soLuong, double donGia, String loaiHang, String nhaSanXuat,Date ngayNhapKho) {
            super(maHang, tenHang, soLuong, donGia, "Ceramics");
            this.ngayNhapKho = ngayNhapKho;
            this.nhaSanXuat = nhaSanXuat;
        }

        @Override
        public double tinhTongSoLuong() {
            return soLuong;
        }

        @Override
        public double tinhVAT() {
            return donGia * 0.10;
        }
        public String getNhaSanXuat() {
            return nhaSanXuat;
        }

        public void setNhaSanXuat(String nhaSanXuat) {
            this.nhaSanXuat = nhaSanXuat;
        }

        public Date getNgayNhapKho() {
            return ngayNhapKho;
        }

        public void setNgayNhapKho(Date ngayNhapKho) {
            this.ngayNhapKho = ngayNhapKho;
        }
    }
