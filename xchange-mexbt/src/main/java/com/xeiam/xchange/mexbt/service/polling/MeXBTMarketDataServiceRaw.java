package com.xeiam.xchange.mexbt.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.mexbt.MeXBT;
import com.xeiam.xchange.mexbt.dto.marketdata.MeXBTOrderBook;
import com.xeiam.xchange.mexbt.dto.marketdata.MeXBTTicker;
import com.xeiam.xchange.mexbt.dto.marketdata.MeXBTTrade;

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
