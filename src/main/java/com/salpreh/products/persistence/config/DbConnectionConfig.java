package com.salpreh.products.persistence.config;

import com.salpreh.products.config.ConfigLoader.Config;
import io.micronaut.context.annotation.Factory;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlClient;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;

@Factory
@Singleton
public class DbConnectionConfig {

  private SqlClient client;

  @Singleton
  public SqlClient client(Vertx vertx, Config config) {
    if (client != null) return client;

    PgConnectOptions connectOptions = new PgConnectOptions()
      .setPort(config.getProperty("database.connection.port", Integer.class))
      .setHost(config.getProperty("database.connection.host", String.class))
      .setDatabase(config.getProperty("database.connection.database", String.class))
      .setUser(config.getProperty("database.connection.user", String.class))
      .setPassword(config.getProperty("database.connection.password", String.class));

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
