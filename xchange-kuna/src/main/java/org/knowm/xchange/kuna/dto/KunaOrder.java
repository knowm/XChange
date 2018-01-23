package org.knowm.xchange.kuna.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.knowm.xchange.kuna.dto.enums.KunaOrderType;
import org.knowm.xchange.kuna.dto.enums.KunaSide;
import org.knowm.xchange.kuna.util.KunaUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Order of crypto currency.
 * Instances of this type are immutable, constructed with a dedicated Builder implementation.
 *
 * @author Dat Bui
 */
@JsonDeserialize(builder = KunaOrder.Builder.class)
public class KunaOrder {

  public static final String ORD_TYPE = "ord_type";
  public static final String AVG_PRICE = "avg_price";
  public static final String CREATED_AT = "created_at";
  public static final String REMAINING_VOLUME = "remaining_volume";
  public static final String EXECUTED_VOLUME = "executed_volume";
  public static final String TRADES_COUNT = "trades_count";

  private int id;
  private KunaSide side;
  private KunaOrderType orderType;
  private BigDecimal price;
  private BigDecimal averagePrice;
  private String state;
  private String market;
  private Date createdAt;
  private BigDecimal volume;
  private BigDecimal remainingVolume;
  private BigDecimal executedVolume;
  private int tradesCount;

  /**
   * Hide default constructor.
   */
  private KunaOrder() {
  }

  /**
   * Returns order id.
   *
   * @return order id
   */
  public int getId() {
    return id;
  }

  /**
   * Returns always "sell".
   *
   * @return order side
   */
  public KunaSide getSide() {
    return side;
  }

  /**
   * Returns order type — limit or market.
   *
   * @return order type
   */
  @JsonProperty(ORD_TYPE)
  public KunaOrderType getOrderType() {
    return orderType;
  }

  /**
   * Price price for 1 unit of a crypto currency.
   *
   * @return price
   */
  public BigDecimal getPrice() {
    return price;
  }

  /**
   * Returns the average trade price for the order.
   *
   * @return average price
   */
  @JsonProperty(AVG_PRICE)
  public BigDecimal getAveragePrice() {
    return averagePrice;
  }

  /**
   * Returns order state — always wait.
   *
   * @return order state
   */
  public String getState() {
    return state;
  }

  /**
   * Retruns market ID.
   *
   * @return market ID
   */
  public String getMarket() {
    return market;
  }

  /**
   * Retruns the time of placing the order.
   *
   * @return order place time
   */
  @JsonProperty(CREATED_AT)
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * Retuns volume of trading in crypto currency.
   *
   * @return trading volume
   */
  public BigDecimal getVolume() {
    return volume;
  }

  /**
   * Returns unfilled amount of crypto currency.
   *
   * @return unfilled amount
   */
  @JsonProperty(REMAINING_VOLUME)
  public BigDecimal getRemainingVolume() {
    return remainingVolume;
  }

  /**
   * Returns sold amount of crypto currency.
   *
   * @return sold amount
   */
  @JsonProperty(EXECUTED_VOLUME)
  public BigDecimal getExecutedVolume() {
    return executedVolume;
  }

  @JsonProperty(TRADES_COUNT)
  public int getTradesCount() {
    return tradesCount;
  }

  /**
   * Creates new builder.
   *
   * @return builder
   */
  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private KunaOrder target = new KunaOrder();

    public Builder withId(int id) {
      target.id = id;
      return this;
    }

    public Builder withSide(String side) {
      target.side = KunaSide.valueOfIgnoreCase(side);
      return this;
    }

    @JsonProperty(ORD_TYPE)
    public Builder withOrderType(String orderType) {
      target.orderType = KunaOrderType.valueOfIgnoreCase(orderType);
      return this;
    }

    public Builder withPrice(BigDecimal price) {
      target.price = price;
      return this;
    }

    @JsonProperty(AVG_PRICE)
    public Builder withAveragePrice(BigDecimal averagePrice) {
      target.averagePrice = averagePrice;
      return this;
    }

    public Builder withState(String state) {
      target.state = state;
      return this;
    }

    public Builder withMarket(String market) {
      target.market = market;
      return this;
    }

    @JsonProperty(CREATED_AT)
    public Builder withCreatedAt(String createdAt) {
      if (createdAt == null || createdAt.isEmpty()) {
        target.createdAt = null;
      } else {
        target.createdAt = KunaUtils.toDate(createdAt);
      }
      return this;
    }

    public Builder withVolume(BigDecimal volume) {
      target.volume = volume;
      return this;
    }

    @JsonProperty(REMAINING_VOLUME)
    public Builder withRemainingVolume(BigDecimal remainingVolume) {
      target.remainingVolume = remainingVolume;
      return this;
    }

    @JsonProperty(EXECUTED_VOLUME)
    public Builder withExecutedVolume(BigDecimal executedVolume) {
      target.executedVolume = executedVolume;
      return this;
    }

    @JsonProperty(TRADES_COUNT)
    public Builder withTradesCount(int tradesCount) {
      target.tradesCount = tradesCount;
      return this;
    }

    public KunaOrder build() {
      return this.target;
    }
  }
}
