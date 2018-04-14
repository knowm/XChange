package org.knowm.xchange.coinone.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinone.dto.marketdata.CoinoneOrderBook;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * Implementation of the market data service for Korbit
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

  public CoinoneOrderBook getCoinoneOrderBook(CurrencyPair currencyPair) throws IOException {

    try {
      return coinone.getOrderBook(currencyPair.base.toString().toLowerCase());
    } catch (Exception e) {
      e.printStackTrace();
      throw new ExchangeException(e);
    }
  }
}
