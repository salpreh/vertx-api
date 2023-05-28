package com.salpreh.products.persistence.repositories;

import com.salpreh.products.domain.models.Product;
import com.salpreh.products.persistence.entities.ProductEntity;
import com.salpreh.products.persistence.mappers.DbMapper;
import io.vertx.core.Future;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.templates.SqlTemplate;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class ProductRepository {

  private final DbMapper mapper;
  private final SqlClient client;

  public Future<Product> findById(long id) {
    return SqlTemplate.forQuery(client, "select * from product where id = #{id}")
      .mapTo(ProductEntity.class)
      .execute(Map.of("id", id))
      .map(rows -> rows.iterator().next())
      .map(mapper::map);
  }

  public Future<List<Product>> findAll() {
    return SqlTemplate.forQuery(client, "select * from product")
      .mapTo(ProductEntity.class)
      .execute(null)
      .map(rows -> StreamSupport.stream(rows.spliterator(), false)
        .map(mapper::map)
        .toList()
      );
  }

  public Future<Product> create(Product product) {
    return SqlTemplate.forQuery(client, "insert into product (name, description, price, stock) values (#{name}, #{description}, #{price}, #{stock}) returning *")
      .mapFrom(ProductEntity.class)
      .mapTo(ProductEntity.class)
      .execute(mapper.map(product))
      .map(v -> v.iterator().next())
      .map(mapper::map);
  }

  public Future<Product> update(long id, Product product) {
    product.setId(id);

    return SqlTemplate.forQuery(client, "update product set name = #{name}, description = #{description}, price = #{price}, stock = #{stock} where id = #{id} returning *")
      .mapFrom(ProductEntity.class)
      .mapTo(ProductEntity.class)
      .execute(mapper.map(product))
      .map(v -> v.iterator().next())
      .map(mapper::map);
  }

  public Future<Void> delete(long id) {
    return SqlTemplate.forQuery(client, "delete from product where id = #{id}")
      .execute(Map.of("id", id))
      .mapEmpty();
  }
}
