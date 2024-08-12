package org.knowm.xchange.gateio.config;

import java.time.Clock;
import lombok.Data;
import si.mazi.rescu.IRestProxyFactory;
import si.mazi.rescu.RestProxyFactoryImpl;

@Data
public final class Config {

  private Class<? extends IRestProxyFactory> restProxyFactoryClass = RestProxyFactoryImpl.class;

  private Clock clock;

  private static Config instance = new Config();

  private Config() {
    clock = Clock.systemDefaultZone();
  }

  public static Config getInstance() {
    return instance;
  }

}
