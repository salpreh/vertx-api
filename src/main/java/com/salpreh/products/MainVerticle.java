package com.salpreh.products;

import com.salpreh.products.api.RouterConfig;
import com.salpreh.products.config.ConfigLoader.Config;
import com.salpreh.products.config.ConfigLoader.ConfigStore;
import io.micronaut.context.BeanContext;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class MainVerticle extends AbstractVerticle {

  private BeanContext beanContext;
  private RouterConfig routerConfig;
  private ConfigStore configStore;

  @Override
  public void init(Vertx vertx, Context context) {
    super.init(vertx, context);

    beanContext = BeanContext.build();
    beanContext.registerSingleton(vertx);
    beanContext.start();

    routerConfig = beanContext.getBean(RouterConfig.class);
    configStore = beanContext.getBean(ConfigStore.class);
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    configStore.getConfig().onComplete(result -> {
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
