package com.salpreh.products.api.handlers;

import com.salpreh.products.domain.models.Product;
import com.salpreh.products.domain.services.ProductService;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class ProductHandler {

  private final ProductService productService;

  public void getAll(RoutingContext routingContext) {
    productService.getAll()
      .onComplete(res -> {
        if (res.succeeded()) {
          routingContext.response()
            .putHeader("content-type", "application/json")
            .end(Json.encode(res.result()));
        } else {
          routingContext.response()
            .setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
            .end();
        }
      });
  }

  public void getOne(RoutingContext routingContext) {
    long id = Long.parseLong(routingContext.pathParam("id"));

    productService.getById(id)
      .onComplete(res -> {
        if (res.succeeded()) {
          routingContext.response()
            .putHeader("content-type", "application/json")
            .end(Json.encode(res.result()));
        } else {
          routingContext.response()
            .setStatusCode(HttpResponseStatus.NOT_FOUND.code())
            .end();
        }
      });
  }

  public void create(RoutingContext routingContext) {
    Product product = routingContext.body().asPojo(Product.class);

    productService.create(product)
        .onComplete(res -> {
          if (res.succeeded()) {
            routingContext.response()
              .putHeader("content-type", "application/json")
              .setStatusCode(HttpResponseStatus.CREATED.code())
              .end(Json.encode(res.result()));
          } else {
            routingContext.response()
              .setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
              .end();
          }
        });
  }

  public void update(RoutingContext routingContext) {
    long id = Long.parseLong(routingContext.pathParam("id"));
    Product product = routingContext.body().asPojo(Product.class);

    productService.update(id, product)
      .onComplete(res -> {
        if (res.succeeded()) {
          routingContext
            .response()
            .putHeader("content-type", "application/json")
            .end(Json.encode(res.result()));
        } else {
          routingContext.response()
            .setStatusCode(HttpResponseStatus.NOT_FOUND.code())
            .end();
        }
      });

  }

  public void delete(RoutingContext routingContext) {
    long id = Long.parseLong(routingContext.pathParam("id"));
    productService.delete(id)
      .onComplete(res -> {
        if (res.succeeded()) {
          routingContext.response()
            .putHeader("content-type", "application/json")
            .setStatusCode(HttpResponseStatus.NO_CONTENT.code())
            .end();
        } else {
          routingContext.response()
            .setStatusCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code())
            .end();
        }
      });
  }
}
