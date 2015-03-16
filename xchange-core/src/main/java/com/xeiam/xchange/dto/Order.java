package com.xeiam.xchange.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.xeiam.xchange.currency.CurrencyPair;

/**
 * Data object representing an order
 */
public class Order {

  public enum OrderType {

    /**
     * Buying order (you're making an offer)
     */
    BID,
    /**
     * Selling order (you're asking for offers)
     */
    ASK
  }

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
   * The timestamp on the order
   */
  private final Date timestamp;

  /**
   * @param type Either BID (buying) or ASK (selling)
   * @param tradableAmount The amount to trade
   * @param CurrencyPair currencyPair The identifier (e.g. BTC/USD)
   * @param id An id (usually provided by the exchange)
   * @param timestamp the absolute time for this order
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

  @Override
  public String toString() {

    return "Order [type=" + type + ", tradableAmount=" + tradableAmount + ", currencyPair=" + currencyPair + ", id=" + id + ", timestamp="
        + timestamp + "]";
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
}
