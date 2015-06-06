package com.xeiam.xchange.ripple;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.ripple.dto.marketdata.RippleOrder;
import com.xeiam.xchange.ripple.dto.marketdata.RippleOrderBook;

/**
 * Various adapters for converting from Ripple DTOs to XChange DTOs
 */
public abstract class RippleAdapters {

  /**
   * private Constructor
   */
  private RippleAdapters() {
  }

  /**
   * Adapts a Ripple OrderBook to an XChange OrderBook Object
   */
  public static OrderBook adaptOrderBook(final RippleOrderBook rippleOrderBook, final CurrencyPair currencyPair) {
    final String orderBook = rippleOrderBook.getOrderBook(); // e.g. XRP/BTC+rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B
    final String[] splitPair = orderBook.split("/");

    final String baseSymbol = splitPair[0].split("\\+")[0];
    if (baseSymbol.equals(currencyPair.baseSymbol) == false) {
      throw new IllegalStateException(String.format("base symbol in Ripple order book %s does not match requested base %s", orderBook, currencyPair));
    }

    final String counterSymbol = splitPair[1].split("\\+")[0];
    if (counterSymbol.equals(currencyPair.counterSymbol) == false) {
      throw new IllegalStateException(String.format("counter symbol in Ripple order book %s does not match requested base %s", orderBook,
          currencyPair));
    }

    final List<LimitOrder> bids = createOrders(currencyPair, OrderType.BID, rippleOrderBook.getBids());
    final List<LimitOrder> asks = createOrders(currencyPair, OrderType.ASK, rippleOrderBook.getAsks());
    return new OrderBook(null, asks, bids);
  }

  public static List<LimitOrder> createOrders(final CurrencyPair currencyPair, final OrderType orderType, final List<RippleOrder> orders) {
    final List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (final RippleOrder ripple : orders) {

      // Taker Pays = the amount the taker must pay to consume this order.
      // Taker Gets = the amount the taker will get once the order is consumed.
      //
      // Funded vs Unfunded https://wiki.ripple.com/Unfunded_offers

      // amount of base currency
      final BigDecimal tradableAmount;
      if (orderType == OrderType.BID) {
        tradableAmount = new BigDecimal(ripple.getTakerPaysFunded().getValue());
      } else {
        tradableAmount = new BigDecimal(ripple.getTakerGetsFunded().getValue());
      }

      // price in counter currency
      final BigDecimal price = new BigDecimal(ripple.getPrice().getValue());

      final LimitOrder xchangeOrder = new LimitOrder(orderType, tradableAmount, currencyPair, Integer.toString(ripple.getSequence()), null, price);
      limitOrders.add(xchangeOrder);
    }
    return limitOrders;
  }
}
