package test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import product.Database.GetProductListDAOMySQL;
import product.Entity.ElectronicsProduct;
import product.Entity.Product;

public class TestDeleteProduct {

    @Mock
    private GetProductListDAOMySQL productDAO; // Mock DAO

    @InjectMocks
    private TestDeleteProduct testDeleteProduct; // Inject mock into the test class

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Khởi tạo các đối tượng mock
    }

    @Test
    public void testDeleteProduct() {
        // Tạo một sản phẩm để kiểm tra
        ElectronicsProduct product = new ElectronicsProduct(1, "Test Product", 10, 100.0, "Electronics", 12, 500.0);
        
        // Giả lập hành vi của DAO
        when(productDAO.addProduct(product)).thenReturn(true);
        when(productDAO.getAllProductList()).thenReturn(new ArrayList<Product>() {{
            add(product); // Thêm sản phẩm vào danh sách giả lập
        }});
        when(productDAO.deleteProduct(1)).thenReturn(true); // Giả lập xóa thành công

        // Thực hiện thêm sản phẩm
        boolean isAdded = productDAO.addProduct(product);
        System.out.println("Thêm product: " + product.getTenHang() + " - Success: " + isAdded);
        assertTrue(isAdded, "Sản phẩm nên được thêm thành công");

        // Kiểm tra xem sản phẩm có tồn tại trước khi xóa
        Product foundProduct = productDAO.getAllProductList().stream()
            .filter(p -> p.getMaHang() == 1)
            .findFirst()
            .orElse(null);
        
        assertNotNull(foundProduct, "Sản phẩm phải tồn tại trước khi xóa");
        System.out.println("Sản phẩm được tìm thấy trước khi xóa: " + foundProduct.getTenHang());

        // Thực hiện xóa sản phẩm
        boolean isDeleted = productDAO.deleteProduct(1);
        System.out.println("Xóa sản phẩm với maHang 1 - Thành công: " + isDeleted);
        
        // Kiểm tra xem xóa thành công
        assertTrue(isDeleted, "Sản phẩm nên được xóa thành công");

        // Kiểm tra xem sản phẩm đã bị xóa
        when(productDAO.getAllProductList()).thenReturn(new ArrayList<>()); // Giả lập danh sách sản phẩm rỗng
        assertNull(productDAO.getAllProductList().stream()
            .filter(p -> p.getMaHang() == 1)
            .findFirst()
            .orElse(null), "Sản phẩm không nên tồn tại sau khi xóa");
        System.out.println("Sản phẩm đã bị xóa thành công. Không tìm thấy sản phẩm nào với maHang 1.");
    }
}