package org.knowm.xchange.coinsetter.dto.pricealert.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A price alert.
 */
public class CoinsetterPriceAlert {

  private final UUID uuid;
  private final UUID customerUuid;
  private final String type;
  private final String condition;
  private final BigDecimal price;
  private final String symbol;
  private final Date createDate;

  /**
   * @param uuid Price Alert UUID
   * @param customerUuid Customer UUID
   * @param type The type of notification the price alert is setup to use ("EMAIL", "TEXT", "BOTH")
   * @param condition The condition to be met that will triger the price alert ("CROSSES", "GREATER", "LESS")
   * @param price Price that alert is set to
   * @param symbol Ticker symbol
   * @param createDate The date the message was created
   */
  public CoinsetterPriceAlert(@JsonProperty("uuid") UUID uuid, @JsonProperty("customerUuid") UUID customerUuid, @JsonProperty("type") String type,
      @JsonProperty("condition") String condition, @JsonProperty("price") BigDecimal price, @JsonProperty("symbol") String symbol,
      @JsonProperty("createDate") @JsonFormat(timezone = "EST") Date createDate) {

    this.uuid = uuid;
    this.customerUuid = customerUuid;
    this.type = type;
    this.condition = condition;
    this.price = price;
    this.symbol = symbol;
    this.createDate = createDate;
  }

  public UUID getUuid() {

    return uuid;
  }

  public UUID getCustomerUuid() {

    return customerUuid;
  }

  public String getType() {

    return type;
  }

  public String getCondition() {

    return condition;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public String getSymbol() {

    return symbol;
  }

  public Date getCreateDate() {

    return createDate;
  }

  @Override
  public String toString() {

    return "CoinsetterPriceAlert [uuid=" + uuid + ", customerUuid=" + customerUuid + ", type=" + type + ", condition=" + condition + ", price="
        + price + ", symbol=" + symbol + ", createDate=" + createDate + "]";
  }

}
