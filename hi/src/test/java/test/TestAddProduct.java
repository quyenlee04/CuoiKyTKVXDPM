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
    logger.info("Starting testAddValidProduct...");
    
    // Arrange
    when(mockPreparedStatement.executeUpdate()).thenReturn(1);
    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    
    Product validProduct = new ElectronicsProduct(
        1, 
        "Test Laptop", 
        10, 
        10000, 
        "Electrics", 
        12, 
        500.0
    );
    
    // Act
    boolean result = productDAO.addProduct(validProduct);
    
    // Assert
    assertTrue(result, "Product should be added successfully");
    logger.info("Finished testAddValidProduct. Result: " + result);
}

@Test
@DisplayName("Test adding a valid food product successfully")
public void testAddValidFoodProduct() throws SQLException {
    logger.info("Starting testAddValidFoodProduct...");
    
    // Arrange
    when(mockPreparedStatement.executeUpdate()).thenReturn(1);
    Date currentDate = new Date(System.currentTimeMillis());
    Date expiryDate = new Date(System.currentTimeMillis() + 86400000); // tomorrow
    
    Product validProduct = new FoodProduct(
        2,
        "Test Food",
        20,
        50.0,
        "Food",
        currentDate,
        expiryDate,
        "Test Supplier"
    );
    
    // Act
    boolean result = productDAO.addProduct(validProduct);
    
    // Assert
    assertTrue(result, "Food product should be added successfully");
    logger.info("Finished testAddValidFoodProduct. Result: " + result);
}



    @Test
    @DisplayName("Test adding an invalid product")
    public void testAddInvalidProduct() throws SQLException {
        logger.info("Starting testAddInvalidProduct...");
        
        // Arrange: Create an invalid FoodProduct with incorrect data
        Product invalidProduct = new FoodProduct(15, "Invalid", 32, 50.0, "Food", null, null, "th");

        // Act: Call the addProduct method
        boolean result = productDAO.addProduct(invalidProduct);

        // Assert: Verify the product was not added due to invalid data
        assertFalse(result , "Product should not be added due to invalid data.");
        logger.info("Finished testAddInvalidProduct. Result: " + result);
    }
    @Test
@DisplayName("Test adding a product with negative quantity")
public void testAddProductWithNegativeQuantity() throws SQLException {
    logger.info("Starting testAddProductWithNegativeQuantity...");

    Product invalidProduct = new ElectronicsProduct(35, "Laptop", -5, 1000.0, "Electronics", 24, 1500.0);

    boolean result = productDAO.addProduct(invalidProduct);

    assertFalse(result, "Product should not be added due to negative quantity.");
    logger.info("Finished testAddProductWithNegativeQuantity. Result: " + result);
}
}