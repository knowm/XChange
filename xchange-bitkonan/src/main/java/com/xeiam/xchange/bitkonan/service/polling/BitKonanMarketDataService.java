package com.xeiam.xchange.bitkonan.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitkonan.BitKonanAdapters;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author Piotr Ładyżyński
 */
public class BitKonanMarketDataService extends BitKonanMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitKonanMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    if (currencyPair.equals(CurrencyPair.BTC_USD)) {
      return BitKonanAdapters.adaptTicker(getBitKonanTickerBTC(), currencyPair);
    } else {
      throw new NotYetImplementedForExchangeException();
    }
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    if (currencyPair.equals(CurrencyPair.BTC_USD)) {
      return BitKonanAdapters.adaptOrderBook(getBitKonanOrderBookBTC());
    } else {
      throw new NotYetImplementedForExchangeException();
    }
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }

}
