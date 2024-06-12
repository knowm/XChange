package org.knowm.xchange.ftx.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class FtxFillDto {

  private final Date time;

  private final String id;

  private final String market;

  private final BigDecimal price;

  private final FtxOrderSide side;

  private final BigDecimal size;

  private final String orderId;

  private final String tradeId;

  private final BigDecimal fee;

  private final String feeCurrency;

  private final BigDecimal feeRate;

  @JsonCreator
  public FtxFillDto(
      @JsonProperty("time") Date time,
      @JsonProperty("id") String id,
      @JsonProperty("market") String market,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("side") FtxOrderSide side,
      @JsonProperty("size") BigDecimal size,
      @JsonProperty("orderId") String orderId,
      @JsonProperty("tradeId") String tradeId,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("feeCurrency") String feeCurrency,
      @JsonProperty("feeRate") BigDecimal feeRate) {
    this.time = time;
    this.id = id;
    this.market = market;
    this.price = price;
    this.side = side;
    this.size = size;
    this.orderId = orderId;
    this.tradeId = tradeId;
    this.fee = fee;
    this.feeCurrency = feeCurrency;
    this.feeRate = feeRate;
  }

  public Date getTime() {
    return time;
  }

  public String getId() {
    return id;
  }

  public String getMarket() {
    return market;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public FtxOrderSide getSide() {
    return side;
  }

  public BigDecimal getSize() {
    return size;
  }

  public String getOrderId() {
    return orderId;
  }

  public String getTradeId() {
    return tradeId;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public String getFeeCurrency() {
    return feeCurrency;
  }

  public BigDecimal getFeeRate() {
    return feeRate;
  }

  @Override
  public String toString() {
    return "FtxFillDto{"
        + "time="
        + time
        + ", id='"
        + id
        + '\''
        + ", market='"
        + market
        + '\''
        + ", price="
        + price
        + ", side="
        + side
        + ", size="
        + size
        + ", orderId='"
        + orderId
        + '\''
        + ", tradeId='"
        + tradeId
        + '\''
        + ", fee="
        + fee
        + ", feeCurrency='"
        + feeCurrency
        + '\''
        + ", feeRate="
        + feeRate
        + '}';
  }
}
