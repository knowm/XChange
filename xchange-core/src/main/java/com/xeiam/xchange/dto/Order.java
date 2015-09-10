package com.xeiam.xchange.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.xeiam.xchange.currency.CurrencyPair;

/**
 * Data object representing an order
 */
public abstract class Order {

  public enum OrderType {

    /**
     * Buying order (you're making an offer)
     */
    BID, /**
          * Selling order (you're asking for offers)
          */
    ASK
  }

  public interface IOrderFlags {
  };

  /**
   * Order type i.e. bid or ask
   */
  private final OrderType type;

  /**
   * Amount to be ordered / amount that was ordered
   */
  private final BigDecimal tradableAmount;

  /**
   * The currency pair
   */
  private final CurrencyPair currencyPair;

  /**
   * An identifier that uniquely identifies the order
   */
  private final String id;

  /**
   * The timestamp on the order according to the exchange's server, null if not provided
   */
  private final Date timestamp;

  /**
   * Any applicable order flags
   */
  private final Set<IOrderFlags> flags = new HashSet<IOrderFlags>();

  /**
   * @param type Either BID (buying) or ASK (selling)
   * @param tradableAmount The amount to trade
   * @param CurrencyPair currencyPair The identifier (e.g. BTC/USD)
   * @param id An id (usually provided by the exchange)
   * @param timestamp the absolute time for this order according to the exchange's server, null if not provided
   */
  public Order(OrderType type, BigDecimal tradableAmount, CurrencyPair currencyPair, String id, Date timestamp) {

    this.type = type;
    this.tradableAmount = tradableAmount;
    this.currencyPair = currencyPair;
    this.id = id;
    this.timestamp = timestamp;
  }

  /**
   * @return The type (BID or ASK)
   */
  public OrderType getType() {

    return type;
  }

  /**
   * @return The amount to trade
   */
  public BigDecimal getTradableAmount() {

    return tradableAmount;
  }

  public CurrencyPair getCurrencyPair() {

    return currencyPair;
  }

  /**
   * @return A unique identifier (normally provided by the exchange)
   */
  public String getId() {

    return id;
  }

  public Date getTimestamp() {

    return timestamp;
  }

  public Set<IOrderFlags> getOrderFlags() {
    return flags;
  }

  public void addOrderFlag(IOrderFlags flag) {
    flags.add(flag);
  }

  public void setOrderFlags(Set<IOrderFlags> flags) {
    this.flags.clear();
    if (flags != null) {
      this.flags.addAll(flags);
    }
  }

  @Override
  public String toString() {

    return "Order [type=" + type + ", tradableAmount=" + tradableAmount + ", currencyPair=" + currencyPair + ", id=" + id + ", timestamp=" + timestamp
        + "]";
  }

  @Override
  public int hashCode() {

    int hash = 7;
    hash = 83 * hash + (this.type != null ? this.type.hashCode() : 0);
    hash = 83 * hash + (this.tradableAmount != null ? this.tradableAmount.hashCode() : 0);
    hash = 83 * hash + (this.currencyPair != null ? this.currencyPair.hashCode() : 0);
    hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
    hash = 83 * hash + (this.timestamp != null ? this.timestamp.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {

    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Order other = (Order) obj;
    if (this.type != other.type) {
      return false;
    }
    if ((this.tradableAmount == null) ? (other.tradableAmount != null) : this.tradableAmount.compareTo(other.tradableAmount) != 0) {
      return false;
    }
    if ((this.currencyPair == null) ? (other.currencyPair != null) : !this.currencyPair.equals(other.currencyPair)) {
      return false;
    }
    if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
      return false;
    }
    if (this.timestamp != other.timestamp && (this.timestamp == null || !this.timestamp.equals(other.timestamp))) {
      return false;
    }
    return true;
  }

  public abstract static class Builder {

    protected OrderType orderType;
    protected BigDecimal tradableAmount;
    protected CurrencyPair currencyPair;
    protected String id;
    protected Date timestamp;

    protected final Set<IOrderFlags> flags = new HashSet<IOrderFlags>();

    protected Builder(OrderType orderType, CurrencyPair currencyPair) {
      this.orderType = orderType;
      this.currencyPair = currencyPair;
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

    public Builder flags(Set<IOrderFlags> flags) {
      this.flags.addAll(flags);
      return this;
    }

    public Builder flag(IOrderFlags flag) {
      this.flags.add(flag);
      return this;
    }
  }
}
