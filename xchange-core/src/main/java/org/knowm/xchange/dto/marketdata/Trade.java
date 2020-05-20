package org.knowm.xchange.dto.marketdata;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Data object representing a Trade */
@JsonDeserialize(builder = Trade.Builder.class)
public class Trade implements Serializable {

  private static final long serialVersionUID = -4078893146776655648L;

  /** Did this trade result from the execution of a bid or a ask? */
  protected final OrderType type;

  /** Amount that was traded */
  protected final BigDecimal originalAmount;

  /** The currency pair */
  protected final CurrencyPair currencyPair;

  /** The price */
  protected final BigDecimal price;

  /** The timestamp of the trade according to the exchange's server, null if not provided */
  protected final Date timestamp;

  /** The trade id */
  protected final String id;

  protected final String makerOrderId;

  protected final String takerOrderId;

  /**
   * This constructor is called to create a public Trade object in {@link
   * MarketDataService#getTrades(org.knowm.xchange.currency.CurrencyPair, Object...)}
   * implementations) since it's missing the orderId and fee parameters.
   *
   * @param type The trade type (BID side or ASK side)
   * @param originalAmount The depth of this trade
   * @param price The price (either the bid or the ask)
   * @param timestamp The timestamp of the trade according to the exchange's server, null if not
   *     provided
   * @param id The id of the trade
   * @param makerOrderId The orderId of the maker in the trade
   * @param takerOrderId The orderId of the taker in the trade
   */
  public Trade(
      OrderType type,
      BigDecimal originalAmount,
      CurrencyPair currencyPair,
      BigDecimal price,
      Date timestamp,
      String id,
      String makerOrderId,
      String takerOrderId) {

    this.type = type;
    this.originalAmount = originalAmount;
    this.currencyPair = currencyPair;
    this.price = price;
    this.timestamp = timestamp;
    this.id = id;
    this.makerOrderId = makerOrderId;
    this.takerOrderId = takerOrderId;
  }

  public OrderType getType() {

    return type;
  }

  public BigDecimal getOriginalAmount() {

    return originalAmount;
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

  public String getMakerOrderId() {
    return makerOrderId;
  }

  public String getTakerOrderId() {
    return takerOrderId;
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

    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "Trade{"
        + "type="
        + type
        + ", originalAmount="
        + originalAmount
        + ", currencyPair="
        + currencyPair
        + ", price="
        + price
        + ", timestamp="
        + timestamp
        + ", id='"
        + id
        + '\''
        + ", makerOrderId='"
        + makerOrderId
        + '\''
        + ", takerOrderId='"
        + takerOrderId
        + '\''
        + '}';
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {

    protected OrderType type;
    protected BigDecimal originalAmount;
    protected CurrencyPair currencyPair;
    protected BigDecimal price;
    protected Date timestamp;
    protected String id;
    protected String makerOrderId;
    protected String takerOrderId;

    public static Builder from(Trade trade) {
      return new Builder()
          .type(trade.getType())
          .originalAmount(trade.getOriginalAmount())
          .currencyPair(trade.getCurrencyPair())
          .price(trade.getPrice())
          .timestamp(trade.getTimestamp())
          .id(trade.getId());
    }

    public Builder type(OrderType type) {

      this.type = type;
      return this;
    }

    public Builder originalAmount(BigDecimal originalAmount) {

      this.originalAmount = originalAmount;
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

    public Builder makerOrderId(String makerOrderId) {

      this.makerOrderId = makerOrderId;
      return this;
    }

    public Builder takerOrderId(String takerOrderId) {

      this.takerOrderId = takerOrderId;
      return this;
    }

    public Trade build() {

      return new Trade(
          type, originalAmount, currencyPair, price, timestamp, id, makerOrderId, takerOrderId);
    }
  }
}
