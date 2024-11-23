package product.UseCase.CalculateTotal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import product.Database.GetProductListDAOMySQL;
import product.Entity.Product;

public class CalculateTotalStockUseCase {
private final GetProductListDAOMySQL productListDAOMySQL;

    public CalculateTotalStockUseCase(GetProductListDAOMySQL productListDAOMySQL) {
        this.productListDAOMySQL = productListDAOMySQL;
    }

    public Map<String, Integer> execute() {
        List<Product> productList = productListDAOMySQL.getAllProductList();
        Map<String, Integer> totalStock = new HashMap<>();

        for (Product product : productList) {
            String productType = product.getLoaiHang();
            int quantity = product.getSoLuong();
            totalStock.put(productType, totalStock.getOrDefault(productType, 0) + quantity);
        }

        return totalStock;
    }
}
