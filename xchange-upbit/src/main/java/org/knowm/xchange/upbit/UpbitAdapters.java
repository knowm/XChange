package org.knowm.xchange.upbit;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.upbit.dto.account.UpbitBalances;
import org.knowm.xchange.upbit.dto.marketdata.UpbitMarket;
import org.knowm.xchange.upbit.dto.marketdata.UpbitOrderBook;
import org.knowm.xchange.upbit.dto.marketdata.UpbitOrderBookData;
import org.knowm.xchange.upbit.dto.marketdata.UpbitOrderBooks;
import org.knowm.xchange.upbit.dto.marketdata.UpbitTicker;
import org.knowm.xchange.upbit.dto.marketdata.UpbitTickers;
import org.knowm.xchange.upbit.dto.marketdata.UpbitTrade;
import org.knowm.xchange.upbit.dto.marketdata.UpbitTrades;
import org.knowm.xchange.upbit.dto.trade.UpbitOrderResponse;
import org.knowm.xchange.utils.DateUtils;

public final class UpbitAdapters {

  private UpbitAdapters() {}

  public static OrderBook adaptOrderBook(UpbitOrderBooks upbitOrderBooks) {
    UpbitOrderBook upbitOrderBook = upbitOrderBooks.getUpbitOrderBooks()[0];
    CurrencyPair currencyPair = UpbitUtils.toCurrencyPair(upbitOrderBook.getMarket());
    Map<OrderType, List<LimitOrder>> orderbookMap =
        adaptMarketOrderToLimitOrder(upbitOrderBook.getOrderbookUnits(), currencyPair);
    return new OrderBook(
        DateUtils.fromMillisUtc(upbitOrderBook.getTimestamp().longValue()),
        orderbookMap.get(OrderType.ASK),
        orderbookMap.get(OrderType.BID));
  }

  private static Map<OrderType, List<LimitOrder>> adaptMarketOrderToLimitOrder(
      UpbitOrderBookData[] upbitOrders, CurrencyPair currencyPair) {

    List<LimitOrder> asks = new ArrayList<>(upbitOrders.length);
    List<LimitOrder> bids = new ArrayList<>(upbitOrders.length);
    Arrays.stream(upbitOrders)
        .forEach(
            upbitOrder -> {
              asks.add(
                  new LimitOrder.Builder(OrderType.ASK, currencyPair)
                      .originalAmount(upbitOrder.getAskSize())
                      .limitPrice(upbitOrder.getAskPrice())
                      .build());
              bids.add(
                  new LimitOrder.Builder(OrderType.BID, currencyPair)
                      .originalAmount(upbitOrder.getBidSize())
                      .limitPrice(upbitOrder.getBidPrice())
                      .build());
            });
    Map<OrderType, List<LimitOrder>> map = new HashMap<>();
    map.put(OrderType.ASK, asks);
    map.put(OrderType.BID, bids);
    return map;
  }

  public static Ticker adaptTicker(UpbitTickers tickers) {
    UpbitTicker ticker = tickers.getTickers()[0];

    return adaptTicker(ticker);
  }

  public static List<Ticker> adaptTickers(UpbitTickers tickers) {
    return Arrays.stream(tickers.getTickers())
        .map(UpbitAdapters::adaptTicker)
        .collect(Collectors.toList());
  }

  private static Ticker adaptTicker(UpbitTicker ticker) {
    final CurrencyPair currencyPair = UpbitUtils.toCurrencyPair(ticker.getMarket());
    final Date date = DateUtils.fromMillisUtc(ticker.getTimestamp().longValue() * 1000);
    return new Ticker.Builder()
        .instrument(currencyPair)
        .high(ticker.getHigh_price())
        .low(ticker.getLow_price())
        .last(ticker.getTrade_price())
        .volume(ticker.getTrade_volume())
        .open(ticker.getOpening_price())
        .timestamp(date)
        .build();
  }

  public static Trades adaptTrades(UpbitTrades trades, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<>(trades.getUpbitTrades().length);
    for (UpbitTrade trade : trades.getUpbitTrades()) {
      tradeList.add(adaptTrade(trade, currencyPair));
    }
    return new Trades(tradeList, 0, Trades.TradeSortType.SortByTimestamp);
  }

  private static Trade adaptTrade(UpbitTrade trade, CurrencyPair currencyPair) {
    OrderType orderType = OrderType.BID;
    if (OrderType.ASK.toString().equals(trade.getAskBid())) orderType = OrderType.ASK;

    return new Trade.Builder()
        .type(orderType)
        .originalAmount(trade.getTradeVolume())
        .instrument(currencyPair)
        .price(trade.getTradePrice())
        .timestamp(DateUtils.fromMillisUtc(trade.getTimestamp().longValue()))
        .id("")
        .build();
  }

  public static Wallet adaptWallet(UpbitBalances wallets) {
    List<Balance> balances = new ArrayList<>();
    Arrays.stream(wallets.getBalances())
        .forEach(
            balance -> {
              balances.add(
                  new Balance(
                      Currency.getInstance(balance.getCurrency()),
                      balance.getBalance().add(balance.getLocked()),
                      balance.getBalance()));
            });
    return Wallet.Builder.from(balances).build();
  }

  public static Order adaptOrderInfo(UpbitOrderResponse upbitOrderResponse) {
    Order.OrderStatus status;
    if (upbitOrderResponse.getState().equalsIgnoreCase("cancel")) {
      status = Order.OrderStatus.CANCELED;
    } else if (upbitOrderResponse.getExecutedVolume().compareTo(BigDecimal.ZERO) == 0) {
      status = Order.OrderStatus.NEW;
    } else if (upbitOrderResponse.getRemainingVolume().compareTo(BigDecimal.ZERO) > 0) {
      status = Order.OrderStatus.PARTIALLY_FILLED;
    } else if (upbitOrderResponse.getState().equalsIgnoreCase("done")) {
      status = Order.OrderStatus.FILLED;
    } else {
      status = Order.OrderStatus.NEW;
    }

    final OrderType orderType = UpbitUtils.fromSide(upbitOrderResponse.getSide());
    final CurrencyPair currencyPair = UpbitUtils.toCurrencyPair(upbitOrderResponse.getMarket());
    return new LimitOrder.Builder(orderType, currencyPair)
        .originalAmount(upbitOrderResponse.getVolume())
        .id(upbitOrderResponse.getUuid())
        .timestamp(Date.from(ZonedDateTime.parse(upbitOrderResponse.getCreatedAt()).toInstant()))
        .limitPrice(upbitOrderResponse.getAvgPrice())
        .averagePrice(upbitOrderResponse.getAvgPrice())
        .cumulativeAmount(upbitOrderResponse.getExecutedVolume())
        .fee(upbitOrderResponse.getPaidFee())
        .orderStatus(status)
        .build();
  }

  public static ExchangeMetaData adaptMetadata(List<UpbitMarket> markets) {
    Map<CurrencyPair, CurrencyPairMetaData> pairMeta =
        markets.stream()
            .map(UpbitMarket::getMarket)
            .map(UpbitUtils::toCurrencyPair)
            .collect(
                Collectors.toMap(
                    Function.identity(), cp -> new CurrencyPairMetaData.Builder().build()));
    return new ExchangeMetaData(pairMeta, null, null, null, null);
  }
}
