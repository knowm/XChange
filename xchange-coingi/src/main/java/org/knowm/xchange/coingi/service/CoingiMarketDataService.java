package org.knowm.xchange.coingi.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coingi.CoingiAdapters;
import org.knowm.xchange.coingi.CoingiErrorAdapter;
import org.knowm.xchange.coingi.dto.CoingiException;
import org.knowm.xchange.coingi.dto.marketdata.CoingiOrderBook;
import org.knowm.xchange.coingi.dto.marketdata.CoingiTransaction;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CoingiMarketDataService extends CoingiMarketDataServiceRaw
    implements MarketDataService {
  public CoingiMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... objects) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    try {
      int maxAskCount = 100;
      int maxBidCount = 100;
      int maxDepthRangeCount = 32;

      if (args.length > 0) {
        maxAskCount = (int) args[0];

        if (args.length > 1) maxBidCount = (int) args[1];

        if (args.length > 2) maxDepthRangeCount = (int) args[2];

        if (args.length > 3)
          throw new IllegalArgumentException(
              "getOrderBook() accepts up to 3 optional arguments, but "
                  + args.length
                  + " were passed!");
      }

      CoingiOrderBook orderBook =
          getCoingiOrderBook(currencyPair, maxAskCount, maxBidCount, maxDepthRangeCount);
      return CoingiAdapters.adaptOrderBook(orderBook);
    } catch (CoingiException e) {
      throw CoingiErrorAdapter.adapt(e);
    }
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    try {
      int maxCount = 100;
      if (args.length == 1) maxCount = (int) args[0];

      List<CoingiTransaction> transactions = getTransactions(currencyPair, maxCount);
      return CoingiAdapters.adaptTrades(transactions, currencyPair);
    } catch (CoingiException e) {
      throw CoingiErrorAdapter.adapt(e);
    }
  }
}
