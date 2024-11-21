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
        try {
            connection.setAutoCommit(false);
            
            // Insert into main product table
            String baseSql = "INSERT INTO product (maHang, tenHang, soLuong, donGia, loaiHang) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(baseSql)) {
                pstmt.setInt(1, product.getMaHang());
                pstmt.setString(2, product.getTenHang());
                pstmt.setInt(3, product.getSoLuong());
                pstmt.setDouble(4, product.getDonGia());
                pstmt.setString(5, product.getLoaiHang());
                pstmt.executeUpdate();
                
                // Add product details
                if (addProductDetails(product)) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    

private boolean addProductDetails(Product product) {
    if (product instanceof ElectronicsProduct) {
        ElectronicsProduct electronics = (ElectronicsProduct) product;
        String sql = "INSERT INTO electronicsproduct (maHang, thoiGianBaoHanh, congSuat) VALUES (?, ?, ?)";
        try (PreparedStatement pstmtDetail = connection.prepareStatement(sql)) {
            pstmtDetail.setInt(1, electronics.getMaHang());
            pstmtDetail.setInt(2, electronics.getThoiGianBaoHanh());
            pstmtDetail.setDouble(3, electronics.getCongSuat());
            pstmtDetail.executeUpdate(); // Chỉ gọi một lần cho chi tiết sản phẩm
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    } else if (product instanceof FoodProduct) {
        FoodProduct food = (FoodProduct) product;
        String sql = "INSERT INTO foodproduct (maHang, ngaySanXuat, ngayHetHan, nhaCungCap) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmtDetail = connection.prepareStatement(sql)) {
            pstmtDetail.setInt(1, food.getMaHang());
            pstmtDetail.setDate(2, new java.sql.Date(food.getNgaySanXuat().getTime()));
            pstmtDetail.setDate(3, new java.sql.Date(food.getNgayHetHan().getTime()));
            pstmtDetail.setString(4, food.getNhaCungCap());
            pstmtDetail.executeUpdate(); // Chỉ gọi một lần cho chi tiết sản phẩm
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    } else if (product instanceof CeramicsProduct) {
        CeramicsProduct ceramics = (CeramicsProduct) product;
        String sql = "INSERT INTO ceramicsproduct (maHang, nhaSanXuat, ngayNhapKho) VALUES (?, ?, ?)";
        try (PreparedStatement pstmtDetail = connection.prepareStatement(sql)) {
            pstmtDetail.setInt(1, ceramics.getMaHang());
            pstmtDetail.setString(2, ceramics.getNhaSanXuat());
            pstmtDetail.setDate(3, new java.sql.Date(ceramics.getNgayNhapKho().getTime()));
            pstmtDetail.executeUpdate(); // Chỉ gọi một lần cho chi tiết sản phẩm
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    return true; // Trả về true nếu thêm chi tiết sản phẩm thành công
}

public boolean deleteProduct(int productId) {
    try {
        connection.setAutoCommit(false);
        
        // Delete from child tables first
        String[] deleteChildSQL = {
            "DELETE FROM electronicsproduct WHERE maHang = ?",
            "DELETE FROM foodproduct WHERE maHang = ?",
            "DELETE FROM ceramicsproduct WHERE maHang = ?"
        };
        
        for (String sql : deleteChildSQL) {
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, productId);
                pstmt.executeUpdate();
            }
        }
        
        // Then delete from parent table
        String deleteMainSQL = "DELETE FROM product WHERE maHang = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteMainSQL)) {
            pstmt.setInt(1, productId);
            int result = pstmt.executeUpdate();
            connection.commit();
            return result > 0;
        }
    } catch (SQLException e) {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}

public boolean updateProduct(Product product) {
    // Cập nhật thông tin chính của sản phẩm
    String sql = "UPDATE product SET tenHang = ?, soLuong = ?, donGia = ?, loaiHang = ? WHERE maHang = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, product.getTenHang());
        pstmt.setInt(2, product.getSoLuong());
        pstmt.setDouble(3, product.getDonGia());
        pstmt.setString(4, product.getLoaiHang());
        pstmt.setInt(5, product.getMaHang());

        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            // Cập nhật thông tin cho sản phẩm con dựa vào loại hàng
            if (product instanceof ElectronicsProduct) {
                ElectronicsProduct electronicsProduct = (ElectronicsProduct) product;
                String detailSql = "UPDATE electronicsproduct SET thoiGianBaoHanh = ?, congSuat = ? WHERE maHang = ?";
                try (PreparedStatement detailPstmt = connection.prepareStatement(detailSql)) {
                    detailPstmt.setInt(1, electronicsProduct.getThoiGianBaoHanh());
                    detailPstmt.setDouble(2, electronicsProduct.getCongSuat());
                    detailPstmt.setInt(3, electronicsProduct.getMaHang());
                    detailPstmt.executeUpdate();
                }
            } else if (product instanceof FoodProduct) {
                FoodProduct foodProduct = (FoodProduct) product;
                String detailSql = "UPDATE foodproduct SET ngaySanXuat = ?, ngayHetHan = ?, nhaCungCap = ? WHERE maHang = ?";
                try (PreparedStatement detailPstmt = connection.prepareStatement(detailSql)) {
                    detailPstmt.setDate(1, new java.sql.Date(foodProduct.getNgaySanXuat().getTime()));
                    detailPstmt.setDate(2, new java.sql.Date(foodProduct.getNgayHetHan().getTime()));
                    detailPstmt.setString(3, foodProduct.getNhaCungCap());
                    detailPstmt.setInt(4, foodProduct.getMaHang());
                    detailPstmt.executeUpdate();
                }
            } else if (product instanceof CeramicsProduct) {
                CeramicsProduct ceramicsProduct = (CeramicsProduct) product;
                String detailSql = "UPDATE ceramicsproduct SET nhaSanXuat = ?, ngayNhapKho = ? WHERE maHang = ?";
                try (PreparedStatement detailPstmt = connection.prepareStatement(detailSql)) {
                    detailPstmt.setString(1, ceramicsProduct.getNhaSanXuat());
                    detailPstmt.setDate(2, new java.sql.Date(ceramicsProduct.getNgayNhapKho().getTime()));
                    detailPstmt.setInt(3, ceramicsProduct.getMaHang());
                    detailPstmt.executeUpdate();
                }
            }
            return true; // Trả về true nếu cập nhật thành công
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; // Trả về false nếu có lỗi xảy ra
}

public List<Product> getProductsExpiringSoon() {
    List<Product> products = new ArrayList<>();
    String sql = "SELECT p.maHang, p.tenHang, p.soLuong, p.donGia, p.loaiHang " +
                 "FROM product p " +
                 "JOIN foodproduct f ON p.maHang = f.maHang " +
                 "WHERE f.ngayHetHan BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY)";

    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            int maHang = rs.getInt("maHang");
            String tenHang = rs.getString("tenHang");
            int soLuong = rs.getInt("soLuong");
            double donGia = rs.getDouble("donGia");
            String loaiHang = rs.getString("loaiHang");
          
            
                FoodProduct foodProduct = new FoodProduct(maHang, tenHang, soLuong, donGia, loaiHang, null, null, null);
                products.add(foodProduct);
            
             
            
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return products;
}
}
