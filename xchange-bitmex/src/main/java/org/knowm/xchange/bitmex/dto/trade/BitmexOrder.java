package org.knowm.xchange.bitmex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public class BitmexOrder {

  private final String refId;
  private final String userRefId;
  private final BitmexOrderStatus status;
  private final double openTimestamp;
  private final double startTimestamp;
  private final double expireTimestamp;
  private final BitmexOrderDescription orderDescription;
  private final BigDecimal volume;
  private final BigDecimal volumeExecuted;
  private final BigDecimal cost;
  private final BigDecimal fee;
  private final BigDecimal price;
  private final BigDecimal stopPrice;
  private final BigDecimal limitPrice;
  private final String miscellaneous;
  private final List<String> orderFlags;
  private final List<String> tradeIds;
  private final double closeTimestamp;
  private final String closeReason;

  /**
   * Constructor
   *
   * @param refId
   * @param userRefId
   * @param status
   * @param openTimestamp
   * @param startTimestamp
   * @param expireTimestamp
   * @param orderDescription
   * @param volume
   * @param volumeExecuted
   * @param cost
   * @param fee
   * @param price
   * @param stopPrice
   * @param limitPrice
   * @param misc
   * @param orderFLags
   * @param tradeIds
   * @param closeTimestamp
   * @param closeReason
   */
  public BitmexOrder(
      @JsonProperty("refid") String refId,
      @JsonProperty("userref") String userRefId,
      @JsonProperty("status") BitmexOrderStatus status,
      @JsonProperty("opentm") double openTimestamp,
      @JsonProperty("starttm") double startTimestamp,
      @JsonProperty("expiretm") double expireTimestamp,
      @JsonProperty("descr") BitmexOrderDescription orderDescription,
      @JsonProperty("vol") BigDecimal volume,
      @JsonProperty("vol_exec") BigDecimal volumeExecuted,
      @JsonProperty("cost") BigDecimal cost,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("stopprice") BigDecimal stopPrice,
      @JsonProperty("limitprice") BigDecimal limitPrice,
      @JsonProperty("misc") String misc,
      @JsonProperty("oflags") List<String> orderFLags,
      @JsonProperty("trades") List<String> tradeIds,
      @JsonProperty("closetm") double closeTimestamp,
      @JsonProperty("reason") String closeReason) {

    this.refId = refId;
    this.userRefId = userRefId;
    this.status = status;
    this.openTimestamp = openTimestamp;
    this.startTimestamp = startTimestamp;
    this.expireTimestamp = expireTimestamp;
    this.orderDescription = orderDescription;
    this.volume = volume;
    this.volumeExecuted = volumeExecuted;
    this.cost = cost;
    this.fee = fee;
    this.price = price;
    this.stopPrice = stopPrice;
    this.limitPrice = limitPrice;
    this.miscellaneous = misc;
    this.orderFlags = orderFLags;
    this.tradeIds = tradeIds;
    this.closeTimestamp = closeTimestamp;
    this.closeReason = closeReason;
  }

  public BitmexOrderDescription getOrderDescription() {

    return orderDescription;
  }

  public String getRefId() {

    return refId;
  }

  public String getUserRefId() {

    return userRefId;
  }

  public BitmexOrderStatus getStatus() {

    return status;
  }

  public double getOpenTimestamp() {

    return openTimestamp;
  }

  public double getStartTimestamp() {

    return startTimestamp;
  }

  public double getExpireTimestamp() {

    return expireTimestamp;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public BigDecimal getVolumeExecuted() {

    return volumeExecuted;
  }

  public BigDecimal getCost() {

    return cost;
  }

  public BigDecimal getFee() {

    return fee;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getStopPrice() {

    return stopPrice;
  }

  public BigDecimal getLimitPrice() {

    return limitPrice;
  }

  public String getMiscellaneous() {

    return miscellaneous;
  }

  public List<String> getOrderFlags() {

    return orderFlags;
  }

  public List<String> getTradeIds() {

    return tradeIds;
  }

  public double getCloseTimestamp() {

    return closeTimestamp;
  }

  public String getCloseReason() {

    return closeReason;
  }

  @Override
  public String toString() {

    return "BitmexOrder [refId="
        + refId
        + ", userRefId="
        + userRefId
        + ", status="
        + status
        + ", openTimestamp="
        + openTimestamp
        + ", startTimestamp="
        + startTimestamp
        + ", expireTimestamp="
        + expireTimestamp
        + ", orderDescription="
        + orderDescription
        + ", volume="
        + volume
        + ", volumeExecuted="
        + volumeExecuted
        + ", cost="
        + cost
        + ", fee="
        + fee
        + ", price="
        + price
        + ", stopPrice="
        + stopPrice
        + ", limitPrice="
        + limitPrice
        + ", miscellaneous="
        + miscellaneous
        + ", orderFlags="
        + orderFlags
        + ", tradeIds="
        + tradeIds
        + ", closeTimestamp="
        + closeTimestamp
        + ", closeReason="
        + closeReason
        + "]";
  }
}
