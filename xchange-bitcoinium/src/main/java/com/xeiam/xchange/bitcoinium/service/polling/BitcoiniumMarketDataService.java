package com.xeiam.xchange.bitcoinium.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcoinium.BitcoiniumAdapters;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * <p>
 * Implementation of the generic market data service for Bitcoinium
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BitcoiniumMarketDataService extends BitcoiniumMarketDataServiceRaw implements PollingMarketDataService {

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

    if (args.length == 1) {
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
    BitcoiniumOrderbook bitcoiniumOrderbook = getBitcoiniumOrderbook(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode(), priceWindow);

    // Adapt to XChange DTOs
    return BitcoiniumAdapters.adaptOrderbook(bitcoiniumOrderbook, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
