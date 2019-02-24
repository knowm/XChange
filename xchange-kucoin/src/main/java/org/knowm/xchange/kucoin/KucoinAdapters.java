package org.knowm.xchange.kucoin;

import static java.util.stream.Collectors.toCollection;
import static org.knowm.xchange.dto.Order.OrderStatus.CANCELED;
import static org.knowm.xchange.dto.Order.OrderStatus.NEW;
import static org.knowm.xchange.dto.Order.OrderStatus.PARTIALLY_FILLED;
import static org.knowm.xchange.dto.Order.OrderStatus.UNKNOWN;
import static org.knowm.xchange.dto.Order.OrderType.ASK;
import static org.knowm.xchange.dto.Order.OrderType.BID;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeException;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Ordering;
import com.kucoin.sdk.rest.response.AccountBalancesResponse;
import com.kucoin.sdk.rest.response.OrderBookResponse;
import com.kucoin.sdk.rest.response.OrderResponse;
import com.kucoin.sdk.rest.response.SymbolResponse;
import com.kucoin.sdk.rest.response.SymbolTickResponse;
import com.kucoin.sdk.rest.response.TradeHistoryResponse;
import com.kucoin.sdk.rest.response.TradeResponse;

public class KucoinAdapters {

  public static String adaptCurrencyPair(CurrencyPair pair) {
    return pair.base.getCurrencyCode() + "-" + pair.counter.getCurrencyCode();
  }

  public static CurrencyPair adaptCurrencyPair(String symbol) {
    String[] split = symbol.split("-");
    if (split.length != 2) {
      throw new ExchangeException("Invalid kucoin symbol: " + symbol);
    }
    return new CurrencyPair(split[0], split[1]);
  }

  public static Ticker.Builder adaptTickerFull(CurrencyPair pair, SymbolTickResponse stats) {
    return new Ticker.Builder()
        .currencyPair(pair)
        .bid(stats.getBuy())
        .ask(stats.getSell())
        .last(stats.getLast())
        .high(stats.getHigh())
        .low(stats.getLow())
        .volume(stats.getVol())
        .quoteVolume(stats.getVolValue())
        .open(stats.getOpen())
        .timestamp(new Date(stats.getTime()));
  }

  /**
   * Imperfect implementation. Kucoin appears to enforce a base <strong>and</strong>
   * quote min <strong>and max</strong> amount that the XChange API current doesn't
   * take account of.
   *
   * @param exchangeMetaData The static exchange metadata.
   * @param kucoinSymbols Kucoin symbol data.
   * @return Exchange metadata.
   */
  public static ExchangeMetaData adaptMetadata(ExchangeMetaData exchangeMetaData, List<SymbolResponse> kucoinSymbols) {
    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = exchangeMetaData.getCurrencyPairs();
    Map<Currency, CurrencyMetaData> currencies = exchangeMetaData.getCurrencies();

    for (SymbolResponse symbol : kucoinSymbols) {

      CurrencyPair pair = adaptCurrencyPair(symbol.getSymbol());
      CurrencyPairMetaData staticMetaData = exchangeMetaData.getCurrencyPairs().get(pair);

      BigDecimal minSize = symbol.getBaseMinSize();
      BigDecimal maxSize = symbol.getBaseMaxSize();
      int priceScale = symbol.getQuoteIncrement().stripTrailingZeros().scale();

      CurrencyPairMetaData cpmd =
          new CurrencyPairMetaData(
              null,
              minSize,
              maxSize,
              priceScale,
              staticMetaData != null ? staticMetaData.getFeeTiers() : null);
      currencyPairs.put(pair, cpmd);

      if (!currencies.containsKey(pair.base)) currencies.put(pair.base, null);
      if (!currencies.containsKey(pair.counter)) currencies.put(pair.counter, null);
    }

    return new ExchangeMetaData(
        currencyPairs,
        currencies,
        exchangeMetaData.getPublicRateLimits(),
        exchangeMetaData.getPrivateRateLimits(),
        true);
  }

