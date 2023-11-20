package org.knowm.xchange.client;

import lombok.Data;
import si.mazi.rescu.IRestProxyFactory;
import si.mazi.rescu.RestProxyFactoryImpl;

@Data
public class ProxyConfig {

  private Class<? extends IRestProxyFactory> restProxyFactoryClass = RestProxyFactoryImpl.class;

  private static ProxyConfig instance = new ProxyConfig();

  public static ProxyConfig getInstance() {
    return instance;
  }
}
