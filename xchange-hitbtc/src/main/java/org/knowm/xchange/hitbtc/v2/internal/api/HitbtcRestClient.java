package org.knowm.xchange.hitbtc.v2.internal.api;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.hitbtc.v2.Hitbtc;
import org.knowm.xchange.hitbtc.v2.HitbtcAuthenticated;

import si.mazi.rescu.RestProxyFactory;

public enum HitbtcRestClient {

  INSTANCE;

  protected HitbtcAuthenticated hitbtc;

  HitbtcRestClient() { }

  public void init(Exchange exchange) {
    hitbtc = RestProxyFactory.createProxy(HitbtcAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
  }

  public HitbtcAuthenticated call() {
    return hitbtc;
  }

}
