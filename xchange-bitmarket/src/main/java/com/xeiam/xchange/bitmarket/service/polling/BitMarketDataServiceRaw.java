package com.xeiam.xchange.bitmarket.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketOrderBook;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTicker;
import com.xeiam.xchange.bitmarket.dto.marketdata.BitMarketTrade;
import com.xeiam.xchange.currency.CurrencyPair;

/**
 * @author kpysniak
 */
public class BitMarketDataServiceRaw extends BitMarketBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BitMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BitMarketTicker getBitMarketTicker(CurrencyPair currencyPair) throws IOException {

    return bitMarket.getTicker(currencyPair.base.getCurrencyCode().toUpperCase() + currencyPair.counter.getCurrencyCode().toString());
  }

  public BitMarketOrderBook getBitMarketOrderBook(CurrencyPair currencyPair) throws IOException {

    return bitMarket.getOrderBook(currencyPair.base.getCurrencyCode().toUpperCase() + currencyPair.counter.getCurrencyCode().toString());
  }

  public BitMarketTrade[] getBitMarketTrades(CurrencyPair currencyPair) throws IOException {

    return bitMarket.getTrades(currencyPair.base.getCurrencyCode().toUpperCase() + currencyPair.counter.getCurrencyCode().toString());
  }

}
