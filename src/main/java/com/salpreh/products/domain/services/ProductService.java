package com.salpreh.products.domain.services;

import com.salpreh.products.domain.models.Product;
import java.util.List;

public class ProductService {

  public List<Product> getAll() {
    return List.of(mock());
  }

  public Product getById(long id) {
    return mock();
  }

  public Product create(Product product) {
    return mock();
  }

  public Product update(long id, Product product) {
    return mock();
  }

  public void delete(Long id) {
    // Do nothing
  }

  private Product mock() {
    return Product.builder()
      .id(1L)
      .name("Orxata")
      .description("Orxata de xufa")
      .price(10.0)
      .stock(50)
      .build();
  }
}
