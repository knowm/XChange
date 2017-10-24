package org.knowm.xchange.quadrigacx.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import org.knowm.xchange.quadrigacx.QuadrigaCxUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public final class QuadrigaCxOrder {

  private final String id;
  private final String datetime;
  /**
   * 0 - buy (bid); 1 - sell (ask)
   */
  private final int type;
  private final BigDecimal price;
  private final BigDecimal amount;
  private final String errorMessage;

  /**
   * Constructor
   *
   * @param id
   * @param datetime
   * @param type
   * @param price
   * @param amount
   */
  public QuadrigaCxOrder(@JsonProperty("id") String id, @JsonProperty("datetime") String datetime, @JsonProperty("type") int type,
      @JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("error") @JsonDeserialize(using = QuadrigaCxErrorDeserializer.class) String errorMessage) {

    this.id = id;
    this.datetime = datetime;
    this.type = type;
    this.price = price;
    this.amount = amount;
    this.errorMessage = errorMessage;
  }

  public String getDatetime() {

    return datetime;
  }

  public String getId() {

    return id;
  }

  public int getType() {

    return type;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  @JsonIgnore
  public Date getTime() {

    return QuadrigaCxUtils.parseDate(getDatetime());
  }

  @JsonIgnore
  public String getErrorMessage() {

    return errorMessage;
  }

  @Override
  public String toString() {

    return errorMessage != null ? errorMessage
        : String.format("Order{id=%s, datetime=%s, type=%s, price=%s, amount=%s}", id, datetime, type, price, amount);
  }
}
