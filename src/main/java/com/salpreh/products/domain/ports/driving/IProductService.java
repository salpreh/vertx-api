package com.salpreh.products.domain.ports.driving;

import com.salpreh.products.domain.models.Product;
import io.vertx.core.Future;
import java.util.List;

public interface IProductService {
  Future<List<Product>> getAll();
  Future<Product> getById(long id);
  Future<Product> create(Product product);
  Future<Product> update(long id, Product product);
  Future<Void> delete(Long id);
}
