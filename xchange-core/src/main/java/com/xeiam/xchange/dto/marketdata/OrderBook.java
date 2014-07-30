package com.xeiam.xchange.dto.marketdata;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;

/**
 * DTO representing the exchange order book
 */
public final class OrderBook {

  private Date timeStamp;
  private final List<LimitOrder> asks;
  private final List<LimitOrder> bids;

  /**
   * Constructor
   * 
   * @param timeStamp The timeStamp of the OrderBook or of the latest Update
   * @param asks
   *          The ASK orders
   * @param bids
   *          The BID orders
   */
  public OrderBook(Date timeStamp, List<LimitOrder> asks, List<LimitOrder> bids) {

    this.timeStamp = timeStamp;
    this.asks = asks;
    this.bids = bids;
  }

  public Date getTimeStamp() {

    return timeStamp;
  }

  public List<LimitOrder> getAsks() {

    Collections.sort(asks);
    return asks;
  }

  public List<LimitOrder> getBids() {

    Collections.sort(bids);
    return bids;
  }

  /**
   * Given a new LimitOrder, it will replace and old matching limit order in
   * the orderbook or simply get added. Finally, it is sorted. The timeStamp may be updated as well.
   * 
   * @param limitOrder the new LimitOrder
   */
  public void update(LimitOrder limitOrder) {

    if (limitOrder.getType().equals(OrderType.ASK)) {

      Iterator<LimitOrder> it = asks.iterator();
      while (it.hasNext()) {
        LimitOrder order = it.next();
        if (order.getLimitPrice().compareTo(limitOrder.getLimitPrice()) == 0) { // they
                                                                                // are
                                                                                // equal.
                                                                                // found
                                                                                // it!
          it.remove();
          break;
        }
      }
      asks.add(limitOrder); // just add it
      Collections.sort(asks); // finally sort

    }
    else {

      Iterator<LimitOrder> it = bids.iterator();
      while (it.hasNext()) {
        LimitOrder order = it.next();
        if (order.getLimitPrice().compareTo(limitOrder.getLimitPrice()) == 0) { // they
                                                                                // are
                                                                                // equal.
                                                                                // found
                                                                                // it!
          it.remove();
          break;
        }
      }
      bids.add(limitOrder); // just add it
      Collections.sort(bids); // finally sort
    }
    updateDate(limitOrder.getTimestamp());
  }

  /**
   * Given an OrderBookUpdate, it will replace and old matching limit order in
   * the orderbook or simply get added. Finally, it is sorted.The timeStamp may be updated as well.
   * 
   * @param orderBookUpdate the new OrderBookUpdate
   */
  public void update(OrderBookUpdate orderBookUpdate) {

    // First, we need to remove orders with the same limit price
    Iterator<LimitOrder> it;
    if (orderBookUpdate.getLimitOrder().getType() == OrderType.ASK) {
      it = this.asks.iterator();
    }
    else {
      it = this.bids.iterator();
    }
    while (it.hasNext()) {
      LimitOrder order = it.next();
      if (order.getLimitPrice().compareTo(orderBookUpdate.getLimitOrder().getLimitPrice()) == 0) { // they are equal. found it!
        it.remove();
        break;
      }
    }

    // If volume is not zero we need to add a new limit order with the
    // updated amount
    if (orderBookUpdate.getTotalVolume().compareTo(BigDecimal.ZERO) != 0) {

      OrderType type = orderBookUpdate.getLimitOrder().getType();
      BigDecimal tradeableAmount = orderBookUpdate.getTotalVolume();
      CurrencyPair currencyPair = orderBookUpdate.getLimitOrder().getCurrencyPair();
      String id = orderBookUpdate.getLimitOrder().getId();
      Date date = orderBookUpdate.getLimitOrder().getTimestamp();
      BigDecimal limit = orderBookUpdate.getLimitOrder().getLimitPrice();
      LimitOrder updatedOrder = new LimitOrder(type, tradeableAmount, currencyPair, id, date, limit);

      if (orderBookUpdate.getLimitOrder().getType() == OrderType.ASK) {
        asks.add(updatedOrder);
        Collections.sort(asks);
      }
      else {
        bids.add(updatedOrder);
        Collections.sort(bids);
      }
    }
    updateDate(orderBookUpdate.getLimitOrder().getTimestamp());
  }

  private void updateDate(Date updateDate) {

    if (updateDate != null && (timeStamp == null || updateDate.after(timeStamp))) {
      this.timeStamp = updateDate;
    }
  }

  @Override
  public String toString() {

    return "Depth [timestamp: " + timeStamp + ", asks=" + asks.toString() + ", bids=" + bids.toString() + "]";
  }

}
