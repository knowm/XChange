package com.xeiam.xchange.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;

/**
 * DTO representing a market order
 * <p>
 * A market order is a buy or sell order to be executed immediately at current market prices. As long as there are willing sellers and buyers, market
 * orders are filled. Market orders are therefore used when certainty of execution is a priority over price of execution.
 * </p>
 * <strong>Use market orders with caution, and review {@link LimitOrder} in case it is more suitable.</strong>
 */
public final class MarketOrder extends Order {

  /**
   * @param type Either BID (buying) or ASK (selling)
   * @param tradableAmount The amount to trade
   * @param currencyPair The identifier (e.g. BTC/USD)
   * @param id An id (usually provided by the exchange)
   * @param timestamp the absolute time for this order
   */
  public MarketOrder(OrderType type, BigDecimal tradableAmount, CurrencyPair currencyPair, String id, Date timestamp) {

    super(type, tradableAmount, currencyPair, id, timestamp);
  }

  /**
   * @param type Either BID (buying) or ASK (selling)
   * @param tradableAmount The amount to trade
   * @param currencyPair The identifier (e.g. BTC/USD)
   * @param timestamp the absolute time for this order
   */
  public MarketOrder(OrderType type, BigDecimal tradableAmount, CurrencyPair currencyPair, Date timestamp) {

    super(type, tradableAmount, currencyPair, "", timestamp);
  }

  /**
   * @param type Either BID (buying) or ASK (selling)
   * @param tradableAmount The amount to trade
   * @param currencyPair currencyPair The identifier (e.g. BTC/USD)
   */
  public MarketOrder(OrderType type, BigDecimal tradableAmount, CurrencyPair currencyPair) {

    super(type, tradableAmount, currencyPair, "", null);
  }

  public static class Builder {

    OrderType orderType;
    BigDecimal tradableAmount;
    CurrencyPair currencyPair;
    String id;
    Date timestamp;

    public Builder(OrderType orderType, CurrencyPair currencyPair) {

      this.orderType = orderType;
      this.currencyPair = currencyPair;
    }

    public static Builder from(Order o) {
      return new Builder(o.getType(), o.getCurrencyPair()).tradableAmount(o.getTradableAmount()).timestamp(o.getTimestamp()).id(o.getId());
    }

    public Builder orderType(OrderType orderType) {

      this.orderType = orderType;
      return this;
    }

    public Builder tradableAmount(BigDecimal tradableAmount) {

      this.tradableAmount = tradableAmount;
      return this;
    }

    public Builder currencyPair(CurrencyPair currencyPair) {

      this.currencyPair = currencyPair;
      return this;
    }

    public Builder id(String id) {

      this.id = id;
      return this;
    }

    public Builder timestamp(Date timestamp) {

      this.timestamp = timestamp;
      return this;
    }

    public MarketOrder build() {

      return new MarketOrder(orderType, tradableAmount, currencyPair, id, timestamp);
    }
  }

}
