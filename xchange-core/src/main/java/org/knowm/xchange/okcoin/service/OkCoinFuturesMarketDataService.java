package org.knowm.xchange.okcoin.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.okcoin.FuturesContract;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class OkCoinFuturesMarketDataService extends OkCoinMarketDataServiceRaw implements MarketDataService {
  /**
   * Default contract to use
   */
  private final FuturesContract futuresContract;

  /**
   * Constructor
   *
   * @param exchange
   */
  public OkCoinFuturesMarketDataService(Exchange exchange, FuturesContract futuresContract) {

    super(exchange);

    this.futuresContract = futuresContract;
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    if (args != null && args.length > 0) {
      return OkCoinAdapters.adaptTicker(getFuturesTicker(currencyPair, (FuturesContract) args[0]), currencyPair);
    } else {
      return OkCoinAdapters.adaptTicker(getFuturesTicker(currencyPair, futuresContract), currencyPair);
    }
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    if (args != null && args.length > 0) {
      return OkCoinAdapters.adaptOrderBook(getFuturesDepth(currencyPair, (FuturesContract) args[0]), currencyPair);
    } else {
      return OkCoinAdapters.adaptOrderBook(getFuturesDepth(currencyPair, futuresContract), currencyPair);
    }
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    if (args != null && args.length > 0) {
      return OkCoinAdapters.adaptTrades(getFuturesTrades(currencyPair, (FuturesContract) args[0]), currencyPair);
    } else {
      return OkCoinAdapters.adaptTrades(getFuturesTrades(currencyPair, futuresContract), currencyPair);
    }
  }
}