  public static OrderBook adaptOrderBook(CurrencyPair currencyPair, OrderBookResponse kc) {
    Date timestamp = new Date(Long.parseLong(kc.getSequence()));
    List<LimitOrder> asks = kc.getAsks().stream()
        .map(PriceAndSize::new)
        .sorted(Ordering.natural().onResultOf(s -> s.price))
        .map(s -> adaptLimitOrder(currencyPair, ASK, s, timestamp))
        .collect(toCollection(LinkedList::new));
    List<LimitOrder> bids = kc.getBids().stream()
        .map(PriceAndSize::new)
        .sorted(Ordering.natural().onResultOf((PriceAndSize s) -> s.price).reversed())
        .map(s -> adaptLimitOrder(currencyPair, BID, s, timestamp))
        .collect(toCollection(LinkedList::new));
    return new OrderBook(timestamp, asks, bids);
  }

  private static LimitOrder adaptLimitOrder(CurrencyPair currencyPair, OrderType orderType, PriceAndSize priceAndSize, Date timestamp) {
    return new LimitOrder.Builder(orderType, currencyPair)
        .limitPrice(priceAndSize.price)
        .originalAmount(priceAndSize.size)
        .orderStatus(NEW)
        .build();
  }

  public static Trades adaptTrades(CurrencyPair currencyPair, List<TradeHistoryResponse> kucoinTrades) {
    return new Trades(kucoinTrades.stream()
        .map(o -> adaptTrade(currencyPair, o))
        .collect(Collectors.toList()), TradeSortType.SortByTimestamp);
  }

  public static Balance adaptBalance(AccountBalancesResponse a) {
    return new Balance(Currency.getInstance(a.getCurrency()), a.getBalance(), a.getAvailable());
  }

  private static Trade adaptTrade(CurrencyPair currencyPair, TradeHistoryResponse trade) {
    return new Trade.Builder()
        .currencyPair(currencyPair)
        .originalAmount(trade.getSize())
        .price(trade.getPrice())
        .timestamp(new Date(Long.parseLong(trade.getSequence())))
        .type(adaptSide(trade.getSide()))
        .build();
  }

  private static OrderType adaptSide(String side) {
    return "sell".equals(side) ? ASK : BID;
  }

  public static Order adaptOrder(OrderResponse order) {

    OrderType orderType = adaptSide(order.getSide());
    CurrencyPair currencyPair = adaptCurrencyPair(order.getSymbol());

    OrderStatus status;
    if (order.isCancelExist()) {
      status = CANCELED;
    } else if (order.isActive()) {
      if (order.getDealSize().signum() == 0) {
        status = NEW;
      } else {
        status = PARTIALLY_FILLED;
      }
    } else {
      status = UNKNOWN;
    }

    Order.Builder builder;
    if (StringUtils.isNotEmpty(order.getStop())) {
      builder = new StopOrder.Builder(orderType, currencyPair)
          .limitPrice(order.getPrice())
          .stopPrice(order.getStopPrice());
    } else {
      builder = new LimitOrder.Builder(orderType, currencyPair)
          .limitPrice(order.getPrice());
    }
    builder = builder
        .averagePrice(order.getDealSize().compareTo(BigDecimal.ZERO) == 0
            ? MoreObjects.firstNonNull(order.getPrice(), order.getStopPrice())
            : order.getDealFunds().divide(order.getDealSize(), RoundingMode.HALF_UP))
        .cumulativeAmount(order.getDealSize())
        .fee(order.getFee())
        .id(order.getId())
        .orderStatus(status)
        .originalAmount(order.getSize())
        .remainingAmount(order.getSize().subtract(order.getDealSize()))
        .timestamp(order.getCreatedAt());

    if (StringUtils.isNotEmpty(order.getTimeInForce())) {
      builder.flag(TimeInForce.getTimeInForce(order.getTimeInForce()));
    }

    return StopOrder.Builder.class.isInstance(builder)
        ? ((StopOrder.Builder) builder).build()
        : ((LimitOrder.Builder) builder).build();
  }

  public static UserTrade adaptUserTrade(TradeResponse trade) {
    return new UserTrade.Builder()
        .currencyPair(adaptCurrencyPair(trade.getSymbol()))
        .feeAmount(trade.getFee())
        .feeCurrency(Currency.getInstance(trade.getFeeCurrency()))
        .id(trade.getTradeId())
        .orderId(trade.getOrderId())
        .originalAmount(trade.getSize())
        .price(trade.getPrice())
        .timestamp(trade.getTradeCreatedAt())
        .type(adaptSide(trade.getSide()))
        .build();
  }

  private static final class PriceAndSize {

    final BigDecimal price;
    final BigDecimal size;

    PriceAndSize(List<String> data) {
      this.price = new BigDecimal(data.get(0));
      this.size = new BigDecimal(data.get(1));
    }
  }
}