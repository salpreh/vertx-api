package com.salpreh.products.api.handlers;

import com.salpreh.products.domain.models.Product;
import com.salpreh.products.domain.services.ProductService;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

public class ProductHandler {

  private final ProductService productService = new ProductService();

  public void getAll(RoutingContext routingContext) {
    routingContext.response()
      .putHeader("content-type", "application/json")
      .end(Json.encode(productService.getAll()));
  }

  public void getOne(RoutingContext routingContext) {
    long id = Long.parseLong(routingContext.pathParam("id"));

    routingContext.response()
      .putHeader("content-type", "application/json")
      .end(Json.encode(productService.getById(id)));
  }

  public void create(RoutingContext routingContext) {
    Product product = routingContext.body().asPojo(Product.class);

    routingContext.response()
      .putHeader("content-type", "application/json")
      .setStatusCode(HttpResponseStatus.CREATED.code())
      .end(Json.encode(productService.create(product)));
  }

  public void update(RoutingContext routingContext) {
    long id = Long.parseLong(routingContext.pathParam("id"));
    Product product = routingContext.body().asPojo(Product.class);

    routingContext.response()
      .putHeader("content-type", "application/json")
      .end(Json.encode(productService.update(id, product)));
  }

  public void delete(RoutingContext routingContext) {
    long id = Long.parseLong(routingContext.pathParam("id"));
    productService.delete(id);

    routingContext.response()
      .setStatusCode(HttpResponseStatus.NO_CONTENT.code())
      .end();
  }
}
