package org.knowm.xchange.bittrex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.bittrex.dto.account.BittrexBalance;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexLevel;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexMarketSummary;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexSymbol;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTicker;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexTrade;
import org.knowm.xchange.bittrex.dto.trade.BittrexOrder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;

public final class BittrexAdapters {

  public static List<CurrencyPair> adaptCurrencyPairs(Collection<BittrexSymbol> bittrexSymbols) {
    return bittrexSymbols.stream()
        .map(BittrexAdapters::adaptCurrencyPair)
        .collect(Collectors.toList());
  }

  public static CurrencyPair adaptCurrencyPair(BittrexSymbol bittrexSymbol) {
    Currency baseSymbol = bittrexSymbol.getBaseCurrencySymbol();
    Currency counterSymbol = bittrexSymbol.getQuoteCurrencySymbol();
    return new CurrencyPair(baseSymbol, counterSymbol);
  }

  public static List<LimitOrder> adaptOpenOrders(List<BittrexOrder> bittrexOpenOrders) {
    return bittrexOpenOrders == null
        ? null
        : bittrexOpenOrders.stream().map(BittrexAdapters::adaptOrder).collect(Collectors.toList());
  }

  public static List<LimitOrder> adaptOrders(
      BittrexLevel[] orders, CurrencyPair currencyPair, OrderType orderType, int depth) {
    if (orders == null || orders.length == 0) {
      return new ArrayList<>();
    }
    return Arrays.stream(orders)
        .limit(Math.min(orders.length, depth))
        .map(
            order ->
                new LimitOrder.Builder(orderType, currencyPair)
                    .originalAmount(order.getAmount())
                    .limitPrice(order.getPrice())
                    .build())
        .collect(Collectors.toList());
  }

  public static LimitOrder adaptOrder(BittrexOrder order) {
    return adaptOrder(order, adaptOrderStatus(order));
  }

  public static LimitOrder adaptOrder(BittrexOrder order, OrderStatus status) {

    OrderType type =
        order.getDirection().equalsIgnoreCase(BittrexConstants.SELL)
            ? OrderType.ASK
            : OrderType.BID;
    CurrencyPair pair = BittrexUtils.toCurrencyPair(order.getMarketSymbol());
    return new LimitOrder.Builder(type, pair)
        .originalAmount(order.getQuantity())
        .id(order.getId())
        .timestamp(order.getUpdatedAt() != null ? order.getUpdatedAt() : order.getCreatedAt())
        .limitPrice(order.getLimit())
        .remainingAmount(order.getQuantity().subtract(order.getFillQuantity()))
        .fee(order.getCommission())
        .orderStatus(status)
        .build();
  }

  public static OrderStatus adaptOrderStatus(BittrexOrder order) {
    if (order.getQuantity() == null) {
      return OrderStatus.UNKNOWN;
    }
    if (order.getFillQuantity() == null
        || order.getFillQuantity().compareTo(BigDecimal.ZERO) == 0) {
      return OrderStatus.NEW;
    }
    BigDecimal remaining = order.getQuantity().subtract(order.getFillQuantity());
    return remaining.signum() == 1 ? OrderStatus.PARTIALLY_FILLED : OrderStatus.FILLED;
  }

  public static Trade adaptTrade(BittrexTrade trade, CurrencyPair currencyPair) {
    OrderType orderType =
        BittrexConstants.BUY.equalsIgnoreCase(trade.getTakerSide()) ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = trade.getQuantity();
    BigDecimal price = trade.getRate();
    Date date = trade.getExecutedAt();
    final String tradeId = String.valueOf(trade.getId());
    return new Trade.Builder()
        .type(orderType)
        .originalAmount(amount)
        .currencyPair(currencyPair)
        .price(price)
        .timestamp(date)
        .id(tradeId)
        .build();
  }

  public static Trades adaptTrades(List<BittrexTrade> trades, CurrencyPair currencyPair) {
    return new Trades(
        trades.stream().map(trade -> adaptTrade(trade, currencyPair)).collect(Collectors.toList()),
        trades.stream()
            .map(BittrexTrade::getId)
            .map(Long::parseLong)
            .max(Long::compareTo)
            .orElse(0L),
        TradeSortType.SortByID);
  }

  public static List<UserTrade> adaptUserTrades(List<BittrexOrder> bittrexUserTrades) {
    return bittrexUserTrades.stream()
        .map(
            bittrexOrder ->
                new UserTrade.Builder()
                    .type(
                        BittrexConstants.BUY.equalsIgnoreCase(bittrexOrder.getType())
                            ? OrderType.BID
                            : OrderType.ASK)
                    .originalAmount(bittrexOrder.getFillQuantity())
                    .currencyPair(BittrexUtils.toCurrencyPair(bittrexOrder.getMarketSymbol()))
                    .price(bittrexOrder.getLimit())
                    .timestamp(bittrexOrder.getClosedAt())
                    .id(bittrexOrder.getId())
                    .feeAmount(bittrexOrder.getCommission())
                    .feeCurrency(
                        BittrexUtils.toCurrencyPair(bittrexOrder.getMarketSymbol()).counter)
                    .build())
        .collect(Collectors.toList());
  }

  public static Ticker adaptTicker(
      BittrexMarketSummary bittrexMarketSummary, BittrexTicker bittrexTicker) {

    CurrencyPair currencyPair = BittrexUtils.toCurrencyPair(bittrexTicker.getSymbol());
    BigDecimal last = bittrexTicker.getLastTradeRate();
    BigDecimal bid = bittrexTicker.getBidRate();
    BigDecimal ask = bittrexTicker.getAskRate();
    BigDecimal high = bittrexMarketSummary.getHigh();
    BigDecimal low = bittrexMarketSummary.getLow();
    BigDecimal quoteVolume = bittrexMarketSummary.getQuoteVolume();
    BigDecimal volume = bittrexMarketSummary.getVolume();
    Date timestamp = bittrexMarketSummary.getUpdatedAt();

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .last(last)
        .bid(bid)
        .ask(ask)
        .high(high)
        .low(low)
        .quoteVolume(quoteVolume)
        .volume(volume)
        .timestamp(timestamp)
        .build();
  }

  public static Wallet adaptWallet(Collection<BittrexBalance> balances) {
    List<Balance> wallets =
        balances.stream()
            .map(
                bittrexBalance ->
                    new Balance.Builder()
                        .currency(bittrexBalance.getCurrencySymbol())
                        .total(bittrexBalance.getTotal())
                        .available(bittrexBalance.getAvailable())
                        .timestamp(bittrexBalance.getUpdatedAt())
                        .build())
            .collect(Collectors.toList());

    return Wallet.Builder.from(wallets).build();
  }

  public static void adaptMetaData(List<BittrexSymbol> rawSymbols, ExchangeMetaData metaData) {
    BittrexAdapters.adaptCurrencyPairs(rawSymbols)
        .forEach(
            currencyPair -> {
              metaData.getCurrencyPairs().putIfAbsent(currencyPair, null);
              metaData.getCurrencies().putIfAbsent(currencyPair.base, null);
              metaData.getCurrencies().putIfAbsent(currencyPair.counter, null);
            });
  }

  private BittrexAdapters() {
    throw new AssertionError();
  }
}
