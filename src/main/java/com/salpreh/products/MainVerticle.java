package com.salpreh.products;

import com.salpreh.products.api.RouterConfig;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class MainVerticle extends AbstractVerticle {

  private final RouterConfig routerConfig = new RouterConfig();

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.createHttpServer()
      .requestHandler(routerConfig.config(vertx))
      .listen(8080, http -> {
        if (http.succeeded()) {
          startPromise.complete();
          System.out.println("HTTP server started on port 8888");
        } else {
          startPromise.fail(http.cause());
        }
      });
  }
}
