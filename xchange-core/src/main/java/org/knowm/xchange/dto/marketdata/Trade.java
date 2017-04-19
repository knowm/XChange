package org.knowm.xchange.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Data object representing a Trade
 */
public class Trade {

  /**
   * Did this trade result from the execution of a bid or a ask?
   */
  protected final OrderType type;

  /**
   * Amount that was traded
   */
  protected final BigDecimal tradableAmount;

  /**
   * The currency pair
   */
  protected final CurrencyPair currencyPair;

  /**
   * The price
   */
  protected final BigDecimal price;

  /**
   * The timestamp of the trade according to the exchange's server, null if not provided
   */
  protected final Date timestamp;

  /**
   * The trade id
   */
  protected final String id;

  /**
   * This constructor is called to create a public Trade object in
   * {@link MarketDataService#getTrades(org.knowm.xchange.currency.CurrencyPair, Object...)} implementations) since it's missing the orderId and fee
   * parameters.
   *
   * @param type The trade type (BID side or ASK side)
   * @param tradableAmount The depth of this trade
   * @param price The price (either the bid or the ask)
   * @param timestamp The timestamp of the trade according to the exchange's server, null if not provided
   * @param id The id of the trade
   */
  public Trade(OrderType type, BigDecimal tradableAmount, CurrencyPair currencyPair, BigDecimal price, Date timestamp, String id) {

    this.type = type;
    this.tradableAmount = tradableAmount;
    this.currencyPair = currencyPair;
    this.price = price;
    this.timestamp = timestamp;
    this.id = id;
  }

  public OrderType getType() {

    return type;
  }

  public BigDecimal getTradableAmount() {

    return tradableAmount;
  }

  public CurrencyPair getCurrencyPair() {

    return currencyPair;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public Date getTimestamp() {

    return timestamp;
  }

  public String getId() {

    return id;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return this.id.equals(((Trade) o).getId());
  }

  @Override
  public int hashCode() {

    return id.hashCode();
  }

  @Override
  public String toString() {

    return "Trade [type=" + type + ", tradableAmount=" + tradableAmount + ", currencyPair=" + currencyPair + ", price=" + price + ", timestamp="
        + timestamp + ", id=" + id + "]";
  }

  public static class Builder {

    protected OrderType type;
    protected BigDecimal tradableAmount;
    protected CurrencyPair currencyPair;
    protected BigDecimal price;
    protected Date timestamp;
    protected String id;

    public static Builder from(Trade trade) {
      return new Builder().type(trade.getType()).tradableAmount(trade.getTradableAmount()).currencyPair(trade.getCurrencyPair())
          .price(trade.getPrice()).timestamp(trade.getTimestamp()).id(trade.getId());
    }

    public Builder type(OrderType type) {

      this.type = type;
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

    public Builder price(BigDecimal price) {

      this.price = price;
      return this;
    }

    public Builder timestamp(Date timestamp) {

      this.timestamp = timestamp;
      return this;
    }

    public Builder id(String id) {

      this.id = id;
      return this;
    }

    public Trade build() {

      return new Trade(type, tradableAmount, currencyPair, price, timestamp, id);
    }
  }
}
