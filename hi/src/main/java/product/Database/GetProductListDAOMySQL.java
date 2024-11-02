package product.Database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import product.Entity.CeramicsProduct;
import product.Entity.ElectronicsProduct;
import product.Entity.FoodProduct;
import product.Entity.Product;
import product.UseCase.GetProductListDatabaseBoundary;

public class GetProductListDAOMySQL implements GetProductListDatabaseBoundary {
    private static final String URL = "jdbc:mysql://localhost:3306/products";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection connection;

    public GetProductListDAOMySQL() {
        try {
            this.connection = connect();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception (log it, rethrow it, etc.)
            this.connection = null; // or you could throw a custom unchecked exception
        }
    }

    // Constructor for testing
    public GetProductListDAOMySQL(Connection connection) {
        this.connection = connection;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public List<Product> getAllProductList() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product";  // Truy vấn tất cả từ bảng product

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int maHang = rs.getInt("maHang");
                String tenHang = rs.getString("tenHang");
                int soLuong = rs.getInt("soLuong");
                double donGia = rs.getDouble("donGia");
                String loaiHang = rs.getString("loaiHang");

                Product newProduct = null;
                switch (loaiHang) {
                    case "Electronics":
                        newProduct = getElectronicsProduct(maHang, tenHang, soLuong, donGia);
                        break;
                    case "Food":
                        newProduct = getFoodProduct(maHang, tenHang, soLuong, donGia);
                        break;
                    case "Ceramics":
                        newProduct = getCeramicsProduct(maHang, tenHang, soLuong, donGia);
                        break;
                    default:
                        System.out.println("Unknown product type: " + loaiHang);
                        break;
                }
    
                if (newProduct != null) {
                    products.add(newProduct);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    private ElectronicsProduct getElectronicsProduct(int maHang, String tenHang, int soLuong, double donGia) throws SQLException {
        String sql = "SELECT thoiGianBaoHanh, congSuat FROM electronicsproduct WHERE maHang = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maHang);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int thoiGianBaoHanh = rs.getInt("thoiGianBaoHanh");
                double congSuat = rs.getDouble("congSuat");
                return new ElectronicsProduct(maHang, tenHang, soLuong, donGia, "Electronics", thoiGianBaoHanh, congSuat);
            }
        }
        return null;
    }

    // Truy vấn chi tiết cho FoodProduct
    private FoodProduct getFoodProduct(int maHang, String tenHang, int soLuong, double donGia) throws SQLException {
        String sql = "SELECT ngaySanXuat, ngayHetHan, nhaCungCap FROM foodproduct WHERE maHang = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maHang);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Date ngaySanXuat = rs.getDate("ngaySanXuat");
                Date ngayHetHan = rs.getDate("ngayHetHan");
                String nhaCungCap = rs.getString("nhaCungCap");
                return new FoodProduct(maHang, tenHang, soLuong, donGia, "Food", ngaySanXuat, ngayHetHan, nhaCungCap);
            }
        }
        return null;
    }

    // Truy vấn chi tiết cho CeramicsProduct
    private CeramicsProduct getCeramicsProduct(int maHang, String tenHang, int soLuong, double donGia) throws SQLException {
        String sql = "SELECT nhaSanXuat, ngayNhapKho FROM ceramicsproduct WHERE maHang = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maHang);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String nhaSanXuat = rs.getString("nhaSanXuat");
                Date ngayNhapKho = rs.getDate("ngayNhapKho");
                return new CeramicsProduct(maHang, tenHang, soLuong, donGia, "Ceramics", nhaSanXuat, ngayNhapKho);
            }
        }
        return null;
    }

    public boolean addProduct(Product product) {

        if (product.getSoLuong() < 0) {
            System.out.println("Cannot add product: Quantity cannot be negative.");
            return false;
        }
        
        if (product instanceof FoodProduct) {
            FoodProduct food = (FoodProduct) product;
            if (food.getNgaySanXuat() == null || food.getNgayHetHan() == null) {
                System.out.println("Cannot add product: Production and expiration dates cannot be null for food products.");
                return false;
            }
        } else if (product instanceof CeramicsProduct) {
            CeramicsProduct ceramics = (CeramicsProduct) product;
            if (ceramics.getNgayNhapKho() == null) {
                System.out.println("Cannot add product: Warehouse entry date cannot be null for ceramics products.");
                return false;
            }
        }
        String baseSql = "INSERT INTO product (maHang, tenHang, soLuong, donGia, loaiHang) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(baseSql)) {
            pstmt.setInt(1, product.getMaHang());
            pstmt.setString(2, product.getTenHang());
            pstmt.setInt(3, product.getSoLuong());
            pstmt.setDouble(4, product.getDonGia());
            pstmt.setString(5, product.getLoaiHang());
            pstmt.executeUpdate();
    
            // Insert product-specific details based on type
            if (product instanceof ElectronicsProduct) {
                ElectronicsProduct electronics = (ElectronicsProduct) product;
                String sql = "INSERT INTO electronicsproduct (maHang, thoiGianBaoHanh, congSuat) VALUES (?, ?, ?)";
                try (PreparedStatement pstmtDetail = conn.prepareStatement(sql)) {
                    pstmtDetail.setInt(1, electronics.getMaHang());
                    pstmtDetail.setInt(2, electronics.getThoiGianBaoHanh());
                    pstmtDetail.setDouble(3, electronics.getCongSuat());
                    pstmtDetail.executeUpdate();
                }
            } else if (product instanceof FoodProduct) {
                FoodProduct food = (FoodProduct) product;
                String sql = "INSERT INTO foodproduct (maHang, ngaySanXuat, ngayHetHan, nhaCungCap) VALUES (?, ?, ?, ?)";
                try (PreparedStatement pstmtDetail = conn.prepareStatement(sql)) {
                    pstmtDetail.setInt(1, food.getMaHang());
                    pstmtDetail.setDate(2, new java.sql.Date(food.getNgaySanXuat().getTime()));
                    pstmtDetail.setDate(3, new java.sql.Date(food.getNgayHetHan().getTime()));
                    pstmtDetail.setString(4, food.getNhaCungCap());
                    pstmtDetail.executeUpdate();
                }
            } else if (product instanceof CeramicsProduct) {
                CeramicsProduct ceramics = (CeramicsProduct) product;
                String sql = "INSERT INTO ceramicsproduct (maHang, nhaSanXuat, ngayNhapKho) VALUES (?, ?, ?)";
                try (PreparedStatement pstmtDetail = conn.prepareStatement(sql)) {
                    pstmtDetail.setInt(1, ceramics.getMaHang());
                    pstmtDetail.setString(2, ceramics.getNhaSanXuat());
                    pstmtDetail.setDate(3, new java.sql.Date(ceramics.getNgayNhapKho().getTime()));
                    pstmtDetail.executeUpdate();

                    // Ret
                }
            } conn.commit();
            return true;
            
        }catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) connection.rollback(); // Roll back if an error occurs
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            return false;
        }
    }
}
