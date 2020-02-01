package org.knowm.xchange.globitex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.math.BigDecimal;

public class ExecutionReportObject implements Serializable {

  @JsonProperty("orderId")
  private final String orderId;

  @JsonProperty("clientOrderId")
  private final String clientOrderId;

  @JsonProperty("orderStatus")
  private final String orderStatus;

  @JsonProperty("symbol")
  private final String symbol;

  @JsonProperty("side")
  private final String side;

  @JsonProperty("price")
  private final BigDecimal price;

  @JsonProperty("quantity")
  private final BigDecimal quantity;

  @JsonProperty("type")
  private final String type;

  @JsonProperty("timeInForce")
  private final String timeInForce;

  @JsonProperty("lastQuantity")
  private final BigDecimal lastQuantity;

  @JsonProperty("lastPrice")
  private final BigDecimal lastPrice;

  @JsonProperty("leavesQuantity")
  private final BigDecimal leavesQuantity;

  @JsonProperty("cumQuantity")
  private final BigDecimal cumQuantity;

  @JsonProperty("averagePrice")
  private final BigDecimal averagePrice;

  @JsonProperty("created")
  private final long created;

  @JsonProperty("execReportType")
  private final String execReportType;

  @JsonProperty("lastTimestamp")
  private final long lastTimestamp;

  @JsonProperty("account")
  private final String account;

  @JsonProperty("orderSource")
  private final String orderSource;

  public ExecutionReportObject(
      @JsonProperty("orderId") String orderId,
      @JsonProperty("clientOrderId") String clientOrderId,
      @JsonProperty("orderStatus") String orderStatus,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("side") String side,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("type") String type,
      @JsonProperty("timeInForce") String timeInForce,
      @JsonProperty("lastQuantity") BigDecimal lastQuantity,
      @JsonProperty("lastPrice") BigDecimal lastPrice,
      @JsonProperty("leavesQuantity") BigDecimal leavesQuantity,
      @JsonProperty("cumQuantity") BigDecimal cumQuantity,
      @JsonProperty("averagePrice") BigDecimal averagePrice,
      @JsonProperty("created") long created,
      @JsonProperty("execReportType") String execReportType,
      @JsonProperty("lastTimestamp") long lastTimestamp,
      @JsonProperty("account") String account,
      @JsonProperty("orderSource") String orderSource) {
    super();
    this.orderId = orderId;
    this.clientOrderId = clientOrderId;
    this.orderStatus = orderStatus;
    this.symbol = symbol;
    this.side = side;
    this.price = price;
    this.quantity = quantity;
    this.type = type;
    this.timeInForce = timeInForce;
    this.lastQuantity = lastQuantity;
    this.lastPrice = lastPrice;
    this.leavesQuantity = leavesQuantity;
    this.cumQuantity = cumQuantity;
    this.averagePrice = averagePrice;
    this.created = created;
    this.execReportType = execReportType;
    this.lastTimestamp = lastTimestamp;
    this.account = account;
    this.orderSource = orderSource;
  }

  public String getOrderId() {
    return orderId;
  }

  public String getClientOrderId() {
    return clientOrderId;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public String getSymbol() {
    return symbol;
  }

  public String getSide() {
    return side;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public String getType() {
    return type;
  }

  public String getTimeInForce() {
    return timeInForce;
  }

  public BigDecimal getLastQuantity() {
    return lastQuantity;
  }

  public BigDecimal getLastPrice() {
    return lastPrice;
  }

  public BigDecimal getLeavesQuantity() {
    return leavesQuantity;
  }

  public BigDecimal getCumQuantity() {
    return cumQuantity;
  }

  public BigDecimal getAveragePrice() {
    return averagePrice;
  }

  public long getCreated() {
    return created;
  }

  public String getExecReportType() {
    return execReportType;
  }

  public long getLastTimestamp() {
    return lastTimestamp;
  }

  public String getAccount() {
    return account;
  }

  public String getOrderSource() {
    return orderSource;
  }

  @Override
  public String toString() {
    return "ExecutionReportObject{"
        + "orderId='"
        + orderId
        + '\''
        + ", clientOrderId='"
        + clientOrderId
        + '\''
        + ", orderStatus='"
        + orderStatus
        + '\''
        + ", symbol='"
        + symbol
        + '\''
        + ", side='"
        + side
        + '\''
        + ", price='"
        + price
        + '\''
        + ", quantity='"
        + quantity
        + '\''
        + ", type='"
        + type
        + '\''
        + ", timeInForce='"
        + timeInForce
        + '\''
        + ", lastQuantity='"
        + lastQuantity
        + '\''
        + ", lastPrice='"
        + lastPrice
        + '\''
        + ", leavesQuantity='"
        + leavesQuantity
        + '\''
        + ", cumQuantity='"
        + cumQuantity
        + '\''
        + ", averagePrice='"
        + averagePrice
        + '\''
        + ", created="
        + created
        + ", execReportType='"
        + execReportType
        + '\''
        + ", timestamp="
        + lastTimestamp
        + ", account='"
        + account
        + '\''
        + ", orderSource='"
        + orderSource
        + '\''
        + '}';
  }
}
