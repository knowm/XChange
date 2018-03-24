package org.knowm.xchange.bleutrade.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bleutrade.BleutradeAdapters;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeOrderBook;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeTicker;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BleutradeMarketDataService extends BleutradeMarketDataServiceRaw implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BleutradeMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    BleutradeTicker bleutradeTicker = getBleutradeTicker(currencyPair);
    return BleutradeAdapters.adaptBleutradeTicker(bleutradeTicker);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    int depth = 50;

    if (args != null && args.length > 0) {
      if (args[0] instanceof Integer) {
        depth = (Integer) args[0];
      }
    }

    BleutradeOrderBook bleutradeOrderBook = getBleutradeOrderBook(currencyPair, depth);
    return BleutradeAdapters.adaptBleutradeOrderBook(bleutradeOrderBook, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    int count = 50;

    if (args != null && args.length > 0) {
      if (args[0] instanceof Integer) {
        count = (Integer) args[0];
      }
    }

    if (count < 1) {
      count = 1;
    } else if (count > 200) {
      count = 200;
    }

    List<BleutradeTrade> bleutradeTrades = getBleutradeMarketHistory(currencyPair, count);
    return BleutradeAdapters.adaptBleutradeMarketHistory(bleutradeTrades, currencyPair);
  }

}
