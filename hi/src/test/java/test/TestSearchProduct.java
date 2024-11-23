    package test;

    import java.sql.Date;
    import java.util.ArrayList;
    import java.util.List;

    import static org.junit.jupiter.api.Assertions.assertEquals;
    import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
    import static org.mockito.Mockito.when;

    import product.Database.GetProductListDAOMySQL;
    import product.Entity.FoodProduct;
    import product.Entity.Product;
    import product.UseCase.SeachProduct.SearchProductUseCase;

    public class TestSearchProduct {
        private GetProductListDAOMySQL productDAO;
        private SearchProductUseCase searchProductUseCase;

        @BeforeEach
        public void setUp() {
            productDAO = mock(GetProductListDAOMySQL.class);
            searchProductUseCase = new SearchProductUseCase(productDAO);
        }

        @Test
        public void testSearchProductsExpiringSoon() {
            List<Product> allProducts = new ArrayList<>();
            
           
            FoodProduct expiringProduct = new FoodProduct(1, "Product A", 10, 100.0, "Food", 
                Date.valueOf("2024-11-19"), Date.valueOf("2024-11-24"), "Supplier A");
            
            
            FoodProduct nonExpiringProduct = new FoodProduct(2, "Product B", 15, 200.0, "Food", 
                Date.valueOf("2024-10-02"), Date.valueOf("2024-12-30"), "Supplier B");
        
            
            allProducts.add(expiringProduct);
        
           
            when(productDAO.getProductsExpiringSoon()).thenReturn(allProducts);
        
            List<Product> expiringProducts = searchProductUseCase.searchProductsExpiringSoon();
            
            assertEquals(1, expiringProducts.size());
            assertEquals("Product A", expiringProducts.get(0).getTenHang());
            
            System.out.println("Sản phẩm hết hạn trong 7 ngày:");
            for (Product product : expiringProducts) {
                System.out.println(" - " + product.getTenHang());
            }
        }
        
    }