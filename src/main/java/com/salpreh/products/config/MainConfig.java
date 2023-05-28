package com.salpreh.products.config;

import com.salpreh.products.persistence.config.DbConnectionConfig;
import io.micronaut.context.BeanContext;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.SqlClient;

public class MainConfig {
  private final Vertx vertx;
  private final BeanContext beanContext;

  public MainConfig(Vertx vertx) {
    this.vertx = vertx;
    this.beanContext = BeanContext.run();
  }

  public static MainConfig create(Vertx vertx) {
    return new MainConfig(vertx);
  }

  public BeanContext init() {
    dbConnection();

    return beanContext;
  }

  private void dbConnection() {
    DbConnectionConfig dbConnectionConfig = new DbConnectionConfig();
    SqlClient client = dbConnectionConfig.init(vertx);

    beanContext.registerSingleton(dbConnectionConfig);
    beanContext.registerSingleton(client);
  }

}
