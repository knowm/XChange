package org.knowm.xchange.coinsetter.service.polling;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsetter.rs.CoinsetterPing;
import org.knowm.xchange.service.BaseExchangeService;

import si.mazi.rescu.RestProxyFactory;

/**
 * Ping service.
 */
public class CoinsetterPingServiceRaw extends BaseExchangeService {

  protected final CoinsetterPing ping;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinsetterPingServiceRaw(Exchange exchange) {

    super(exchange);

    String baseUrl = exchange.getExchangeSpecification().getSslUri();
    ping = RestProxyFactory.createProxy(CoinsetterPing.class, baseUrl);
  }

  public String ping(String text) throws IOException {

    return ping.ping(text);
  }

}
