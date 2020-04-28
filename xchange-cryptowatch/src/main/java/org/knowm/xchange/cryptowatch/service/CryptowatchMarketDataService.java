package org.knowm.xchange.cryptowatch.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptowatch.CryptowatchAdapters;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchOrderBook;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author massi.gerardi */
public class CryptowatchMarketDataService extends CryptowatchMarketDataServiceRaw
    implements MarketDataService {

  public CryptowatchMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) {
    if (args == null || args.length < 1) {
      throw new ExchangeException("args[0] must be of type String!");
    }
    String market = (String) args[0];
    CryptowatchOrderBook book = getCryptowatchOrderBook(currencyPair, market);
    return CryptowatchAdapters.adaptToOrderbook(book, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    if (args == null || args.length < 1) {
      throw new ExchangeException("args[0] must be of type String!");
    }
    String market = (String) args[0];
    Long since = null;
    if (args.length > 1) {
      Object arg = args[0];
      if (arg instanceof Long) {
        since = (Long) arg;
      }
      throw new ExchangeException("args[0] must be of type Integer!");
    }
    Integer limit = null;
    if (args.length > 2) {
      Object arg = args[1];
      if (arg instanceof Integer) {
        limit = (Integer) arg;
      }
      throw new ExchangeException("args[1] must be of type Integer!");
    }

    List<CryptowatchTrade> result = getCryptowatchTrades(currencyPair, market, limit, since);
    return CryptowatchAdapters.adaptToTrades(result, currencyPair);
  }
}
