package org.knowm.xchange.bitstamp.dto.trade;

import java.math.BigDecimal;
import java.util.Date;

import org.knowm.xchange.bitstamp.BitstampUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import si.mazi.rescu.ExceptionalReturnContentException;

/**
 * @author Matija Mazi
 */
public final class BitstampOrder {

  private int id;
  private Date datetime;
  /**
   * 0 - buy (bid); 1 - sell (ask)
   */
  private int type;
  private BigDecimal price;
  private BigDecimal amount;
  private String errorMessage;

  public BitstampOrder(@JsonProperty("status") String status, @JsonProperty("reason") Object reason, @JsonProperty("id") int id,
      @JsonProperty("datetime") String datetime, @JsonProperty("type") int type, @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("error") @JsonDeserialize(using = BitstampErrorDeserializer.class) String errorMessage) {

    if ("error".equals(status)) {
      throw new ExceptionalReturnContentException(String.valueOf(reason));
    }

    this.id = id;
    this.datetime = BitstampUtils.parseDate(datetime);
    this.type = type;
    this.price = price;
    this.amount = amount;
    this.errorMessage = errorMessage;
  }

  public Date getDatetime() {

    return datetime;
  }

  public int getId() {

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
  public String getErrorMessage() {

    return errorMessage;
  }

  @Override
  public String toString() {

    return errorMessage != null ? errorMessage
        : String.format("Order{id=%s, datetime=%s, type=%s, price=%s, amount=%s}", id, datetime, type, price, amount);
  }
}
