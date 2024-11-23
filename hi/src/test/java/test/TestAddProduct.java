package test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import product.Database.GetProductListDAOMySQL;
import product.Entity.ElectronicsProduct;
import product.Entity.FoodProduct;
import product.Entity.Product;

public class TestAddProduct {
    private GetProductListDAOMySQL productDAO;
    private PreparedStatement mockPreparedStatement;
    private Connection mockConnection;
    private static final Logger logger = Logger.getLogger(TestAddProduct.class.getName());

    @BeforeEach
    public void setUp() throws SQLException {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        
        // Instantiate your DAO with the mock connection
        productDAO = new GetProductListDAOMySQL(mockConnection);
    }

    @Test
    @DisplayName("Test adding a valid electronics product successfully")
    public void testAddValidProduct() throws SQLException {
        logger.info("Bắt đầu kiểm tra thêm sản phẩm điện tử...");
        System.out.println("Bắt đầu kiểm tra thêm sản phẩm điện tử...");
    
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        
        Product validProduct = new ElectronicsProduct(
            1, "Test Laptop", 10, 10000, "Electrics", 12, 500.0
        );
        
        boolean result = productDAO.addProduct(validProduct);
        
        assertTrue(result, "Sản phẩm phải được thêm thành công");
        logger.info("Kết thúc kiểm tra. Kết quả: " + (result ? "Thành công" : "Thất bại"));
        System.out.println("Kết thúc kiểm tra. Kết quả: " + (result ? "Thành công" : "Thất bại"));
    }
    
    @Test
    @DisplayName("Test adding a valid food product successfully")
    public void testAddValidFoodProduct() throws SQLException {
        logger.info("Bắt đầu kiểm tra thêm sản phẩm thực phẩm...");
        System.out.println("Bắt đầu kiểm tra thêm sản phẩm thực phẩm...");
    
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        Date currentDate = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(System.currentTimeMillis() + 86400000);
        
        Product validProduct = new FoodProduct(
            2, "Test Food", 20, 50.0, "Food", currentDate, expiryDate, "Test Supplier"
        );
        
        boolean result = productDAO.addProduct(validProduct);
        
        assertTrue(result, "Sản phẩm thực phẩm phải được thêm thành công");
        logger.info("Kết thúc kiểm tra. Kết quả: " + (result ? "Thành công" : "Thất bại"));
        System.out.println("Kết thúc kiểm tra. Kết quả: " + (result ? "Thành công" : "Thất bại"));
    }
    
    @Test
    @DisplayName("Test adding an invalid product")
    public void testAddInvalidProduct() throws SQLException {
        logger.info("Bắt đầu kiểm tra thêm sản phẩm không hợp lệ...");
        System.out.println("Bắt đầu kiểm tra thêm sản phẩm không hợp lệ...");
    
        // Tạo sản phẩm không hợp lệ với ngày hết hạn trước ngày sản xuất
        Date currentDate = new Date(System.currentTimeMillis());
        Date invalidExpiryDate = new Date(System.currentTimeMillis() - 86400000); // Yesterday
        
        Product invalidProduct = new FoodProduct(
            15, 
            "",  
            -1,  
            -50.0, 
            "Food", 
            currentDate,
            invalidExpiryDate, 
            "" 
        );
    
        
        when(mockPreparedStatement.executeUpdate()).thenThrow(new SQLException("Invalid product data"));
    
        boolean result = productDAO.addProduct(invalidProduct);
    
        assertFalse(result, "Sản phẩm không hợp lệ không được thêm vào");
        logger.info("Kết thúc kiểm tra. Kết quả: " + (result ? "Thành công" : "Thất bại"));
        System.out.println("Kết thúc kiểm tra. Kết quả: " + (result ? "Thành công" : "Thất bại"));
    }  
}