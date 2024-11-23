package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import product.Database.GetProductListDAOMySQL;
import product.Entity.ElectronicsProduct;
import product.Entity.FoodProduct;
import product.Entity.Product;
import product.UseCase.CalculateTotal.CalculateTotalStockUseCase;

public class TestCalculateTotalStock {
    private GetProductListDAOMySQL productDAO;
    private CalculateTotalStockUseCase calculateTotalStockUseCase;

    @BeforeEach
    public void setUp() {
        productDAO = mock(GetProductListDAOMySQL.class);
        calculateTotalStockUseCase = new CalculateTotalStockUseCase(productDAO);
    }

    @Test
    public void testCalculateTotalStock() {
        // Giả lập danh sách sản phẩm
        List<Product> productList = new ArrayList<>();
        productList.add(new ElectronicsProduct(1, "Product A", 10, 100.0, "Electronics", 12, 500.0));
        productList.add(new ElectronicsProduct(2, "Product B", 20, 150.0, "Electronics", 24, 600.0));
        productList.add(new FoodProduct(3, "Product C", 15, 200.0, "Food", null, null, "Supplier A"));

        // Giả lập hành vi của DAO
        when(productDAO.getAllProductList()).thenReturn(productList);

        // Thực hiện tính toán
        Map<String, Integer> totalStock = calculateTotalStockUseCase.execute();

        // Kiểm tra kết quả
        Map<String, Integer> expectedTotalStock = new HashMap<>();
        expectedTotalStock.put("Electronics", 30);
        expectedTotalStock.put("Food", 15);

        assertEquals(expectedTotalStock, totalStock, "Tổng số lượng tồn kho không đúng");

        // In kết quả ra console
        System.out.println("Tổng số lượng tồn kho: " + totalStock);
    }
}