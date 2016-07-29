package org.knowm.xchange.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

/**
 * DTO representing a market order
 * <p>
 * A market order is a buy or sell order to be executed immediately at current market prices. As long as there are willing sellers and buyers, market
 * orders are filled. Market orders are therefore used when certainty of execution is a priority over price of execution.
 * </p>
 * <strong>Use market orders with caution, and review {@link LimitOrder} in case it is more suitable.</strong>
 */
public class MarketOrder extends Order {

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

  public static class Builder extends Order.Builder {

    public Builder(OrderType orderType, CurrencyPair currencyPair) {

      super(orderType, currencyPair);
    }

    public static Builder from(Order order) {

      return (Builder) new Builder(order.getType(), order.getCurrencyPair()).tradableAmount(order.getTradableAmount()).timestamp(order.getTimestamp())
          .id(order.getId()).flags(order.getOrderFlags());
    }

    @Override
    public Builder orderType(OrderType orderType) {

      return (Builder) super.orderType(orderType);
    }

    @Override
    public Builder orderStatus(OrderStatus status) {

      return (Builder) super.orderStatus(status);
    }

    @Override
    public Builder averagePrice(BigDecimal averagePrice) {

      return (Builder) super.averagePrice(averagePrice);
    }

    @Override
    public Builder tradableAmount(BigDecimal tradableAmount) {

      return (Builder) super.tradableAmount(tradableAmount);
    }

    @Override
    public Builder currencyPair(CurrencyPair currencyPair) {

      return (Builder) super.currencyPair(currencyPair);
    }

    @Override
    public Builder id(String id) {

      return (Builder) super.id(id);
    }

    @Override
    public Builder timestamp(Date timestamp) {

      return (Builder) super.timestamp(timestamp);
    }

    public MarketOrder build() {

      MarketOrder order = new MarketOrder(orderType, tradableAmount, currencyPair, id, timestamp);
      order.setOrderFlags(flags);
      order.setAveragePrice(averagePrice);
      order.setOrderStatus(status);
      return order;
    }
  }
}
