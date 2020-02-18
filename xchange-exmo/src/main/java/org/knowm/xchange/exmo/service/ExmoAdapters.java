package org.knowm.xchange.exmo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.utils.DateUtils;

public class ExmoAdapters {
  public static UserTrade adaptTrade(Map<String, String> tradeDatum, CurrencyPair currencyPair) {
    Order.OrderType type = adaptOrderType(tradeDatum);
    BigDecimal amount = new BigDecimal(tradeDatum.get("quantity"));
    BigDecimal price = new BigDecimal(tradeDatum.get("price"));
    Date date = DateUtils.fromUnixTime(Long.parseLong(tradeDatum.get("date")));
    String tradeId = tradeDatum.get("trade_id");
    String orderId = tradeDatum.get("order_id");

    return new UserTrade.Builder()
        .type(type)
        .originalAmount(amount)
        .currencyPair(currencyPair)
        .price(price)
        .timestamp(date)
        .id(tradeId)
        .orderId(orderId)
        .build();
  }

  public static Order.OrderType adaptOrderType(Map<String, String> order) {
    return order.get("type").equals("sell") ? Order.OrderType.ASK : Order.OrderType.BID;
  }

  public static Balance adaptBalance(
      Map<String, String> balances, Map<String, String> reserved, String ccy) {
    Currency currency = Currency.getInstance(ccy);
    BigDecimal available = new BigDecimal(balances.get(ccy));
    BigDecimal frozen = new BigDecimal(reserved.get(ccy));

    return new Balance(currency, available.add(frozen), available, frozen);
  }

  public static List<LimitOrder> adaptOrders(
      CurrencyPair currencyPair, Map<String, Object> orderBookData, Order.OrderType type) {
    if (orderBookData == null) {
      return Collections.EMPTY_LIST;
    }
    List<List<String>> orders =
        (List<List<String>>) orderBookData.get(type.equals(Order.OrderType.ASK) ? "ask" : "bid");
    if (orders == null) {
      return Collections.EMPTY_LIST;
    }
    List<LimitOrder> result = new ArrayList<>();
    for (List<String> orderData : orders) {
      BigDecimal price = new BigDecimal(orderData.get(0));
      BigDecimal quantity = new BigDecimal(orderData.get(1));
      result.add(new LimitOrder(type, quantity, currencyPair, null, null, price));
    }
    return result;
  }

  public static Ticker adaptTicker(CurrencyPair currencyPair, Map<String, String> data) {
    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .ask(new BigDecimal(data.get("sell_price")))
        .bid(new BigDecimal(data.get("buy_price")))
        .high(new BigDecimal(data.get("high")))
        .last(new BigDecimal(data.get("last_trade")))
        .low(new BigDecimal(data.get("low")))
        .volume(new BigDecimal(data.get("vol")))
        .timestamp(DateUtils.fromMillisUtc(Long.parseLong(data.get("updated"))))
        .build();
  }

  public static String format(CurrencyPair currencyPair) {
    return currencyPair.base.getCurrencyCode() + "_" + currencyPair.counter.getCurrencyCode();
  }
}
