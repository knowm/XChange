package org.knowm.xchange.coinone.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinone.dto.marketdata.CoinoneOrderBook;
import org.knowm.xchange.coinone.dto.marketdata.CoinoneTicker;
import org.knowm.xchange.coinone.dto.marketdata.CoinoneTrades;
import org.knowm.xchange.currency.CurrencyPair;

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
public class CoinoneMarketDataServiceRaw extends CoinoneBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinoneMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public CoinoneTicker getTicker(CurrencyPair currencyPair) throws IOException {
    return coinone.getTicker(currencyPair.base.getSymbol());
  }

  public CoinoneOrderBook getCoinoneOrderBook(CurrencyPair currencyPair) throws IOException {
    return coinone.getOrderBook(currencyPair.base.getSymbol());
  }

  public CoinoneTrades getTrades(CurrencyPair currencyPair, String period) throws IOException {
    return coinone.getTrades(currencyPair.base.getSymbol(), period);
  }
}
