package org.knowm.xchange.upbit.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpbitOrderResponse {

  private final String uuid;
  private final String side;
  private final String orderType;
  private final BigDecimal price;
  private final BigDecimal avgPrice;
  private final String state;
  private final String market;
  private final String createdAt;
  private final BigDecimal volume;
  private final BigDecimal remainingVolume;
  private final BigDecimal reservedFee;
  private final BigDecimal remainingFee;
  private final BigDecimal paidFee;
  private final BigDecimal locked;
  private final BigDecimal executedVolume;
  private final BigDecimal tradeCount;
  private final UpbitOrderTrade[] trades;

  public UpbitOrderResponse(
      @JsonProperty("uuid") String uuid,
      @JsonProperty("side") String side,
      @JsonProperty("ord_type") String orderType,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("avg_price") BigDecimal avgPrice,
      @JsonProperty("state") String state,
      @JsonProperty("market") String market,
      @JsonProperty("created_at") String createdAt,
      @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("remaining_volume") BigDecimal remainingVolume,
      @JsonProperty("reserved_fee") BigDecimal reservedFee,
      @JsonProperty("remaining_fee") BigDecimal remainingFee,
      @JsonProperty("paid_fee") BigDecimal paidFee,
      @JsonProperty("locked") BigDecimal locked,
      @JsonProperty("executed_volume") BigDecimal executedVolume,
      @JsonProperty("trade_count") BigDecimal tradeCount,
      @JsonProperty("trades") UpbitOrderTrade[] trades) {
    this.uuid = uuid;
    this.side = side;
    this.orderType = orderType;
    this.price = price;
    this.avgPrice = avgPrice;
    this.state = state;
    this.market = market;
    this.createdAt = createdAt;
    this.volume = volume;
    this.remainingVolume = remainingVolume;
    this.reservedFee = reservedFee;
    this.remainingFee = remainingFee;
    this.paidFee = paidFee;
    this.locked = locked;
    this.executedVolume = executedVolume;
    this.tradeCount = tradeCount;
    this.trades = trades;
  }

  public String getUuid() {
    return uuid;
  }

  public String getSide() {
    return side;
  }

  public String getOrderType() {
    return orderType;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAvgPrice() {
    return avgPrice;
  }

  public String getState() {
    return state;
  }

  public String getMarket() {
    return market;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getRemainingVolume() {
    return remainingVolume;
  }

  public BigDecimal getReservedFee() {
    return reservedFee;
  }

  public BigDecimal getRemainingFee() {
    return remainingFee;
  }

  public BigDecimal getPaidFee() {
    return paidFee;
  }

  public BigDecimal getLocked() {
    return locked;
  }

  public BigDecimal getExecutedVolume() {
    return executedVolume;
  }

  public BigDecimal getTradeCount() {
    return tradeCount;
  }

  public UpbitOrderTrade[] getTrades() {
    return trades;
  }
}
