package org.knowm.xchange.chbtc.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.chbtc.Chbtc;
import org.knowm.xchange.chbtc.dto.marketdata.ChbtcOrderBook;
import org.knowm.xchange.chbtc.dto.marketdata.ChbtcTicker;
import org.knowm.xchange.chbtc.dto.marketdata.ChbtcTickerResponse;
import org.knowm.xchange.chbtc.dto.marketdata.ChbtcTrade;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.RestProxyFactory;

public class ChbtcMarketDataServiceRaw extends BaseExchangeService implements BaseService {

  private final Chbtc chbtc;

  public ChbtcMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    this.chbtc = RestProxyFactory.createProxy(Chbtc.class, exchange.getExchangeSpecification().getSslUri());
  }

  public ChbtcOrderBook getChbtcOrderBook(CurrencyPair pair) throws IOException {
    checkCounter(pair);
    return isBtc(pair) ? chbtc.getOrderBookBtc() : chbtc.getOrderBook(pair.base.getCurrencyCode());
  }

  public ChbtcTrade[] getChbtcTransactions(CurrencyPair pair, Integer sinceTid) throws IOException {
    checkCounter(pair);
    return isBtc(pair) ? chbtc.getTransactionsBtc(sinceTid) : chbtc.getTransactions(pair.base.getCurrencyCode(), sinceTid);
  }

  public ChbtcTicker getChbtcTicker(CurrencyPair pair) throws IOException {
    checkCounter(pair);
    ChbtcTickerResponse resp = isBtc(pair) ? chbtc.getTickerBtc() : chbtc.getTicker(pair.base.getCurrencyCode());
    return resp.getTicker();
  }

  private void checkCounter(CurrencyPair pair) {
    if (!Currency.CNY.equals(pair.counter)) {
      throw new IllegalArgumentException("Unsupported counter symbol " + pair.counter);
    }
  }

  private boolean isBtc(CurrencyPair pair) {
    return Currency.BTC.equals(pair.base);
  }
}
