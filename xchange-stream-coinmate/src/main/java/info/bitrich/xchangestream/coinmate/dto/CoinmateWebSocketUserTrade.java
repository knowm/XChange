package info.bitrich.xchangestream.coinmate.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinmateWebSocketUserTrade {

  @JsonProperty("transactionId")
  private final String transactionId;

  @JsonProperty("date")
  private final long timestamp;

  @JsonProperty("amount")
  private final double amount;

  @JsonProperty("price")
  private final double price;

  @JsonProperty("buyOrderId")
  private final String buyOrderId;

  @JsonProperty("sellOrderId")
  private final String sellOrderId;

  @JsonProperty("orderType")
  private final String userOrderType;

  @JsonProperty("type")
  private final String takerOrderType;

  @JsonProperty("fee")
  private final double fee;

  @JsonProperty("tradeFeeType")
  private final String userFeeType;

  @JsonCreator
  public CoinmateWebSocketUserTrade(
      @JsonProperty("transactionId") String transactionId,
      @JsonProperty("date") long timestamp,
      @JsonProperty("price") double price,
      @JsonProperty("amount") double amount,
      @JsonProperty("buyOrderId") String buyOrderId,
      @JsonProperty("sellOrderId") String sellOrderId,
      @JsonProperty("orderType") String userOrderType,
      @JsonProperty("type") String takerOrderType,
      @JsonProperty("fee") double fee,
      @JsonProperty("tradeFeeType") String userFeeType) {
    this.transactionId = transactionId;
    this.timestamp = timestamp;
    this.amount = amount;
    this.price = price;
    this.buyOrderId = buyOrderId;
    this.sellOrderId = sellOrderId;
    this.userOrderType = userOrderType;
    this.takerOrderType = takerOrderType;
    this.fee = fee;
    this.userFeeType = userFeeType;
  }

  public String getTransactionId() {
    return this.transactionId;
  }

  public long getTimestamp() {
    return this.timestamp;
  }

  public double getAmount() {
    return this.amount;
  }

  public double getPrice() {
    return this.price;
  }

  public String getBuyOrderId() {
    return this.buyOrderId;
  }

  public String getSellOrderId() {
    return this.sellOrderId;
  }

  public String getUserOrderType() {
    return this.userOrderType;
  }

  public String getTakerOrderType() {
    return this.takerOrderType;
  }

  public double getFee() {
    return this.fee;
  }

  public String getUserFeeType() {
    return this.userFeeType;
  }

  @Override
  public String toString() {
    return "CoinmateWebSocketUserTrade{"
        + "transactionId='"
        + transactionId
        + '\''
        + ", timestamp="
        + timestamp
        + ", amount="
        + amount
        + ", price="
        + price
        + ", buyOrderId='"
        + buyOrderId
        + '\''
        + ", sellOrderId='"
        + sellOrderId
        + '\''
        + ", userOrderType="
        + userOrderType
        + ", takerOrderType="
        + takerOrderType
        + ", fee="
        + fee
        + ", userFeeType='"
        + userFeeType
        + '\''
        + '}';
  }
}
