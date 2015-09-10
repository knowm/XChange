package com.xeiam.xchange.okcoin.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.okcoin.FuturesContract;
import com.xeiam.xchange.okcoin.OkCoinAdapters;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class OkCoinFuturesMarketDataService extends OkCoinMarketDataServiceRaw implements PollingMarketDataService {
  /** Default contract to use */
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
    if (args.length > 0) {
      return OkCoinAdapters.adaptTicker(getFuturesTicker(currencyPair, (FuturesContract) args[0]), currencyPair);
    } else {
      return OkCoinAdapters.adaptTicker(getFuturesTicker(currencyPair, futuresContract), currencyPair);
    }
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    if (args.length > 0) {
      return OkCoinAdapters.adaptOrderBook(getFuturesDepth(currencyPair, (FuturesContract) args[0]), currencyPair);
    } else {
      return OkCoinAdapters.adaptOrderBook(getFuturesDepth(currencyPair, futuresContract), currencyPair);
    }
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    if (args.length > 0) {
      return OkCoinAdapters.adaptTrades(getFuturesTrades(currencyPair, (FuturesContract) args[0]), currencyPair);
    } else {
      return OkCoinAdapters.adaptTrades(getFuturesTrades(currencyPair, futuresContract), currencyPair);
    }
  }
}
