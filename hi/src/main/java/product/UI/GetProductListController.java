package product.UI;

import product.UseCase.GetProductListInputBoundary;

public class GetProductListController {
    private GetProductListInputBoundary getProductListInputBoundary = null;

    public GetProductListController(GetProductListInputBoundary productListInputBoundary) {
        this.getProductListInputBoundary = productListInputBoundary;
    }
    public void execute() {
        getProductListInputBoundary.execute();
    }
}
