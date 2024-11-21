package product.UseCase.UpdateProduct;

import product.Database.GetProductListDAOMySQL;
import product.Entity.Product;

public class UpdateProductUseCase implements UpdateProductInputBoundary{
private final GetProductListDAOMySQL productListDAOMySQL;

    public UpdateProductUseCase(GetProductListDAOMySQL productListDAOMySQL) {
        this.productListDAOMySQL = productListDAOMySQL;
    }

    @Override
    public boolean updateProduct(Product product) {
        // Logic để sửa sản phẩm
        return productListDAOMySQL.updateProduct(product);
    }
}
