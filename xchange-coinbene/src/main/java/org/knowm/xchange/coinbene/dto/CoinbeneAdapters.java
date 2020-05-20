package org.knowm.xchange.coinbene.dto;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import org.knowm.xchange.coinbene.dto.account.CoinbeneCoinBalances;
import org.knowm.xchange.coinbene.dto.marketdata.*;
import org.knowm.xchange.coinbene.dto.trading.CoinbeneLimitOrder;
import org.knowm.xchange.coinbene.dto.trading.CoinbeneOrders;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.FeeTier;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;

public class CoinbeneAdapters {

  public static String adaptCurrencyPair(CurrencyPair pair) {

    return (pair.base.getCurrencyCode() + pair.counter.getCurrencyCode()).toLowerCase();
  }

  private static CurrencyPair adaptSymbol(String symbol) {
    // Iterate by base currency priority at Coinbene.
    for (Currency base :
        Arrays.asList(Currency.BTC, Currency.ETH, Currency.USDT, Currency.getInstance("BR"))) {
      String counter = symbol.replace(base.toString(), "");
      if (symbol.startsWith(base.toString())) {
        return new CurrencyPair(base, new Currency(counter));
      } else if (symbol.endsWith(base.toString())) {
        return new CurrencyPair(new Currency(counter), base);
      }
    }
    throw new IllegalArgumentException("Could not parse currency pair from '" + symbol + "'");
  }

  public static String adaptOrderType(OrderType type) {
    switch (type) {
      case BID:
        return "buy-limit";
      case ASK:
        return "sell-limit";
      default:
        throw new IllegalArgumentException("Unsupported order type: " + type);
    }
  }

  private static Ticker adaptCoinbeneTicker(CoinbeneTicker ticker, long timestamp) {
    return new Ticker.Builder()
        .currencyPair(adaptSymbol(ticker.getSymbol()))
        .bid(ticker.getBid())
        .ask(ticker.getAsk())
        .high(ticker.getDayHigh())
        .low(ticker.getDayLow())
        .last(ticker.getLast())
        .volume(ticker.getDayVolume())
        .timestamp(new Date(timestamp))
        .build();
  }

  public static Ticker adaptTicker(CoinbeneTicker.Container container) {
    return adaptCoinbeneTicker(container.getTicker(), container.getTimestamp());
  }

  public static List<Ticker> adaptTickers(CoinbeneTicker.Container container) {
    long timestamp = container.getTimestamp();

    return container.getTickers().stream()
        .filter(
            coinbeneTicker ->
                !coinbeneTicker.getSymbol().endsWith("BRL")) // DASHBRL, EOSBRL unsupported
        .map(coinbeneTicker -> adaptCoinbeneTicker(coinbeneTicker, timestamp))
        .collect(Collectors.toList());
  }

  public static OrderBook adaptOrderBook(
      CoinbeneOrderBook.Container response, CurrencyPair currencyPair) {

    CoinbeneOrderBook orders = response.getOrderBook();
    List<LimitOrder> asks = new LinkedList<>();
    orders.getAsks().forEach(order -> asks.add(adaptOrder(currencyPair, OrderType.ASK, order)));
    List<LimitOrder> bids = new LinkedList<>();
    orders.getBids().forEach(order -> bids.add(adaptOrder(currencyPair, OrderType.BID, order)));
    return new OrderBook(null, asks, bids);
  }

  public static Trade adaptTrade(CoinbeneTrade trade, CurrencyPair pair) {

    return new Trade.Builder()
        .price(trade.getPrice())
        .originalAmount(trade.getQuantity())
        .currencyPair(pair)
        .type(trade.getTake().getOrderType())
        .timestamp(new Date(trade.getTimestamp()))
        .id(trade.getTradeId())
        .build();
  }

  public static OpenOrders adaptOpenOrders(CoinbeneOrders orders) {

    if (orders == null) {
      return new OpenOrders(Collections.EMPTY_LIST);
    }

    return new OpenOrders(
        orders.getOrders().stream()
            .map(CoinbeneAdapters::adaptLimitOrder)
            .collect(Collectors.toList()));
  }

  public static AccountInfo adaptAccountInfo(CoinbeneCoinBalances balances) {
    Wallet wallet =
        Wallet.Builder.from(
                balances.getBalances().stream()
                    .map(
                        balance ->
                            new Balance(
                                new Currency(balance.getAsset()),
                                balance.getTotal(),
                                balance.getAvailable(),
                                balance.getReserved()))
                    .collect(Collectors.toList()))
            .build();

    return new AccountInfo(wallet);
  }

  private static LimitOrder adaptOrder(
      CurrencyPair currencyPair, OrderType orderType, CoinbeneOrder coinbeneOrder) {

    return new LimitOrder(
        orderType, coinbeneOrder.getQuantity(), currencyPair, null, null, coinbeneOrder.getPrice());
  }

  public static LimitOrder adaptLimitOrder(CoinbeneLimitOrder order) {

    return new LimitOrder.Builder(null, adaptSymbol(order.getSymbol()))
        .id(order.getOrderId())
        .timestamp(new Date(order.getCreateTime()))
        .orderStatus(order.getOrderStatus().getStatus())
        .limitPrice(order.getPrice())
        .cumulativeAmount(order.getFilledQuantity())
        .originalAmount(order.getOrderQuantity())
        .build();
  }

  public static ExchangeMetaData adaptMetadata(List<CoinbeneSymbol> markets) {
    Map<CurrencyPair, CurrencyPairMetaData> pairMeta = new HashMap<>();
    for (CoinbeneSymbol ticker : markets) {
      pairMeta.put(
          new CurrencyPair(ticker.getBaseAsset(), ticker.getQuoteAsset()),
          new CurrencyPairMetaData(
              ticker.getTakerFee(),
              ticker.getMinQuantity(),
              null,
              ticker.getTickSize(),
              new FeeTier[] {
                new FeeTier(BigDecimal.ZERO, new Fee(ticker.getMakerFee(), ticker.getTakerFee()))
              },
              new BigDecimal(
                  Math.pow(10.0, -ticker.getLotStepSize()),
                  new MathContext(Math.max(0, ticker.getLotStepSize()), RoundingMode.HALF_UP))));
    }
    return new ExchangeMetaData(pairMeta, null, null, null, null);
  }
}
