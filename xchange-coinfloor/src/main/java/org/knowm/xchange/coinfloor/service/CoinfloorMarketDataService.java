package org.knowm.xchange.coinfloor.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinfloor.CoinfloorAdapters;
import org.knowm.xchange.coinfloor.dto.markedata.CoinfloorOrderBook;
import org.knowm.xchange.coinfloor.dto.markedata.CoinfloorTicker;
import org.knowm.xchange.coinfloor.dto.markedata.CoinfloorTransaction;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CoinfloorMarketDataService extends CoinfloorMarketDataServiceRaw implements MarketDataService {
  public CoinfloorMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair pair, Object... args) throws IOException {
    CoinfloorTicker raw = super.getCoinfloorTicker(pair);
    return CoinfloorAdapters.adaptTicker(raw, pair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair pair, Object... args) throws IOException {
    CoinfloorOrderBook raw = getCoinfloorOrderBook(pair);
    return CoinfloorAdapters.adaptOrderBook(raw, pair);
  }

  @Override
  public Trades getTrades(CurrencyPair pair, Object... args) throws IOException {
    CoinfloorInterval interval;
    if (args != null && args.length == 1 && args[0] instanceof CoinfloorInterval) {
      interval = (CoinfloorInterval) args[0];
    } else {
      interval = CoinfloorInterval.HOUR;
    }
    CoinfloorTransaction[] raw = getCoinfloorTransactions(pair, interval);
    return CoinfloorAdapters.adaptTrades(raw, pair);
  }
}
