package org.knowm.xchange.cryptopia;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaCurrency;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaMarketHistory;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaOrder;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaOrderBook;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaTicker;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaTradePair;
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
import org.knowm.xchange.utils.DateUtils;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

/** Various adapters for converting from Cryptopia DTOs to XChange DTOs */
public final class CryptopiaAdapters {
  private static final String TIMEZONE = "UTC";

  private CryptopiaAdapters() {}

  public static Date convertTimestamp(String timestamp) {

    try {
      return org.knowm.xchange.utils.DateUtils.fromISO8601DateString(timestamp);
    } catch (com.fasterxml.jackson.databind.exc.InvalidFormatException e) {
      throw new Error("Date parse failure:" + timestamp);
    }
  }

  /**
   * Adapts a CryptopiaOrderBook to an OrderBook Object
   *
   * @param cryptopiaOrderBook The exchange specific order book
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrderBook(
      CryptopiaOrderBook cryptopiaOrderBook, CurrencyPair currencyPair) {
    List<LimitOrder> asks =
        createOrders(currencyPair, Order.OrderType.ASK, cryptopiaOrderBook.getAsks());
    List<LimitOrder> bids =
        createOrders(currencyPair, Order.OrderType.BID, cryptopiaOrderBook.getBids());

    return new OrderBook(new Date(), asks, bids);
  }

  private static List<LimitOrder> createOrders(
      CurrencyPair currencyPair, Order.OrderType orderType, List<CryptopiaOrder> orders) {
    List<LimitOrder> limitOrders = new ArrayList<>();

    for (CryptopiaOrder cryptopiaOrder : orders) {
      limitOrders.add(createOrder(currencyPair, cryptopiaOrder, orderType));
    }

    return limitOrders;
  }

  private static LimitOrder createOrder(
      CurrencyPair currencyPair, CryptopiaOrder cryptopiaOrder, Order.OrderType orderType) {
    return new LimitOrder(
        orderType, cryptopiaOrder.getVolume(), currencyPair, "", null, cryptopiaOrder.getPrice());
  }

  /**
   * Adapts a CryptopiaTicker to a Ticker Object
   *
   * @param cryptopiaTicker The exchange specific ticker
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange ticker
   */
  public static Ticker adaptTicker(CryptopiaTicker cryptopiaTicker, CurrencyPair currencyPair) {
    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .last(cryptopiaTicker.getLast())
        .bid(cryptopiaTicker.getBid())
        .ask(cryptopiaTicker.getAsk())
        .high(cryptopiaTicker.getHigh())
        .low(cryptopiaTicker.getLow())
        .volume(cryptopiaTicker.getVolume())
        .timestamp(new Date())
        .build();
  }

  public static Trades adaptTrades(List<CryptopiaMarketHistory> cryptopiaMarketHistory) {
    List<Trade> trades = new ArrayList<>();
    long lastTradeTimestamp = 0;

    for (CryptopiaMarketHistory marketHistory : cryptopiaMarketHistory) {
      final long tradeTimestamp = marketHistory.getTimestamp();

      if (tradeTimestamp > lastTradeTimestamp) {
        lastTradeTimestamp = tradeTimestamp;
      }

      trades.add(adaptTrade(marketHistory));
    }

    return new Trades(trades, lastTradeTimestamp, Trades.TradeSortType.SortByTimestamp);
  }

  private static Trade adaptTrade(CryptopiaMarketHistory cryptopiaMarketHistory) {
    Order.OrderType orderType =
        "Buy".equals(cryptopiaMarketHistory.getType()) ? Order.OrderType.BID : Order.OrderType.ASK;
    String tradeTimestamp = String.valueOf(cryptopiaMarketHistory.getTimestamp());
    Date date = DateUtils.fromMillisUtc(cryptopiaMarketHistory.getTimestamp() * 1000L);

    return new Trade.Builder()
        .type(orderType)
        .originalAmount(cryptopiaMarketHistory.getAmount())
        .currencyPair(
            CurrencyPairDeserializer.getCurrencyPairFromString(cryptopiaMarketHistory.getLabel()))
        .price(cryptopiaMarketHistory.getPrice())
        .timestamp(date)
        .id(tradeTimestamp)
        .build();
  }

  public static ExchangeMetaData adaptToExchangeMetaData(
      List<CryptopiaCurrency> cryptopiaCurrencies, List<CryptopiaTradePair> tradePairs) {
    Map<CurrencyPair, CurrencyPairMetaData> marketMetaDataMap = new HashMap<>();
    Map<Currency, CurrencyMetaData> currencyMetaDataMap = new HashMap<>();

    for (CryptopiaCurrency cryptopiaCurrency : cryptopiaCurrencies) {
      currencyMetaDataMap.put(
          Currency.getInstance(cryptopiaCurrency.getSymbol()), new CurrencyMetaData(8, null));
    }

    for (CryptopiaTradePair cryptopiaTradePair : tradePairs) {
      CurrencyPair currencyPair =
          CurrencyPairDeserializer.getCurrencyPairFromString(cryptopiaTradePair.getLabel());
      CurrencyPairMetaData currencyPairMetaData =
          new CurrencyPairMetaData(
              cryptopiaTradePair.getTradeFee(),
              cryptopiaTradePair.getMinimumTrade(),
              cryptopiaTradePair.getMaximumTrade(),
              8,
              null);

      marketMetaDataMap.put(currencyPair, currencyPairMetaData);
    }

    return new ExchangeMetaData(marketMetaDataMap, currencyMetaDataMap, null, null, null);
  }
}
