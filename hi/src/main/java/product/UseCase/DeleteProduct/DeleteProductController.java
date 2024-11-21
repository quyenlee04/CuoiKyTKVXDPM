package product.UseCase.DeleteProduct;

public class DeleteProductController {
    private final DeleteProductUseCase deleteProductUseCase;

    public DeleteProductController(DeleteProductUseCase deleteProductUseCase) {
        this.deleteProductUseCase = deleteProductUseCase;
    }

    public boolean  deleteProduct(int productId) {
        boolean success = deleteProductUseCase.execute(productId);
        if (success) {
            // Thông báo thành công
            System.out.println("Xóa sản phẩm thành công!");
        } else {
            // Thông báo thất bại
            System.out.println("Không thể xóa sản phẩm.");
        }
        return success;
    }
    }

