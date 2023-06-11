package com.salpreh.products.config;

import io.micronaut.context.annotation.Factory;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.pointer.JsonPointer;
import jakarta.inject.Singleton;

@Factory
public class ConfigLoader {

  @Singleton
  public ConfigRetriever configRetriever(Vertx vertx) {
    ConfigStoreOptions store = new ConfigStoreOptions()
      .setType("file")
      .setFormat("yaml")
      .setConfig(new JsonObject()
        .put("path", "application.yaml")
      );

    ConfigRetrieverOptions retrieverOptions = new ConfigRetrieverOptions()
      .addStore(store);

    ConfigRetriever configRetriever = ConfigRetriever.create(vertx, retrieverOptions);

    return configRetriever;
  }

  @Singleton
  public ConfigStore configStore(ConfigRetriever configRetriever) {
    return ConfigStore.of(configRetriever);
  }

  public static class ConfigStore {
    private final ConfigRetriever configRetriever;

    public ConfigStore(ConfigRetriever configRetriever) {
      this.configRetriever = configRetriever;
    }

    public static ConfigStore of(ConfigRetriever configRetriever) {
      return new ConfigStore(configRetriever);
    }

    public Future<Config> getConfig() {
      return configRetriever.getConfig()
        .map(Config::new);
    }
  }

  public static class Config {
    public final JsonObject config;
    public Config(JsonObject config) {
      this.config = config;
    }

    public <T> T getProperty(String property, Class<T> type) {
      JsonPointer pointer = JsonPointer.from("/" + property.replace(".", "/"));
      return type.cast(pointer.queryJson(config));
    }
  }

}
