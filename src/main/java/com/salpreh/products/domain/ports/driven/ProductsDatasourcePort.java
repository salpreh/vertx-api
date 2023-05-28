package com.salpreh.products.domain.ports.driven;

import com.salpreh.products.domain.models.Product;
import io.vertx.core.Future;
import java.util.List;

public interface ProductsDatasourcePort {

  Future<Product> findById(long id);
  Future<List<Product>> findAll();
  Future<Product> create(Product product);
  Future<Product> update(long id, Product product);
  Future<Void> delete(long id);
}
