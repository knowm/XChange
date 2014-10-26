package com.xeiam.xchange.coinsetter.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinsetter.rs.CoinsetterPing;
import com.xeiam.xchange.service.BaseExchangeService;

/**
 * Ping service.
 */
public class CoinsetterPingServiceRaw extends BaseExchangeService {

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
