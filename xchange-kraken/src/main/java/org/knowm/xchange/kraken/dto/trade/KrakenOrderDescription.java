package org.knowm.xchange.kraken.dto.trade;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenOrderDescription {

  private final String assetPair;
  private final KrakenType type;
  private final KrakenOrderType orderType;
  private final BigDecimal price;
  private final BigDecimal secondaryPrice;
  private final String leverage;
  private final String positionTxId;
  private final String orderDescription;
  private final String closeDescription;

  /**
   * Constructor
   *
   * @param assetPair
   * @param type
   * @param orderType
   * @param price
   * @param secondaryPrice
   * @param leverage
   * @param positionTxId
   * @param orderDescription
   * @param closeDescription
   */
  public KrakenOrderDescription(@JsonProperty("pair") String assetPair, @JsonProperty("type") KrakenType type,
      @JsonProperty("ordertype") KrakenOrderType orderType, @JsonProperty("price") BigDecimal price,
      @JsonProperty("price2") BigDecimal secondaryPrice, @JsonProperty("leverage") String leverage, @JsonProperty("position") String positionTxId,
      @JsonProperty("order") String orderDescription, @JsonProperty("close") String closeDescription) {

    this.assetPair = assetPair;
    this.type = type;
    this.orderType = orderType;
    this.price = price;
    this.secondaryPrice = secondaryPrice;
    this.leverage = leverage;
    this.positionTxId = positionTxId;
    this.orderDescription = orderDescription;
    this.closeDescription = closeDescription;
  }

  public String getAssetPair() {

    return assetPair;
  }

  public KrakenType getType() {

    return type;
  }

  public KrakenOrderType getOrderType() {

    return orderType;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public BigDecimal getSecondaryPrice() {

    return secondaryPrice;
  }

  public String getLeverage() {

    return leverage;
  }

  public String getPositionTxId() {

    return positionTxId;
  }

  public String getOrderDescription() {

    return orderDescription;
  }

  public String getCloseDescription() {

    return closeDescription;
  }

  @Override
  public String toString() {

    return "KrakenOrderDescription [assetPair=" + assetPair + ", type=" + type + ", orderType=" + orderType + ", price=" + price + ", secondaryPrice="
        + secondaryPrice + ", leverage=" + leverage + ", positionTxId=" + positionTxId + ", orderDescription=" + orderDescription
        + ", closeDescription=" + closeDescription + "]";
  }

}