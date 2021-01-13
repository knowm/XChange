package org.knowm.xchange.upbit.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.upbit.dto.marketdata.UpbitOrderBooks;
import org.knowm.xchange.upbit.dto.marketdata.UpbitTickers;
import org.knowm.xchange.upbit.dto.marketdata.UpbitTrades;

/**
 * Implementation of the market data service for Korbit
 *
 * <p>
 *
 * <p>
 *
 * <p>
 *
 * <ul>
 *   <li>Provides access to various market data values
 * </ul>
 */
public class UpbitMarketDataServiceRaw extends UpbitBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public UpbitMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public UpbitTickers getTicker(CurrencyPair currencyPair) throws IOException {
    return upbit.getTicker(
        currencyPair.counter.getCurrencyCode() + "-" + currencyPair.base.getCurrencyCode());
  }

  public UpbitOrderBooks getUpbitOrderBook(CurrencyPair currencyPair) throws IOException {
    return upbit.getOrderBook(
        currencyPair.counter.getCurrencyCode() + "-" + currencyPair.base.getCurrencyCode());
  }

  public UpbitTrades getTrades(CurrencyPair currencyPair) throws IOException {
    return upbit.getTrades(
        currencyPair.counter.getCurrencyCode() + "-" + currencyPair.base.getCurrencyCode(), 100);
  }
}
