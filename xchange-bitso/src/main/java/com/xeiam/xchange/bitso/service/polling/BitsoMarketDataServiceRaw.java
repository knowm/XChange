package com.xeiam.xchange.bitso.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitso.Bitso;
import com.xeiam.xchange.bitso.dto.marketdata.BitsoOrderBook;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;

/**
 * @author Piotr Ładyżyński
 */
public class BitsoMarketDataServiceRaw  extends BitsoBasePollingService {

  private final Bitso bitso;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitsoMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.bitso = RestProxyFactory.createProxy(Bitso.class, exchange.getExchangeSpecification().getSslUri());
  }


  public BitsoOrderBook getBitsoOrderBook() throws IOException {

    return bitso.getOrderBook();
  }
}
