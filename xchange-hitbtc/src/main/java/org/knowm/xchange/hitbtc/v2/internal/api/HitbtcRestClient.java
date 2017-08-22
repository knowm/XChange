package org.knowm.xchange.hitbtc.v2.internal.api;

import org.knowm.xchange.Exchange;

import si.mazi.rescu.RestProxyFactory;

public enum HitbtcRestClient {

  INSTANCE;

  protected HitbtcApi hitbtc;

  private HitbtcRestClient() { }

  public void init(Exchange exchange) {
    hitbtc = RestProxyFactory.createProxy(HitbtcApi.class, exchange.getExchangeSpecification().getSslUri());
  }

  public HitbtcApi call() {
    return hitbtc;
  }

}
