package com.salpreh.products;

import com.salpreh.products.api.RouterConfig;
import com.salpreh.products.config.MainConfig;
import io.micronaut.context.BeanContext;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class MainVerticle extends AbstractVerticle {

  private BeanContext beanContext;
  private RouterConfig routerConfig;

  @Override
  public void init(Vertx vertx, Context context) {
    super.init(vertx, context);

    beanContext = MainConfig.create(vertx).init();

    routerConfig = beanContext.getBean(RouterConfig.class);
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.createHttpServer()
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
  }

  @Override
  public void stop() throws Exception {
    beanContext.close();
  }
}
