package org.knowm.xchange.yobit.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.yobit.YoBitAdapters;
import org.knowm.xchange.yobit.dto.DefaultOrderBookRequestParams;
import org.knowm.xchange.yobit.dto.DefaultPublicTradesDataRequestParams;
import org.knowm.xchange.yobit.dto.DefaultTickerRequestParams;
import org.knowm.xchange.yobit.dto.MultiCurrencyOrderBooksRequestParams;
import org.knowm.xchange.yobit.dto.MultiCurrencyPairTickersRequestParams;
import org.knowm.xchange.yobit.dto.MultiCurrencyPublicTradesDataRequestParams;
import org.knowm.xchange.yobit.dto.OrderBooksRequestParam;
import org.knowm.xchange.yobit.dto.PublicTradesRequestParams;
import org.knowm.xchange.yobit.dto.TickersRequestParams;
import org.knowm.xchange.yobit.dto.marketdata.YoBitOrderBooksReturn;
import org.knowm.xchange.yobit.dto.marketdata.YoBitTickersReturn;
import org.knowm.xchange.yobit.dto.marketdata.YoBitTrade;
import org.knowm.xchange.yobit.dto.marketdata.YoBitTrades;

public class YoBitMarketDataService extends YoBitMarketDataServiceRaw implements MarketDataService {

  public YoBitMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return getTickers(new DefaultTickerRequestParams(currencyPair)).iterator().next();
  }

  public Iterable<Ticker> getTickers(TickersRequestParams params) throws IOException {
    if (params instanceof MultiCurrencyPairTickersRequestParams) {
      MultiCurrencyPairTickersRequestParams request = (MultiCurrencyPairTickersRequestParams) params;

      YoBitTickersReturn yoBitTickers = getYoBitTickers(request.currencyPairs);

      List<Ticker> res = new ArrayList<>();
      for (String ccyPair : yoBitTickers.tickers.keySet()) {
        CurrencyPair currencyPair = YoBitAdapters.adaptCurrencyPair(ccyPair);
        Ticker ticker = YoBitAdapters.adaptTicker(yoBitTickers.tickers.get(ccyPair), currencyPair);
        res.add(ticker);
      }

      return res;
    }

    throw new IllegalStateException("Do not understand " + params);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    int level = 50;
    if (args != null && args.length > 0) {
      if (args[0] instanceof Number) {
        level = ((Number) args[0]).intValue();
      }
    }

    return getOrderBooks(new DefaultOrderBookRequestParams(level, currencyPair)).iterator().next();
  }

  public Iterable<OrderBook> getOrderBooks(OrderBooksRequestParam params) throws IOException {
    if (params instanceof MultiCurrencyOrderBooksRequestParams) {
      MultiCurrencyOrderBooksRequestParams booksRequestParam = (MultiCurrencyOrderBooksRequestParams) params;

      List<OrderBook> res = new ArrayList<>();

      YoBitOrderBooksReturn orderBooks = getOrderBooks(booksRequestParam.currencyPairs, booksRequestParam.desiredDepth);

      for (String ccyPair : orderBooks.orderBooks.keySet()) {
        CurrencyPair currencyPair = YoBitAdapters.adaptCurrencyPair(ccyPair);
        OrderBook orderBook = YoBitAdapters.adaptOrderBook(orderBooks.orderBooks.get(ccyPair), currencyPair);
        res.add(orderBook);
      }

      return res;
    }

    throw new IllegalStateException("Don't understand " + params);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return getTrades(new DefaultPublicTradesDataRequestParams(currencyPair));
  }

  public Trades getTrades(PublicTradesRequestParams params) throws IOException {
    if (params instanceof MultiCurrencyPublicTradesDataRequestParams) {
      MultiCurrencyPublicTradesDataRequestParams multiCurrencyPublicTradesDataRequestParams = (MultiCurrencyPublicTradesDataRequestParams) params;
      YoBitTrades publicTrades = getPublicTrades(multiCurrencyPublicTradesDataRequestParams.currencyPairs);

      List<Trade> all = new ArrayList<>();
      Map<String, List<YoBitTrade>> tradesByCcyPair = publicTrades.getTrades();
      for (String ccyPair : tradesByCcyPair.keySet()) {
        CurrencyPair currencyPair = YoBitAdapters.adaptCurrencyPair(ccyPair);
        List<Trade> trades = YoBitAdapters.adaptTrades(tradesByCcyPair.get(ccyPair), currencyPair).getTrades();
        all.addAll(trades);
      }

      Collections.sort(all, new Comparator<Trade>() {
        @Override
        public int compare(Trade a, Trade b) {
          return a.getTimestamp().compareTo(b.getTimestamp());
        }
      });

      return new Trades(all, Trades.TradeSortType.SortByID);
    }

    throw new IllegalStateException("Don't understand " + params);
  }
}
