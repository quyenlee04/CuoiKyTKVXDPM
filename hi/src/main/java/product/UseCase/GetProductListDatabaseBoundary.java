package product.UseCase;

import java.util.List;

import product.Entity.Product;

public interface GetProductListDatabaseBoundary {
    List<Product> getAllProductList();
}
