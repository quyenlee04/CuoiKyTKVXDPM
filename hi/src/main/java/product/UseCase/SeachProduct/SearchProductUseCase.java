package product.UseCase.SeachProduct;

import java.util.List;

import product.Database.GetProductListDAOMySQL;
import product.Entity.Product;

public class SearchProductUseCase {
    private final GetProductListDAOMySQL productListDAOMySQL;

    public SearchProductUseCase(GetProductListDAOMySQL productListDAOMySQL) {
        this.productListDAOMySQL = productListDAOMySQL;
    }

    public List<Product> execute() {
        return productListDAOMySQL.getProductsExpiringSoon();
    }
}