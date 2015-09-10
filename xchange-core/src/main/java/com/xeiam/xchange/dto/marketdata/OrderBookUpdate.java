package com.xeiam.xchange.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;

/**
 * Immutable data object representing a Market Depth update.
 */
public final class OrderBookUpdate {

  private final LimitOrder limitOrder;

  /** this is the total volume at this price in the order book */
  private final BigDecimal totalVolume;

  /**
   * Build an order book update.
   *
   * @param type the order type (BID/ASK)
   * @param volume volume in the tradable currency
   * @param tradableIdentifier the tradable identifier (e.g. BTC in BTC/USD)
   * @param transactionCurrency the transaction currency (e.g. USD in BTC/USD)
   * @param limitPrice the limit price - minimum acceptable price for a BID, maximum acceptable price for an ASK
   * @param timestamp the timestamp for the update
   * @param totalVolume the total volume in the order
   */
  // TODO document the distinction between volume and total volume, and which currencies they are in respectively
  // TODO document which currency the limitPrice is in
  public OrderBookUpdate(OrderType type, BigDecimal volume, CurrencyPair currencyPair, BigDecimal limitPrice, Date timestamp,
      BigDecimal totalVolume) {

    this.limitOrder = new LimitOrder(type, volume, currencyPair, "", timestamp, limitPrice);
    this.totalVolume = totalVolume;
  }

  /**
   * Get the order limit.
   *
   * @return the order limit
   */
  public LimitOrder getLimitOrder() {

    return limitOrder;
  }

  /**
   * Get the total volume.
   *
   * @return the total volume
   */
  public BigDecimal getTotalVolume() {

    return totalVolume;
  }

  @Override
  public String toString() {

    return "OrderBookUpdate [limitOrder=" + limitOrder + ", totalVolume=" + totalVolume + "]";
  }

}
