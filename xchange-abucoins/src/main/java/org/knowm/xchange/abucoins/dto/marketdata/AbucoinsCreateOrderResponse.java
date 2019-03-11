package org.knowm.xchange.abucoins.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder;

public class AbucoinsCreateOrderResponse {
  /** Product id ex. BTC-USD */
  String productID;

  /**
   * [deprecated] executed value of amount
   *
   * @deprecated
   */
  String used;

  /** Order size */
  BigDecimal size;

  /** Order price */
  BigDecimal price;

  /** Offer id */
  String id;

  /** Offer side (buy or sell) */
  AbucoinsOrder.Side side;

  /** Offer type (limit, market) */
  AbucoinsOrder.Type type;

  /** Offer time in force param (GTC, GTT, IOC, FOK) */
  AbucoinsOrder.TimeInForce timeInForce;

  /** Post only param (true or false) */
  boolean postOnly;

  /** Order time */
  String createdAt;

  /** Realized amount */
  BigDecimal filledSize;

  /** Charged fees */
  BigDecimal fillFees;

  /** Sum of all executed values (amount * price) */
  BigDecimal executedValue;

  /** Order status (pending, open, done, rejected) */
  AbucoinsOrder.Status status;

  /** [optional] Reason of order reject */
  String reason;

  /** True if order is fully executed */
  boolean settled;

  /** True if your order is hide on the orderbook */
  boolean hidden;

  /** Self trading prevention flag ('' or 'co') */
  String stp;

  String message;

  public AbucoinsCreateOrderResponse(
      @JsonProperty("product_id") String productID,
      @JsonProperty("used") String used,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("id") String id,
      @JsonProperty("side") AbucoinsOrder.Side side,
      @JsonProperty("type") AbucoinsOrder.Type type,
      @JsonProperty("time_in_force") AbucoinsOrder.TimeInForce timeInForce,
      @JsonProperty("post_only") boolean postOnly,
      @JsonProperty("created_at") String createdAt,
      @JsonProperty("filled_size") BigDecimal filledSize,
      @JsonProperty("fill_fees") BigDecimal fillFees,
      @JsonProperty("executed_value") BigDecimal executedValue,
      @JsonProperty("status") AbucoinsOrder.Status status,
      @JsonProperty("reason") String reason,
      @JsonProperty("settled") boolean settled,
      @JsonProperty("hidden") boolean hidden,
      @JsonProperty("stp") String stp,
      @JsonProperty("message") String message) {
    this.productID = productID;
    this.used = used;
    this.size = size;
    this.price = price;
    this.id = id;
    this.side = side;
    this.type = type;
    this.timeInForce = timeInForce;
    this.postOnly = postOnly;
    this.createdAt = createdAt;
    this.filledSize = filledSize;
    this.fillFees = fillFees;
    this.executedValue = executedValue;
    this.status = status;
    this.reason = reason;
    this.settled = settled;
    this.hidden = hidden;
    this.stp = stp;
    this.message = message;
  }

  public String getProductID() {
    return productID;
  }

  public String getUsed() {
    return used;
  }

  public BigDecimal getSize() {
    return size;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public String getId() {
    return id;
  }

  public AbucoinsOrder.Side getSide() {
    return side;
  }

  public AbucoinsOrder.Type getType() {
    return type;
  }

  public AbucoinsOrder.TimeInForce getTimeInForce() {
    return timeInForce;
  }

  public boolean isPostOnly() {
    return postOnly;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public BigDecimal getFilledSize() {
    return filledSize;
  }

  public BigDecimal getFillFees() {
    return fillFees;
  }

  public BigDecimal getExecutedValue() {
    return executedValue;
  }

  public AbucoinsOrder.Status getStatus() {
    return status;
  }

  public String getReason() {
    return reason;
  }

  public boolean isSettled() {
    return settled;
  }

  public boolean isHidden() {
    return hidden;
  }

  public String getStp() {
    return stp;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "AbucoinsCreateOrderResponse [productID="
        + productID
        + ", used="
        + used
        + ", size="
        + size
        + ", price="
        + price
        + ", id="
        + id
        + ", side="
        + side
        + ", type="
        + type
        + ", timeInForce="
        + timeInForce
        + ", postOnly="
        + postOnly
        + ", createdAt="
        + createdAt
        + ", filledSize="
        + filledSize
        + ", fillFees="
        + fillFees
        + ", executedValue="
        + executedValue
        + ", status="
        + status
        + ", reason="
        + reason
        + ", settled="
        + settled
        + ", hidden="
        + hidden
        + ", stp="
        + stp
        + ", message="
        + message
        + "]";
  }
}
