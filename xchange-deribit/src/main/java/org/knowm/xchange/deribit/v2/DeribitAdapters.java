package org.knowm.xchange.deribit.v2;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.deribit.v2.dto.DeribitError;
import org.knowm.xchange.deribit.v2.dto.DeribitException;
import org.knowm.xchange.deribit.v2.dto.account.AccountSummary;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitOrderBook;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitTicker;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitTrade;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitTrades;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;

public class DeribitAdapters {

  public static CurrencyPair adaptCurrencyPair(String instrumentName) {
    String[] temp = instrumentName.split("-", 2);
    return new CurrencyPair(temp[0], temp[1]);
  }

  public static String adaptInstrumentName(CurrencyPair pair, Object[] args) {
    if (args == null || args.length == 0) {
      throw new ExchangeException(
          "You need to provide a postfix for the instrument name in the first parameter of the array, ie: PERPETUAL, 28JUN19...");
    }
    String instrumentPostfix = args[0].toString();
    return pair.base.getSymbol() + "-" + instrumentPostfix;
  }

  public static Ticker adaptTicker(DeribitTicker deribitTicker) {

    return new Ticker.Builder()
        .currencyPair(adaptCurrencyPair(deribitTicker.getInstrumentName()))
        .open(deribitTicker.getOpenInterest())
        .last(deribitTicker.getLastPrice())
        .bid(deribitTicker.getBestBidPrice())
        .ask(deribitTicker.getBestAskPrice())
        .high(deribitTicker.getMaxPrice())
        .low(deribitTicker.getMinPrice())
        .volume(deribitTicker.getStats().getVolume())
        .bidSize(deribitTicker.getBestBidAmount())
        .askSize(deribitTicker.getBestAskAmount())
        .timestamp(deribitTicker.getTimestamp())
        .build();
  }

  public static OrderBook adaptOrderBook(DeribitOrderBook deribitOrderBook) {
    CurrencyPair pair = adaptCurrencyPair(deribitOrderBook.getInstrumentName());
    List<LimitOrder> bids = adaptOrdersList(deribitOrderBook.getBids(), Order.OrderType.BID, pair);
    List<LimitOrder> asks = adaptOrdersList(deribitOrderBook.getAsks(), Order.OrderType.ASK, pair);
    return new OrderBook(deribitOrderBook.getTimestamp(), asks, bids);
  }

  /** convert orders map (price -> amount) to a list of limit orders */
  private static List<LimitOrder> adaptOrdersList(
      TreeMap<BigDecimal, BigDecimal> map, Order.OrderType type, CurrencyPair pair) {
    return map.entrySet().stream()
        .map(e -> new LimitOrder(type, e.getValue(), pair, null, null, e.getKey()))
        .collect(Collectors.toList());
  }

  public static Trade adaptTrade(DeribitTrade deribitTrade) {

    Order.OrderType type = null;
    switch (deribitTrade.getDirection()) {
      case buy:
        type = Order.OrderType.BID;
        break;
      case sell:
        type = Order.OrderType.ASK;
        break;
      default:
        break;
    }

    return new Trade.Builder()
        .type(type)
        .originalAmount(deribitTrade.getAmount())
        .currencyPair(adaptCurrencyPair(deribitTrade.getInstrumentName()))
        .price(deribitTrade.getPrice())
        .timestamp(deribitTrade.getTimestamp())
        .id(deribitTrade.getTradeId())
        .build();
  }

  public static Trades adaptTrades(DeribitTrades deribitTrades) {

    return new Trades(
        deribitTrades.getTrades().stream()
            .map(trade -> adaptTrade(trade))
            .collect(Collectors.toList()));
  }

  /** Parse errors from HTTP exceptions */
  public static ExchangeException adapt(DeribitException ex) {

    DeribitError error = ex.getError();

    if (error != null
        && error.getClass().equals(DeribitError.class)
        && isNotEmpty(error.getMessage())) {

      int code = error.getCode();
      String msg = error.getMessage();
      String data = error.getData().toString();
      if (isNotEmpty(data)) {
        msg += " - " + data;
      }

      switch (code) {
        case -32602:
          return new CurrencyPairNotValidException(data, ex);
        default:
          return new ExchangeException(msg, ex);
      }
    }
    return new ExchangeException("Operation failed without any error message", ex);
  }

  public static Balance adapt(AccountSummary as) {
    return new Balance(
        Currency.getInstance(as.getCurrency()), as.getBalance(), as.getAvailableFunds());
  }
}
