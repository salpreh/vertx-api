package com.salpreh.products.api;

import com.salpreh.products.api.handlers.ProductHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class RouterConfig {

  private final ProductHandler productHandler;

  public Router config(Vertx vertx) {
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());

    router.get("/product").handler(productHandler::getAll);
    router.get("/product/:id").handler(productHandler::getOne);
    router.post("/product").handler(productHandler::create);
    router.put("/product/:id").handler(productHandler::update);
    router.delete("/product/:id").handler(productHandler::delete);

    return router;
  }
}
