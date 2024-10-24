package com.product.UseCase;

import java.util.List;

import com.product.Entity.Product;

public interface GetProductListDatabaseBoundary {
    List<Product> getAllProductList();
}
