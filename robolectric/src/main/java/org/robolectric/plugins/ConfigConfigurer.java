package org.robolectric.plugins;

import java.lang.reflect.Method;
import java.util.Properties;
import javax.annotation.Nonnull;
import org.robolectric.annotation.Config;
import org.robolectric.pluginapi.ConfigurationStrategy.ConfigCollection;
import org.robolectric.pluginapi.Configurer;

public class ConfigConfigurer implements Configurer<Config> {

  public static Config get(ConfigCollection testConfig) {
    return testConfig.get(Config.class);
  }

  @Override
  public Class<Config> getConfigClass() {
    return Config.class;
  }

  @Nonnull
  @Override
  public Config defaultConfig() {
    return Config.Builder.defaults().build();
  }

  @Override
  public Config getConfigFor(@Nonnull Properties packageProperties) {
    return Config.Implementation.fromProperties(packageProperties);
  }

  @Override
  public Config getConfigFor(@Nonnull Class<?> testClass) {
    return testClass.getAnnotation(Config.class);
  }

  @Override
  public Config getConfigFor(@Nonnull Method method) {
    return method.getAnnotation(Config.class);
  }

  @Nonnull
  @Override
  public Config merge(@Nonnull Config parentConfig, @Nonnull Config childConfig) {
    return new Config.Builder(parentConfig).overlay(childConfig).build();
  }

}