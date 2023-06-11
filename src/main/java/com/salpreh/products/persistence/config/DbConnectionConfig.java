package com.salpreh.products.persistence.config;

import io.micronaut.context.annotation.Factory;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import javax.persistence.Persistence;
import org.hibernate.reactive.stage.Stage;
import org.hibernate.reactive.stage.Stage.SessionFactory;

@Factory
@Singleton
public class DbConnectionConfig {

  private SessionFactory emf;

  @Singleton
  public SessionFactory entityManagerFactory() {
    if (emf != null) return emf;

    emf = Persistence.createEntityManagerFactory("products")
      .unwrap(Stage.SessionFactory.class);

    return emf;
  }

  @PreDestroy
  public void close() {
    if (emf != null) {
      emf.close();
    }
  }
}
