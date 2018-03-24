package org.knowm.xchange.bitcoinium;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import org.knowm.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook.CondensedOrder;
import org.knowm.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;

/**
 * Various adapters for converting from Bitcoinium DTOs to XChange DTOs
 */
public final class BitcoiniumAdapters {

  /**
   * private Constructor
   */
  private BitcoiniumAdapters() {

  }

  /**
   * Adapts a BitcoiniumTicker to a Ticker Object
   *
   * @param bitcoiniumTicker
   * @return
   */
  public static Ticker adaptTicker(BitcoiniumTicker bitcoiniumTicker, CurrencyPair currencyPair) {

    BigDecimal last = bitcoiniumTicker.getLast();
    BigDecimal high = bitcoiniumTicker.getHigh();
    BigDecimal low = bitcoiniumTicker.getLow();
    BigDecimal ask = bitcoiniumTicker.getAsk();
    BigDecimal bid = bitcoiniumTicker.getBid();
    BigDecimal volume = bitcoiniumTicker.getVolume();

    return new Ticker.Builder().currencyPair(currencyPair).last(last).high(high).low(low).volume(volume).ask(ask).bid(bid).build();
  }

  /**
   * Adapts a BitcoiniumOrderbook to a OrderBook Object
   *
   * @param bitcoiniumOrderbook
   * @return the XChange OrderBook
   */
  public static OrderBook adaptOrderbook(BitcoiniumOrderbook bitcoiniumOrderbook, CurrencyPair currencyPair) {

    List<LimitOrder> asks = createOrders(currencyPair, Order.OrderType.ASK, bitcoiniumOrderbook.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, Order.OrderType.BID, bitcoiniumOrderbook.getBids());
    Date date = new Date(bitcoiniumOrderbook.getBitcoiniumTicker().getTimestamp()); // Note, this is the timestamp of the piggy-backed Ticker.
    return new OrderBook(date, asks, bids);

  }

  public static List<LimitOrder> createOrders(CurrencyPair currencyPair, Order.OrderType orderType, CondensedOrder[] condensedOrders) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    for (int i = 0; i < condensedOrders.length; i++) {

      LimitOrder limitOrder = new LimitOrder(orderType, condensedOrders[i].getVolume(), currencyPair, "", null, condensedOrders[i].getPrice());
      limitOrders.add(limitOrder);
    }
    return limitOrders;
  }
}
