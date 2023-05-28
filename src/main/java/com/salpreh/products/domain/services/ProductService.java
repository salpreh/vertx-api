package com.salpreh.products.domain.services;

import com.salpreh.products.domain.models.Product;
import com.salpreh.products.persistence.repositories.ProductRepository;
import io.vertx.core.Future;
import java.util.List;

public class ProductService {

  private final ProductRepository productRepository = new ProductRepository();

  public Future<List<Product>> getAll() {
    return productRepository.findAll();
  }

  public Future<Product> getById(long id) {
    return productRepository.findById(id);
  }

  public Future<Product> create(Product product) {
    return productRepository.create(product);
  }

  public Future<Product> update(long id, Product product) {
    return productRepository.update(id, product);
  }

  public Future<Void> delete(Long id) {
    return productRepository.delete(id);
  }
}
