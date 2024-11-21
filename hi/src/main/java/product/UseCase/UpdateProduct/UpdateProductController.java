package product.UseCase.UpdateProduct;

import product.Entity.Product;

public class UpdateProductController {
 private final UpdateProductInputBoundary updateProductInputBoundary;

    public UpdateProductController(UpdateProductInputBoundary updateProductInputBoundary) {
        this.updateProductInputBoundary = updateProductInputBoundary;
    }

    public boolean updateProduct(Product product) {
        return updateProductInputBoundary.updateProduct(product);
    }
}
