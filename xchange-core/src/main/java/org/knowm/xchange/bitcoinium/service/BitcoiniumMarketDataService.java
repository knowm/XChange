package org.knowm.xchange.bitcoinium.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoinium.BitcoiniumAdapters;
import org.knowm.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import org.knowm.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * <p>
 * Implementation of the generic market data service for Bitcoinium
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BitcoiniumMarketDataService extends BitcoiniumMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcoiniumMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    // Request data
    BitcoiniumTicker bitcoiniumTicker = getBitcoiniumTicker(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    // Adapt to XChange DTOs
    return BitcoiniumAdapters.adaptTicker(bitcoiniumTicker, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    String priceWindow = "";

    if (args != null && args.length == 1) {
      Object arg0 = args[0];
      if (!(arg0 instanceof String)) {
        throw new ExchangeException("priceWindow argument must be a String!");
      } else {
        priceWindow = (String) arg0;
      }
    } else {
      throw new ExchangeException("Exactly 1 String arguments is necessary: the priceWindow!");
    }

    // Request data
    BitcoiniumOrderbook bitcoiniumOrderbook = getBitcoiniumOrderbook(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode(),
        priceWindow);

    // Adapt to XChange DTOs
    return BitcoiniumAdapters.adaptOrderbook(bitcoiniumOrderbook, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
