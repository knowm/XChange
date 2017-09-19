package org.knowm.xchange.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.knowm.xchange.currency.CurrencyPair;

/**
 * Data object representing an order
 */
public abstract class Order {

  public enum OrderType {

    /**
     * Buying order (the trader is providing the counter currency)
     */
    BID,
    /**
     * Selling order (the trader is providing the base currency)
     */
    ASK,
    /**
     * This is to close a short position when trading crypto currency derivatives such as swaps, futures for CFD's.
     */
    EXIT_ASK,
    /**
     * This is to close a long position when trading crypto currency derivatives such as swaps, futures for CFD's.
     */
    EXIT_BID
  }

  public enum OrderStatus {

    /**
     * Initial order when instantiated
     */
    PENDING_NEW,
    /**
     * Initial order when placed on the order book at exchange
     */
    NEW,
    /**
     * Partially match against opposite order on order book at exchange
     */
    PARTIALLY_FILLED,
    /**
     * Fully match against opposite order on order book at exchange
     */
    FILLED,
    /**
     * Waiting to be removed from order book at exchange
     */
    PENDING_CANCEL,
    /**
     * Removed from order book at exchange
     */
    CANCELED,
    /**
     * Waiting to be replaced by another order on order book at exchange
     */
    PENDING_REPLACE,
    /**
     * Order has been replace by another order on order book at exchange
     */
    REPLACED,
    /**
     * Order has been triggered at stop price
     */
    STOPPED,
    /**
     * Order has been rejected by exchange and not place on order book
     */
    REJECTED,
    /**
     * Order has expired it's time to live or trading session and been removed from order book
     */
    EXPIRED
  }

  public interface IOrderFlags {
  }

  /**
   * Order type i.e. bid or ask
   */
  private final OrderType type;

  /**
   * Status of order during it lifecycle
   */
  private OrderStatus status;

  /**
   * Amount to be ordered / amount that was ordered
   */
  private final BigDecimal originalAmount;

  /**
   * Amount to be ordered / amount that has been matched against order on the order book/filled
   */
  private BigDecimal cumulativeAmount;

  /**
   * Weighted Average price of the fills in the order
   */
  private BigDecimal averagePrice;

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
  private final Set<IOrderFlags> flags = new HashSet<>();

  /**
   * @param type Either BID (buying) or ASK (selling)
   * @param tradableAmount The amount to trade
   * @param currencyPair currencyPair The identifier (e.g. BTC/USD)
   * @param id An id (usually provided by the exchange)
   * @param timestamp the absolute time for this order according to the exchange's server, null if not provided
   */
  public Order(OrderType type, BigDecimal originalAmount, CurrencyPair currencyPair, String id, Date timestamp) {

    this.type = type;
    this.originalAmount = originalAmount;
    this.currencyPair = currencyPair;
    this.id = id;
    this.timestamp = timestamp;
    this.averagePrice = BigDecimal.ZERO;
    this.status = OrderStatus.PENDING_NEW;
    this.cumulativeAmount = BigDecimal.ZERO;
  }

  /**
   * @param type Either BID (buying) or ASK (selling)
   * @param tradableAmount The amount to trade
   * @param currencyPair currencyPair The identifier (e.g. BTC/USD)
   * @param id An id (usually provided by the exchange)
   * @param timestamp the absolute time for this order according to the exchange's server, null if not provided
   * @param averagePrice the averagePrice of fill belonging to the order
   * @param status the status of the order at the exchange
   */
  public Order(OrderType type, BigDecimal originalAmount, CurrencyPair currencyPair, String id, Date timestamp, BigDecimal averagePrice,
      BigDecimal cumulativeAmount, OrderStatus status) {

    this.type = type;
    this.originalAmount = originalAmount;
    this.currencyPair = currencyPair;
    this.id = id;
    this.timestamp = timestamp;
    this.averagePrice = averagePrice;
    this.cumulativeAmount = cumulativeAmount;
    this.status = status;
  }

  /**
   * @return The type (BID or ASK)
   */
  public OrderType getType() {

    return type;
  }

  /**
   * @return The type (PENDING_NEW, NEW, PARTIALLY_FILLED, FILLED, PENDING_CANCEL, CANCELED, PENDING_REPLACE, REPLACED, STOPPED, REJECTED or EXPIRED)
   */
  public OrderStatus getStatus() {

    return status;
  }

  /**
   * @deprecated use getOriginalAmount
   * @return The amount to trade
   */
  public BigDecimal getTradableAmount() {

    return getOriginalAmount();
  }

  /**
   * @return The amount to trade
   */
  public BigDecimal getOriginalAmount() {

    return originalAmount;
  }

  

  /**
   * @return The amount that has been filled
   */
  public BigDecimal getCumulativeAmount() {

    return cumulativeAmount;
  }

  /**
   * @return The average price of the fills in the order
   */
  public BigDecimal getAveragePrice() {

    return averagePrice;
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

  public boolean hasFlag(IOrderFlags flag) {

    return flags.contains(flag);
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

  public void setOrderStatus(OrderStatus status) {

    this.status = status;
  }

  public void setAveragePrice(BigDecimal averagePrice) {

    this.averagePrice = averagePrice;
  }

  public void setCumulativeAmount(BigDecimal cumulativeAmount) {

    this.cumulativeAmount = cumulativeAmount;
  }

  @Override
  public String toString() {

    return "Order [type=" + type + ", originalAmount=" + originalAmount + ", averagePrice=" + averagePrice + ", currencyPair=" + currencyPair
        + ", id=" + id + ", timestamp=" + timestamp + ", status=" + status + "]";
  }

  @Override
  public int hashCode() {

    int hash = 7;
    hash = 83 * hash + (this.type != null ? this.type.hashCode() : 0);
    hash = 83 * hash + (this.originalAmount != null ? this.originalAmount.hashCode() : 0);
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
    if ((this.originalAmount == null) ? (other.originalAmount != null) : this.originalAmount.compareTo(other.originalAmount) != 0) {
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
    protected BigDecimal averagePrice;
    protected OrderStatus status;

    protected final Set<IOrderFlags> flags = new HashSet<>();

    protected Builder(OrderType orderType, CurrencyPair currencyPair) {

      this.orderType = orderType;
      this.currencyPair = currencyPair;
    }

    public Builder orderType(OrderType orderType) {

      this.orderType = orderType;
      return this;
    }

    public Builder orderStatus(OrderStatus status) {

      this.status = status;
      return this;
    }

    public Builder tradableAmount(BigDecimal tradableAmount) {

      this.tradableAmount = tradableAmount;
      return this;
    }

    public Builder averagePrice(BigDecimal averagePrice) {

      this.averagePrice = averagePrice;
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
