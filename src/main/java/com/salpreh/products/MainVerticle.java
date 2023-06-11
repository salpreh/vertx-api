package com.salpreh.products;

import com.salpreh.products.api.RouterConfig;
import com.salpreh.products.config.ConfigLoader;
import com.salpreh.products.config.ConfigLoader.Config;
import io.micronaut.context.BeanContext;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class MainVerticle extends AbstractVerticle {

  private BeanContext beanContext;
  private RouterConfig routerConfig;
  private Future<Config> config;

  @Override
  public void init(Vertx vertx, Context context) {
    super.init(vertx, context);

    beanContext = BeanContext.build();
    beanContext.registerSingleton(vertx);
    config = ConfigLoader.configStore(vertx)
      .getConfig()
      .andThen(config -> beanContext.registerSingleton(config.result()))
      .andThen(__ -> {
        beanContext.start();
        routerConfig = beanContext.getBean(RouterConfig.class);
      })
      .onFailure(Throwable::printStackTrace);
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    config.onComplete(result -> {
      Config config = result.result();

      vertx.createHttpServer()
        .requestHandler(routerConfig.config(vertx))
        .listen(config.getProperty("server.port", Integer.class))
        .onComplete(res -> {
          if (res.succeeded()) {
            startPromise.complete();
            System.out.println("HTTP server started");
          } else {
            startPromise.fail(res.cause());
          }
        });
    });
  }

  @Override
  public void stop() throws Exception {
    beanContext.close();
  }
}
