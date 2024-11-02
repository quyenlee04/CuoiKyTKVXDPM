package test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import product.Database.GetProductListDAOMySQL;
import product.Entity.ElectronicsProduct;
import product.Entity.FoodProduct;
import product.Entity.Product;

public class TestAddProduct {

    private GetProductListDAOMySQL productDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    @BeforeEach
    public void setUp() throws SQLException {
        mockConnection = Mockito.mock(Connection.class);
        mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        productDAO = new GetProductListDAOMySQL(mockConnection);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

    }

    @Test
    public void testAddValidProduct() throws SQLException {
        System.out.println("Starting testAddValidProduct...");
        
        
        Product validProduct = new ElectronicsProduct(34, "Laptop", -5, 1000.0, "Electronics", 24, 1500.0);
        

        boolean result = productDAO.addProduct(validProduct);
      
        assertTrue(result);
        verify(mockPreparedStatement).setInt(1, validProduct.getMaHang());
        verify(mockPreparedStatement).setString(2, validProduct.getTenHang());
        verify(mockPreparedStatement).setInt(3, validProduct.getSoLuong());
        verify(mockPreparedStatement).setDouble(4, validProduct.getDonGia());
        verify(mockPreparedStatement).setString(5, validProduct.getLoaiHang());
        verify(mockPreparedStatement).executeUpdate();

        System.out.println("Finished testAddValidProduct.");
    }

    @Test
    public void testAddInvalidProduct() throws SQLException {
        System.out.println("Starting testAddInvalidProduct...");
        
        // Arrange: Create an invalid FoodProduct with incorrect data
        Product invalidProduct = new FoodProduct(15, "Invalid ", 32, 50.0, "Food", null, null, "th");

        // Act: Call the addProduct method
        boolean result = productDAO.addProduct(invalidProduct);

        // Assert: Verify the product was not added due to invalid data
        assertFalse(result);

        System.out.println("Finished testAddInvalidProduct.");
    }
      @Test
    void testAddFoodProduct() {
         System.out.println("Starting testAddFoodProduct...");
    try {
        // Setting up valid Food product data
        int maHang = 101;
        String tenHang = "Apple";
        int soLuong = 50;
        double donGia = 10.5;
        String loaiHang = "Food";
        Date ngaySanXuat = Date.valueOf("2023-10-01");
        Date ngayHetHang = Date.valueOf("2024-10-01");
        String nhaCungCap = "SupplierA";

        // Create a FoodProduct object
        Product foodProduct = new FoodProduct(maHang, tenHang, soLuong, donGia, loaiHang, ngaySanXuat, ngayHetHang, nhaCungCap);

        // Act: Call the addProduct method
        boolean result = productDAO.addProduct(foodProduct);

        // Assert: Verify that the product was added successfully
        assertTrue(result);
        verify(mockPreparedStatement).setInt(1, foodProduct.getMaHang());
        verify(mockPreparedStatement).setString(2, foodProduct.getTenHang());
        verify(mockPreparedStatement).setInt(3, foodProduct.getSoLuong());
        verify(mockPreparedStatement).setDouble(4, foodProduct.getDonGia());
        verify(mockPreparedStatement).setString(5, foodProduct.getLoaiHang());
        verify(mockPreparedStatement).executeUpdate();

        System.out.println("Finished testAddFoodProduct.");
    } catch (SQLException e) {
        e.printStackTrace(); // Log the exception
        fail("SQLException was thrown: " + e.getMessage());
    }
    }}
