package com.salpreh.products.persistence.config;

import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlClient;
import jakarta.annotation.PreDestroy;

public class DbConnectionConfig {

  private SqlClient client;

  public SqlClient init(Vertx vertx) {
    if (client != null) return client;

    PgConnectOptions connectOptions = new PgConnectOptions()
      .setPort(5432)
      .setHost("localhost")
      .setDatabase("products")
      .setUser("root")
      .setPassword("password");

    PoolOptions poolOptions = new PoolOptions()
      .setMaxSize(4);

    // Create the client pool
    client = Pool.pool(vertx, connectOptions, poolOptions);

    return client;
  }

  @PreDestroy
  public void close() {
    if (client != null) {
      client.close();
    }
  }
}
