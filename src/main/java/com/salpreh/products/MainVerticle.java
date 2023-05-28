package com.salpreh.products;

import com.salpreh.products.api.RouterConfig;
import com.salpreh.products.persistence.config.DbConnectionConfig;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;

public class MainVerticle extends AbstractVerticle {

  private final RouterConfig routerConfig = new RouterConfig();

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Future<?> dbConfig = vertx
      .executeBlocking(promise -> {
        DbConnectionConfig.init(vertx);
        promise.complete();
      })
      .onComplete(res -> {
        if (res.succeeded()) {
          System.out.println("DB connection established");
        } else {
          System.out.println("DB connection failed"  + res.cause());
        }
      });

    Future<HttpServer> serverConfig = vertx.createHttpServer()
      .requestHandler(routerConfig.config(vertx))
      .listen(8080)
      .onComplete(res -> {
        if (res.succeeded()) {
          startPromise.complete();
          System.out.println("HTTP server started on port 8888");
        } else {
          startPromise.fail(res.cause());
        }
      });

    CompositeFuture.all(dbConfig, serverConfig).onComplete(res -> {
      if (res.succeeded()) {
        System.out.println("All services started");
      } else {
        System.out.println("Some services failed to start");
      }
    });
  }

  @Override
  public void stop() throws Exception {
    DbConnectionConfig.close();
  }
}
