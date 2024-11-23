package test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import product.Database.GetProductListDAOMySQL;
import product.Entity.ElectronicsProduct;
import product.Entity.Product;
import product.UseCase.UpdateProduct.UpdateProductController;
import product.UseCase.UpdateProduct.UpdateProductUseCase;

public class TestUpdateProduct {
    private GetProductListDAOMySQL productDAO;
    private UpdateProductController updateProductController;

    @BeforeEach
    public void setUp() {
        // Tạo mock cho GetProductListDAOMySQL
        productDAO = mock(GetProductListDAOMySQL.class);
        updateProductController = new UpdateProductController(new UpdateProductUseCase(productDAO));
    }

    @Test
    @DisplayName("Kiểm thử cập nhật sản phẩm thành công")
    public void testUpdateProductUsingAdd() {
        // Tạo sản phẩm ban đầu
        ElectronicsProduct originalProduct = new ElectronicsProduct(1, "Original Product", 10, 100.0, "Electronics", 12, 500.0);
        when(productDAO.addProduct(originalProduct)).thenReturn(true);
        
        boolean isAdded = productDAO.addProduct(originalProduct);
        System.out.println("=== THÊM SẢN PHẨM ===");
        System.out.println("Thông tin sản phẩm thêm mới: " + originalProduct);
        System.out.println("Kết quả thêm: " + (isAdded ? "Thành công" : "Thất bại"));
        System.out.println();
    
   
        ElectronicsProduct updatedProduct = new ElectronicsProduct(1, "Updated Product", 20, 150.0, "Electronics", 24, 600.0);
        when(productDAO.updateProduct(updatedProduct)).thenReturn(true);
        
        boolean isUpdated = updateProductController.updateProduct(updatedProduct);
        System.out.println("=== CẬP NHẬT SẢN PHẨM ===");
        System.out.println("Thông tin sản phẩm cập nhật: " + updatedProduct);
        System.out.println("Kết quả cập nhật: " + (isUpdated ? "Thành công" : "Thất bại"));
        System.out.println();
    
       
        when(productDAO.getAllProductList()).thenReturn(List.of(updatedProduct));
        Product retrievedProduct = productDAO.getAllProductList().stream()
            .filter(p -> p.getMaHang() == 1)
            .findFirst()
            .orElse(null);
    
        System.out.println("=== KẾT QUẢ KIỂM TRA ===");
        System.out.println("Sản phẩm sau khi cập nhật: " + retrievedProduct);
        
        assertNotNull(retrievedProduct, "Sản phẩm cập nhật không nên null");
        assertEquals("Updated Product", retrievedProduct.getTenHang(), "Tên sản phẩm nên được cập nhật");
        assertEquals(20, retrievedProduct.getSoLuong(), "Số lượng sản phẩm nên được cập nhật");
        assertEquals(150.0, retrievedProduct.getDonGia(), "Giá sản phẩm nên được cập nhật");
    }
    
}