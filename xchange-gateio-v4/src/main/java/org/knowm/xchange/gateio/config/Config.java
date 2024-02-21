package org.knowm.xchange.gateio.config;

import lombok.Data;
import si.mazi.rescu.IRestProxyFactory;
import si.mazi.rescu.RestProxyFactoryImpl;

@Data
public class Config {

  private Class<? extends IRestProxyFactory> restProxyFactoryClass = RestProxyFactoryImpl.class;

  private static Config instance = new Config();

  public static Config getInstance() {
    return instance;
  }

}
