package com.product.UseCase;

import java.util.ArrayList;
import java.util.List;

import com.product.Entity.Product;

public class GetProductListUseCase implements GetProductListInputBoundary {
    private GetProductListOutputBoundary getSLOutputBoundary = null;
    private GetProductListDatabaseBoundary getSLDBBoundary = null;

    public GetProductListUseCase(GetProductListOutputBoundary getSLOutputBoundary, GetProductListDatabaseBoundary getSLDBBoundary) {
        this.getSLOutputBoundary = getSLOutputBoundary;
        this.getSLDBBoundary = getSLDBBoundary;
    }

    @Override
    public void excute() {
        //lay danh sach hang hoa
        List<Product> listProduct = getSLDBBoundary.getAllProductList();
        List<GetProductListOutputDTO> listOutDTO = new ArrayList<>();

        for(Product product : listProduct){
            GetProductListOutputDTO productDTO = new GetProductListOutputDTO(product.getMaHang(), product.getTenHang(), product.getSoLuong(), (int) product.getDonGia(), product.getLoaiHang(), product.getTinhTongSoLuong(), product.getTinhVAT());
            listOutDTO.add(productDTO);

        }
        getSLOutputBoundary.present(listOutDTO);
    }

    


}
