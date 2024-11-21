package product.UseCase.CalculateTotal;

import java.util.Map;


public class CalculateTotalStockController {
    private final CalculateTotalStockUseCase calculateTotalStockUseCase;

    public CalculateTotalStockController(CalculateTotalStockUseCase calculateTotalStockUseCase) {
        this.calculateTotalStockUseCase = calculateTotalStockUseCase;
    }

    public Map<String, Integer> calculateTotalStock() {
        return calculateTotalStockUseCase.execute();
    }
}
