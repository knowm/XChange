package org.knowm.xchange.campbx;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.campbx.dto.marketdata.CampBXOrderBook;
import org.knowm.xchange.campbx.dto.marketdata.CampBXTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;

/**
 * Various adapters for converting from CampBX DTOs to XChange DTOs
 */
public final class CampBXAdapters {

  /**
   * CampBXOrderBook to a OrderBook Object
   *
   * @param orderBook
   * @param currencyPair (e.g. BTC/USD)
   * @return
   */
  public static OrderBook adaptOrders(CampBXOrderBook orderBook, CurrencyPair currencyPair) {

    List<LimitOrder> asks = createOrders(currencyPair, Order.OrderType.ASK, orderBook.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, Order.OrderType.BID, orderBook.getBids());
    return new OrderBook(null, asks, bids);
  }

  public static List<LimitOrder> createOrders(CurrencyPair currencyPair, Order.OrderType orderType, List<List<BigDecimal>> orders) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    for (List<BigDecimal> ask : orders) {
      checkArgument(ask.size() == 2, "Expected a pair (price, amount) but got {0} elements.", ask.size());
      limitOrders.add(createOrder(currencyPair, ask, orderType));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(CurrencyPair currencyPair, List<BigDecimal> priceAndAmount, Order.OrderType orderType) {

    return new LimitOrder(orderType, priceAndAmount.get(1), currencyPair, "", null, priceAndAmount.get(0));
  }

  public static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {

    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  /**
   * Adapts a CampBXTicker to a Ticker Object
   *
   * @param campbxTicker
   * @param currencyPair (e.g. BTC/USD)
   * @return
   */
  public static Ticker adaptTicker(CampBXTicker campbxTicker, CurrencyPair currencyPair) {

    BigDecimal last = campbxTicker.getLast();
    BigDecimal bid = campbxTicker.getBid();
    BigDecimal ask = campbxTicker.getAsk();

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).build();

  }

}
