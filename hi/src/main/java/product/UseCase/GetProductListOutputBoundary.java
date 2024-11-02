package product.UseCase;

import java.util.List;

public interface GetProductListOutputBoundary {
    void exportResult(ResponseData responseData );
    void present (List<GetProductListOutputDTO> listOutDTO);
}
