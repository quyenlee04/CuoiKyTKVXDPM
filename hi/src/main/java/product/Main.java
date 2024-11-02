package product;

import product.Database.GetProductListDAOMySQL;
import product.UI.GetProductListController;
import product.UI.GetProductListPresenter;
import product.UseCase.GetProductListUseCase;

public class Main {
public static void main(String[] args) {
    GetProductListDAOMySQL getProductListDAOMySQL = new GetProductListDAOMySQL();
    GetProductListPresenter presenter = new GetProductListPresenter(getProductListDAOMySQL); 
    GetProductListUseCase useCase = new GetProductListUseCase(presenter, getProductListDAOMySQL);
    GetProductListController controller = new GetProductListController(useCase);
    controller.execute();
    }
}

