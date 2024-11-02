package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
}