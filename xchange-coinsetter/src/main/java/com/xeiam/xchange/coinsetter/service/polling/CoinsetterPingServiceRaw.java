package com.xeiam.xchange.coinsetter.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinsetter.rs.CoinsetterPing;

/**
 * Ping service.
 */
public class CoinsetterPingServiceRaw extends CoinsetterBasePollingService {

  protected final CoinsetterPing ping;

  /**
   * @param exchangeSpecification
   */
  public CoinsetterPingServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    String baseUrl = exchangeSpecification.getSslUri();
    ping = RestProxyFactory.createProxy(CoinsetterPing.class, baseUrl);
  }

  public String ping(String text) throws IOException {

    return ping.ping(text);
  }

}
