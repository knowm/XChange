package org.knowm.xchange.bitflyer.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * A base class for history related responses
 *
 * @author bryant_harris
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BitflyerBaseHistoryResponse {
  @JsonProperty("id")
  String id;

  @JsonProperty("order_id")
  String orderID;

  @JsonProperty("currency_code")
  String currencyCode;

  @JsonProperty("amount")
  BigDecimal amount;

  @JsonProperty("status")
  String status;

  @JsonProperty("event_date")
  String eventDate;

  public String getID() {
    return id;
  }

  public void setID(String id) {
    this.id = id;
  }

  public String getOrderID() {
    return orderID;
  }

  public void setOrderID(String orderID) {
    this.orderID = orderID;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getEventDate() {
    return eventDate;
  }

  public void setEventDate(String eventDate) {
    this.eventDate = eventDate;
  }
}
