package com.xeiam.xchange.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;

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
   * The timestamp of the trade
   */
  protected final Date timestamp;

  /**
   * The trade id
   */
  protected final String id;

  /**
   * Additional exchange specific data that might be of interest
   */
  private final Map<String, Object> additionalData = new HashMap<String, Object>();

  /**
   * This constructor is called to create a public Trade object in
   * {@link com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService#getTrades(com.xeiam.xchange.currency.CurrencyPair, Object...)}
   * implementations) since it's missing the orderId and fee parameters.
   *
   * @param type The trade type (BID side or ASK side)
   * @param tradableAmount The depth of this trade
   * @param tradableIdentifier The exchange identifier (e.g. "BTC/USD")
   * @param transactionCurrency The transaction currency (e.g. USD in BTC/USD)
   * @param price The price (either the bid or the ask)
   * @param timestamp The timestamp of the trade
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

  public Object getAdditionalData(String key) {
    return additionalData.get(key);
  }

  public void putAdditionalData(String key, Object value) {
    additionalData.put(key, value);
  }

  public Map<String, Object> getAdditionalData() {
    return additionalData;
  }

  public Trade setAdditionalData(Map<String, Object> value) {
    additionalData.clear();
    if (value != null) {
      additionalData.putAll(value);
    }
    return this;
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

    private OrderType type;
    private BigDecimal tradableAmount;
    private CurrencyPair currencyPair;
    private BigDecimal price;
    private Date timestamp;
    private String id;
    private Map<String, Object> additionalData;

    public Builder() {

    }

    public static Builder from(Trade trade) {
      return new Builder().type(trade.getType()).tradableAmount(trade.getTradableAmount()).currencyPair(trade.getCurrencyPair())
          .price(trade.getPrice()).timestamp(trade.getTimestamp()).id(trade.getId()).additionalData(trade.getAdditionalData());
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

    public Builder additionalData(Map<String, Object> additionalData) {

      this.additionalData = additionalData;
      return this;
    }

    public Trade build() {

      return new Trade(type, tradableAmount, currencyPair, price, timestamp, id).setAdditionalData(additionalData);
    }
  }
}
