package product.UI;

import java.util.ArrayList;
import java.util.List;

import product.Database.GetProductListDAOMySQL;
import product.UseCase.DataExport;
import product.UseCase.GetProductListOutputBoundary;
import product.UseCase.GetProductListOutputDTO;
import product.UseCase.ResponseData;

public class GetProductListPresenter implements GetProductListOutputBoundary{
    private DataExport dataExport= null;
    private List<GetProductListOutputDTO> listOutputDTO = null;
    private List<GetProductListViewModel> listViewModels =null;
     private GetProductListDAOMySQL getProductListDAOMySQL;
    
     public GetProductListPresenter(GetProductListDAOMySQL getProductListDAOMySQL) {
        this.getProductListDAOMySQL = getProductListDAOMySQL;
    }
    @Override
    public void exportResult(ResponseData responseData){
        this.dataExport = (DataExport)responseData;
    }

    public DataExport getDataExport() {
        return dataExport;
    }

    @Override
    public void present (List<GetProductListOutputDTO> listOutputDTO){
        this.listOutputDTO = listOutputDTO;
        listViewModels = new ArrayList<>();
        for (GetProductListOutputDTO productDTO : listOutputDTO){
            
            // String tinhVAT = String.format("%.1f", productDTO.getTinhVAT());
            // String donGia = String.format("%.1f", productDTO.getDonGia());
            GetProductListViewModel viewModel = new GetProductListViewModel(productDTO.getMaHang(),productDTO.getTenHang(), productDTO.getSoLuong(),productDTO.getDonGia(), productDTO.getLoaiHang(), productDTO.getTinhVAT());

            listViewModels.add(viewModel);
        }

        GetProductListView form = new GetProductListView(getProductListDAOMySQL);
        form.createAndshowGUI(listViewModels);
    }
    
    public List<GetProductListOutputDTO> getListOutputDTO(){
        return listOutputDTO;
    }
}
