package org.knowm.xchange.exmo.service;

import static org.apache.commons.lang3.StringUtils.join;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;

public class ExmoMarketDataServiceRaw extends BaseExmoService {
  protected ExmoMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public Map<CurrencyPair, Ticker> tickers() throws IOException {
    Map<String, Map<String, String>> tickers = exmo.ticker();

    Map<CurrencyPair, Ticker> results = new HashMap<>();

    for (String market : tickers.keySet()) {
      CurrencyPair currencyPair = adaptMarket(market);
      Ticker ticker = ExmoAdapters.adaptTicker(currencyPair, tickers.get(market));
      results.put(ticker.getCurrencyPair(), ticker);
    }

    return results;
  }

  public OrderBook orderBook(CurrencyPair currencyPair) throws IOException {
    String market = ExmoAdapters.format(currencyPair);

    Map<String, Map<String, Object>> map = exmo.orderBook(market);
    Map<String, Object> orderBookData = map.get(market);

    List<LimitOrder> asks =
        ExmoAdapters.adaptOrders(currencyPair, orderBookData, Order.OrderType.ASK);
    List<LimitOrder> bids =
        ExmoAdapters.adaptOrders(currencyPair, orderBookData, Order.OrderType.BID);

    return new OrderBook(null, asks, bids);
  }

  public void updateMetadata(ExchangeMetaData exchangeMetaData) throws IOException {
    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = exchangeMetaData.getCurrencyPairs();
    Map<Currency, CurrencyMetaData> currencies = exchangeMetaData.getCurrencies();

    Map<String, Map<String, String>> map = exmo.pairSettings();
    for (String marketName : map.keySet()) {
      CurrencyPair currencyPair = adaptMarket(marketName);
      Map<String, String> data = map.get(marketName);

      Integer priceScale = null;
      BigDecimal tradingFee = null;

      if (currencyPairs.containsKey(currencyPair)) {
        priceScale = currencyPairs.get(currencyPair).getPriceScale();
        tradingFee = currencyPairs.get(currencyPair).getTradingFee();
      }

      CurrencyPairMetaData staticMeta = currencyPairs.get(currencyPair);
      // min_quantity or min_amount ???
      CurrencyPairMetaData currencyPairMetaData =
          new CurrencyPairMetaData(
              tradingFee,
              new BigDecimal(data.get("min_quantity")),
              new BigDecimal(data.get("max_quantity")),
              priceScale,
              staticMeta != null ? staticMeta.getFeeTiers() : null);

      currencyPairs.put(currencyPair, currencyPairMetaData);

      if (!currencies.containsKey(currencyPair.base))
        currencies.put(currencyPair.base, new CurrencyMetaData(8, null));
      if (!currencies.containsKey(currencyPair.counter))
        currencies.put(currencyPair.counter, new CurrencyMetaData(8, null));
    }
  }

  public Trades trades(CurrencyPair... currencyPairs) {
    return trades(Arrays.asList(currencyPairs));
  }

  public Trades trades(Collection<CurrencyPair> currencyPairs) {
    Map<String, CurrencyPair> markets = new HashMap<>();
    for (CurrencyPair currencyPair : currencyPairs) {
      String market = currencyPair.base + "_" + currencyPair.counter;
      markets.put(market, currencyPair);
    }
    String marketNames = join(markets.keySet(), ",");

    List<Trade> results = new ArrayList<>();

    Map<String, List<Map>> tradesMap = exmo.trades(marketNames);
    for (String key : tradesMap.keySet()) {
      CurrencyPair currencyPair = markets.get(key);

      List<Map> trades = tradesMap.get(key);
      for (Map tradeData : trades) {
        String id = tradeData.get("trade_id").toString();
        String type = tradeData.get("type").toString();
        String price = tradeData.get("price").toString();

        // which one??
        String quantity = tradeData.get("quantity").toString();
        String amount = tradeData.get("amount").toString();

        long unixTimestamp = Long.parseLong(tradeData.get("date").toString());

        results.add(
            new Trade.Builder()
                .type(type.equalsIgnoreCase("sell") ? Order.OrderType.ASK : Order.OrderType.BID)
                .originalAmount(new BigDecimal(quantity))
                .currencyPair(currencyPair)
                .price(new BigDecimal(price))
                .timestamp(new Date(unixTimestamp * 1000L))
                .id(id)
                .build());
      }
    }

    return new Trades(results);
  }
}
