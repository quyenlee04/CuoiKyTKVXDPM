package product.UseCase.AddProduct;

import product.Entity.Product;

public class AddProductController {
private final AddProductUseCase addProductUseCase;

    public AddProductController(AddProductUseCase addProductUseCase) {
        this.addProductUseCase = addProductUseCase;
    }

    public void addProduct(Product product) {
        boolean success = addProductUseCase.execute(product);
        if (success) {
            // Thông báo thành công
        } else {
            // Thông báo thất bại
        }
    }
}
