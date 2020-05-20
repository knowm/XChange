package org.knowm.xchange.cryptowatch;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchAsset;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchAssetPair;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchOrderBook;
import org.knowm.xchange.cryptowatch.dto.marketdata.CryptowatchTrade;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;

public class CryptowatchAdapters {

  public static ExchangeMetaData adaptToExchangeMetaData(
      ExchangeMetaData exchangeMetaData,
      List<CryptowatchAssetPair> assetPairs,
      List<CryptowatchAsset> assets) {
    Map<CurrencyPair, CurrencyPairMetaData> pairs = new HashMap<>();
    Map<Currency, CurrencyMetaData> currencies = new HashMap<>();
    if (exchangeMetaData != null) {
      pairs.putAll(exchangeMetaData.getCurrencyPairs());
      currencies.putAll(exchangeMetaData.getCurrencies());
    }
    pairs.putAll(
        assetPairs.stream()
            .filter(pair -> !pair.getSymbol().endsWith("futures")) // filter out the futures assets
            .collect(
                Collectors.toMap(
                    pair -> adaptToCurrencyPair(pair),
                    pair -> adaptToCurrencyPairMetadata(pairs.get(adaptToCurrencyPair(pair))),
                    (oldValue, newValue) -> newValue)));

    currencies.putAll(
        assets.stream()
            .collect(
                Collectors.toMap(
                    asset -> adaptToCurrency(asset),
                    asset -> adaptToCurrencyMetadata(currencies.get(adaptToCurrency(asset))),
                    (oldValue, newValue) -> newValue)));

    return new ExchangeMetaData(
        pairs,
        currencies,
        exchangeMetaData == null ? null : exchangeMetaData.getPublicRateLimits(),
        exchangeMetaData == null ? null : exchangeMetaData.getPrivateRateLimits(),
        exchangeMetaData == null ? null : exchangeMetaData.isShareRateLimits());
  }

  private static CurrencyMetaData adaptToCurrencyMetadata(CurrencyMetaData currencyMetaData) {
    if (currencyMetaData != null) {
      return currencyMetaData;
    }
    return new CurrencyMetaData(2, null);
  }

  private static Currency adaptToCurrency(CryptowatchAsset asset) {
    return Currency.getInstance(asset.getSymbol());
  }

  private static CurrencyPairMetaData adaptToCurrencyPairMetadata(
      CurrencyPairMetaData currencyPairMetaData) {
    if (currencyPairMetaData != null) {
      return currencyPairMetaData;
    } else {
      return new CurrencyPairMetaData(null, null, null, null, null);
    }
  }

  public static CurrencyPair adaptToCurrencyPair(CryptowatchAssetPair pair) {
    CryptowatchAsset base = pair.getBase();
    CryptowatchAsset quote = pair.getQuote();
    Currency baseCurrency = Currency.getInstance(base.getSymbol());
    Currency counterCurrency = Currency.getInstance(quote.getSymbol());
    return new CurrencyPair(baseCurrency, counterCurrency);
  }

  public static OrderBook adaptToOrderbook(CryptowatchOrderBook book, CurrencyPair currencyPair) {
    List<LimitOrder> askOrders =
        book.getAsks().stream()
            .map(
                order ->
                    new LimitOrder(
                        Order.OrderType.ASK,
                        order.getVolume(),
                        currencyPair,
                        "",
                        null,
                        order.getPrice()))
            .collect(Collectors.toList());
    List<LimitOrder> bidOrders =
        book.getBids().stream()
            .map(
                order ->
                    new LimitOrder(
                        Order.OrderType.BID,
                        order.getVolume(),
                        currencyPair,
                        "",
                        null,
                        order.getPrice()))
            .collect(Collectors.toList());
    return new OrderBook(new Date(), askOrders, bidOrders);
  }

  public static Trades adaptToTrades(
      List<CryptowatchTrade> cryptowatchTrades, CurrencyPair currencyPair) {
    List<Trade> trades =
        cryptowatchTrades.stream()
            .map(
                x ->
                    new Trade.Builder()
                        .originalAmount(x.getAmount())
                        .currencyPair(currencyPair)
                        .price(x.getPrice())
                        .timestamp(new Date(x.getTimestamp()))
                        .build())
            .collect(Collectors.toList());
    long last = trades.get(trades.size() - 1).getTimestamp().getTime();
    return new Trades(trades, last, Trades.TradeSortType.SortByTimestamp);
  }

  public static String adaptCurrencyPair(CurrencyPair pair) {
    return pair.base.getCurrencyCode().concat(pair.counter.getCurrencyCode()).toLowerCase();
  }
}
