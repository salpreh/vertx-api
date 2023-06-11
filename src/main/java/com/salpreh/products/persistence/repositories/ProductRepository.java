package com.salpreh.products.persistence.repositories;

import com.salpreh.products.domain.models.Product;
import com.salpreh.products.domain.ports.driven.ProductsDatasourcePort;
import com.salpreh.products.persistence.entities.ProductEntity;
import com.salpreh.products.persistence.mappers.DbMapper;
import io.vertx.core.Future;
import jakarta.inject.Singleton;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.reactive.stage.Stage.SessionFactory;

@Singleton
@RequiredArgsConstructor
public class ProductRepository implements ProductsDatasourcePort {

  private final DbMapper mapper;
  private final SessionFactory emf;

  @Override
  public Future<Product> findById(long id) {
    return Future.fromCompletionStage(
      emf.withSession(session -> session.find(ProductEntity.class, id))
    )
      .map(mapper::map)
      .andThen(result -> {
        if (result.result() == null) {
          throw new EntityNotFoundException("Product not found");
        }
      });
  }

  @Override
  public Future<List<Product>> findAll() {
    return Future.fromCompletionStage(
      emf.withSession(session -> session.createQuery("SELECT p FROM ProductEntity p", ProductEntity.class)
          .getResultList())
    )
      .map(mapper::mapProducts);
  }

  @Override
  public Future<Product> create(Product product) {
    ProductEntity productData = mapper.map(product);

    return Future.fromCompletionStage(
      emf.withSession(session -> session.persist(productData)
        .thenAccept(__ -> session.flush()))
    )
      .onFailure(Throwable::printStackTrace)
      .map(__ -> mapper.map(productData));
  }

  @Override
  public Future<Product> update(long id, Product product) {
    return Future.fromCompletionStage(
      emf.withSession(session -> session.find(ProductEntity.class, id)
        .thenApply(pd -> {
          if (pd == null) throw new EntityNotFoundException("Product not found");
          else return pd;
        })
        .thenApply(pd -> mapper.map(product, pd))
        .thenCompose(session::merge)
        .thenCompose(pd -> session.flush().thenApply(__ -> pd))
      )
    ).map(mapper::map);
  }

  @Override
  public Future<Void> delete(long id) {
    return Future.fromCompletionStage(
      emf.withSession(session -> session.find(ProductEntity.class, id)
        .thenApply(pd -> {
          if (pd == null) throw new EntityNotFoundException("Product not found");
          else return pd;
        })
        .thenCompose(session::remove)
        .thenCompose(__ -> session.flush())
        .handle((__, t) -> null) // If entity do not exists we take it as a successfully deletion
      )
    ).mapEmpty();
  }
}
