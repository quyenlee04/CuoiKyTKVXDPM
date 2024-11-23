package product.UseCase.SeachProduct;

import java.util.List;

import product.Entity.Product;

public class SearchProductController {
    private final SearchProductUseCase searchProductUseCase;

    public SearchProductController(SearchProductUseCase searchProductUseCase) {
        this.searchProductUseCase = searchProductUseCase;
    }

    public List<Product> searchProductsExpiringSoon() {
        
        return searchProductUseCase.execute();
    }
}