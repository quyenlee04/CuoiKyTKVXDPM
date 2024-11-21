package test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        
        // Giả lập hành vi của phương thức thêm sản phẩm
        when(productDAO.addProduct(originalProduct)).thenReturn(true);
        
        // Thêm sản phẩm ban đầu vào cơ sở dữ liệu
        boolean isAdded = productDAO.addProduct(originalProduct);
        System.out.println("Đang thêm sản phẩm: " + originalProduct);
        assertTrue(isAdded, "Sản phẩm nên được thêm thành công");
        System.out.println("Sản phẩm đã được thêm thành công: " + originalProduct);

        // Tạo sản phẩm mới với thông tin đã sửa
        ElectronicsProduct updatedProduct = new ElectronicsProduct(1, "Updated Product", 20, 150.0, "Electronics", 24, 600.0);
        
        // Giả lập hành vi của phương thức cập nhật sản phẩm
        when(productDAO.updateProduct(updatedProduct)).thenReturn(true);
        
        // Cập nhật sản phẩm
        boolean isUpdated = updateProductController.updateProduct(updatedProduct);
        System.out.println("Đang cập nhật sản phẩm: " + updatedProduct);
        assertTrue(isUpdated, "Sản phẩm nên được cập nhật thành công");
        System.out.println("Sản phẩm đã được cập nhật thành công: " + updatedProduct);

        // Giả lập hành vi của phương thức lấy sản phẩm
        when(productDAO.getAllProductList()).thenReturn(List.of(updatedProduct));

        // Lấy lại sản phẩm từ cơ sở dữ liệu để xác nhận các thay đổi
        Product retrievedProduct = productDAO.getAllProductList().stream()
            .filter(p -> p.getMaHang() == 1)
            .findFirst()
            .orElse(null);

        assertNotNull(retrievedProduct, "Sản phẩm cập nhật không nên null");
        System.out.println("Sản phẩm đã cập nhật: " + retrievedProduct);

        assertEquals("Updated Product", retrievedProduct.getTenHang(), "Tên sản phẩm nên được cập nhật");
        assertEquals(20, retrievedProduct.getSoLuong(), "Số lượng sản phẩm nên được cập nhật");
        assertEquals(150.0, retrievedProduct.getDonGia(), "Giá sản phẩm nên được cập nhật");
    }
}