package org.knowm.xchange.mexbt.service.polling;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.mexbt.MeXBT;
import org.knowm.xchange.mexbt.dto.marketdata.MeXBTOrderBook;
import org.knowm.xchange.mexbt.dto.marketdata.MeXBTTicker;
import org.knowm.xchange.mexbt.dto.marketdata.MeXBTTrade;

import si.mazi.rescu.RestProxyFactory;

public class MeXBTMarketDataServiceRaw extends MeXBTBasePollingService {

  private final MeXBT meXBT;

  protected MeXBTMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    this.meXBT = RestProxyFactory.createProxy(MeXBT.class, exchange.getExchangeSpecification().getSslUri());
  }

  public MeXBTOrderBook getOrderBook(String currencyPair) throws IOException {
    return meXBT.getOrderBook(currencyPair);
  }

  public MeXBTTrade[] getTrades(String currencyPair, Long since) throws IOException {
    return meXBT.getTrades(currencyPair, since);
  }

  public MeXBTTicker getTicker(String currencyPair) throws IOException {
    return meXBT.getTicker(currencyPair);
  }

}
