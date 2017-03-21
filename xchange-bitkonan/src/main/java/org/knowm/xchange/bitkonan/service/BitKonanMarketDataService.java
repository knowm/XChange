package org.knowm.xchange.bitkonan.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitkonan.BitKonanAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BitKonanMarketDataService extends BitKonanMarketDataServiceRaw implements MarketDataService {

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

    return BitKonanAdapters.adaptTicker(getBitKonanTicker(currencyPair.base.getCurrencyCode().toLowerCase()), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    return BitKonanAdapters.adaptOrderBook(getBitKonanOrderBook(currencyPair.base.getCurrencyCode().toLowerCase()), currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    return BitKonanAdapters.adaptTrades(getBitKonanTrades(currencyPair.base.getCurrencyCode()), currencyPair);
  }

}
