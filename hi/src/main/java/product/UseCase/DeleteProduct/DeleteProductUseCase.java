package product.UseCase.DeleteProduct;

import product.Database.GetProductListDAOMySQL;

public class DeleteProductUseCase {
     private final GetProductListDAOMySQL productListDAOMySQL;

    public DeleteProductUseCase(GetProductListDAOMySQL productListDAOMySQL) {
        this.productListDAOMySQL = productListDAOMySQL;
    }

    public boolean execute(int productId) {
        // Logic để xóa sản phẩm
        return productListDAOMySQL.deleteProduct(productId);
    }
}
