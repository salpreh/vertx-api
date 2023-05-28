package com.salpreh.products.persistence.config;

import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlClient;

public class DbConnectionConfig {

  private static SqlClient CLIENT = null;

  public static void  init(Vertx vertx) {
    PgConnectOptions connectOptions = new PgConnectOptions()
      .setPort(5432)
      .setHost("localhost")
      .setDatabase("products")
      .setUser("root")
      .setPassword("password");

    PoolOptions poolOptions = new PoolOptions()
      .setMaxSize(4);

    // Create the client pool
    CLIENT = Pool.pool(vertx, connectOptions, poolOptions);
  }

  public static SqlClient client() {
    return CLIENT;
  }

  public static void close() {
    if (CLIENT != null) {
      CLIENT.close();
    }
  }
}
