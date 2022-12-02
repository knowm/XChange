package org.knowm.xchange.cobinhood;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.cobinhood.dto.CobinhoodResponse;
import org.knowm.xchange.cobinhood.dto.account.CobinhoodCoinBalances;
import org.knowm.xchange.cobinhood.dto.marketdata.CobinhoodCurrencyPair;
import org.knowm.xchange.cobinhood.dto.marketdata.CobinhoodOrderBook;
import org.knowm.xchange.cobinhood.dto.marketdata.CobinhoodTicker;
import org.knowm.xchange.cobinhood.dto.marketdata.CobinhoodTrade;
import org.knowm.xchange.cobinhood.dto.trading.CobinhoodOrder;
import org.knowm.xchange.cobinhood.dto.trading.CobinhoodOrders;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;

public class CobinhoodAdapters {

  public static String adaptCurrencyPair(CurrencyPair pair) {

    return pair.base.getCurrencyCode() + "-" + pair.counter.getCurrencyCode();
  }

  private static CurrencyPair adaptSymbol(String symbol) {
    try {
      String[] split = symbol.split("-");
      return new CurrencyPair(split[0], split[1]);
    } catch (RuntimeException e) {
      throw new IllegalArgumentException("Not supported Cobinhood symbol: " + symbol);
    }
  }

  public static Ticker adaptTicker(CobinhoodTicker ticker) {

    return new Ticker.Builder()
        .currencyPair(adaptSymbol(ticker.getTradingPairId()))
        .bid(ticker.getHighestBid())
        .ask(ticker.getLowestAsk())
        .high(ticker.getDayHigh())
        .low(ticker.getDayLow())
        .last(ticker.getLastTradePrice())
        .volume(ticker.getDayVolume())
        .timestamp(new Date(ticker.getTimestamp()))
        .build();
  }

  public static OrderBook adaptOrderBook(
      CobinhoodResponse<CobinhoodOrderBook.Container> response, CurrencyPair currencyPair) {

    CobinhoodOrderBook chOrders = response.getResult().getOrderBook();
    List<LimitOrder> asks = new LinkedList<>();
    chOrders.getAsks().forEach(s -> asks.add(adaptLimitOrder(currencyPair, OrderType.ASK, s)));
    List<LimitOrder> bids = new LinkedList<>();
    chOrders.getBids().forEach(s -> bids.add(adaptLimitOrder(currencyPair, OrderType.BID, s)));
    return new OrderBook(null, asks, bids);
  }

  public static Trade adaptTrade(CobinhoodTrade trade, CurrencyPair pair) {

    return new Trade.Builder()
        .price(trade.getPrice())
        .originalAmount(trade.getSize())
        .currencyPair(pair)
        .type(trade.getMakerSide().getOrderType())
        .timestamp(new Date(trade.getTimestamp()))
        .id(trade.getId())
        .build();
  }

  public static OpenOrders adaptOpenOrders(CobinhoodOrders orders) {

    return new OpenOrders(
        orders.getOrders().stream()
            .map(CobinhoodAdapters::adaptOrder)
            .collect(Collectors.toList()));
  }

  public static AccountInfo adaptAccountInfo(CobinhoodCoinBalances balances) {
    Wallet wallet =
        Wallet.Builder.from(
                balances.getBalances().stream()
                    .map(
                        balance ->
                            new Balance(
                                new Currency(balance.getCurrency()), balance.getTotalAmount()))
                    .collect(Collectors.toList()))
            .build();

    return new AccountInfo(wallet);
  }

  private static LimitOrder adaptLimitOrder(
      CurrencyPair currencyPair, OrderType orderType, List<BigDecimal> cobinhoodLimitOrder) {

    return new LimitOrder(
        orderType,
        cobinhoodLimitOrder.get(2),
        currencyPair,
        null,
        null,
        cobinhoodLimitOrder.get(0));
  }

  private static LimitOrder adaptOrder(CobinhoodOrder order) {

    return new LimitOrder.Builder(
            order.getSide().getOrderType(), adaptSymbol(order.getTradingPair()))
        .id(order.getId())
        .timestamp(new Date(order.getTimestamp()))
        .orderType(order.getSide().getOrderType())
        .orderStatus(order.getState().getStatus())
        .limitPrice(order.getPrice())
        .cumulativeAmount(order.getFilled())
        .originalAmount(order.getSize())
        .build();
  }

  public static ExchangeMetaData adaptMetadata(List<CobinhoodCurrencyPair> pairs) {
    Map<CurrencyPair, CurrencyPairMetaData> pairMeta = new HashMap<>();
    for (CobinhoodCurrencyPair pair : pairs) {
      pairMeta.put(
          new CurrencyPair(pair.getBaseCurrencyId(), pair.getQuoteCurrencyId()),
          new CurrencyPairMetaData(null, pair.getBaseMinSize(), pair.getBaseMaxSize(), null, null));
    }
    return new ExchangeMetaData(pairMeta, null, null, null, null);
  }
}
