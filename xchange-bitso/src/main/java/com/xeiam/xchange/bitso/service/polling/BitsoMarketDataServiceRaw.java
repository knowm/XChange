package com.xeiam.xchange.bitso.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitso.Bitso;
import com.xeiam.xchange.bitso.dto.marketdata.BitsoOrderBook;
import com.xeiam.xchange.bitso.dto.marketdata.BitsoTicker;

/**
 * @author Piotr Ładyżyński
 */
public class BitsoMarketDataServiceRaw  extends BitsoBasePollingService {

  private final Bitso bitso;

  public BitsoMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    this.bitso = RestProxyFactory.createProxy(Bitso.class, exchange.getExchangeSpecification().getSslUri());
  }

  public BitsoOrderBook getBitsoOrderBook() throws IOException {
    return bitso.getOrderBook();
  }

  public BitsoTicker getBitsoTicker() throws IOException {
    return bitso.getTicker();
  }
}
