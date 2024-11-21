package product.UseCase.AddProduct;

import product.Database.GetProductListDAOMySQL;
import product.Entity.Product;

public class AddProductUseCase {
     private final GetProductListDAOMySQL productListDAOMySQL;

    public AddProductUseCase(GetProductListDAOMySQL productListDAOMySQL) {
        this.productListDAOMySQL = productListDAOMySQL;
    }

    public boolean execute(Product product) {
        // Logic để thêm sản phẩm
        return productListDAOMySQL.addProduct(product);
    }
}
