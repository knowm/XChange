package org.knowm.xchange.ftx.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class FtxConvertDto {

  @JsonProperty("baseCoin")
  private final String baseCoin;

  @JsonProperty("cost")
  private final BigDecimal cost;

  @JsonProperty("expired")
  private final boolean expired;

  @JsonProperty("filled")
  private final boolean filled;

  @JsonProperty("fromCoin")
  private final String fromCoin;

  @JsonProperty("id")
  private final int id;

  @JsonProperty("price")
  private final BigDecimal price;

  @JsonProperty("proceeds")
  private final BigDecimal proceeds;

  @JsonProperty("quoteCoin")
  private final String quoteCoin;

  @JsonProperty("side")
  private final String side;

  @JsonProperty("toCoin")
  private final String toCoin;

  @JsonCreator
  public FtxConvertDto(
      @JsonProperty(value = "baseCoin", required = false) String baseCoin,
      @JsonProperty(value = "cost", required = false) BigDecimal cost,
      @JsonProperty(value = "expired", required = false) boolean expired,
      @JsonProperty(value = "filled", required = false) boolean filled,
      @JsonProperty(value = "fromCoin", required = false) String fromCoin,
      @JsonProperty(value = "id", required = false) int id,
      @JsonProperty(value = "price", required = false) BigDecimal price,
      @JsonProperty(value = "proceeds", required = false) BigDecimal proceeds,
      @JsonProperty(value = "quoteCoin", required = false) String quoteCoin,
      @JsonProperty(value = "side", required = false) String side,
      @JsonProperty(value = "toCoin", required = false) String toCoin) {
    this.baseCoin = baseCoin;
    this.cost = cost;
    this.expired = expired;
    this.filled = filled;
    this.fromCoin = fromCoin;
    this.id = id;
    this.price = price;
    this.proceeds = proceeds;
    this.quoteCoin = quoteCoin;
    this.side = side;
    this.toCoin = toCoin;
  }

  public String getBaseCoin() {
    return baseCoin;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public boolean isExpired() {
    return expired;
  }

  public boolean isFilled() {
    return filled;
  }

  public String getFromCoin() {
    return fromCoin;
  }

  public int getId() {
    return id;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getProceeds() {
    return proceeds;
  }

  public String getQuoteCoin() {
    return quoteCoin;
  }

  public String getSide() {
    return side;
  }

  public String getToCoin() {
    return toCoin;
  }

  @Override
  public String toString() {
    return "FtxConvertDto [baseCoin="
        + baseCoin
        + ", cost="
        + cost
        + ", expired="
        + expired
        + ", filled="
        + filled
        + ", fromCoin="
        + fromCoin
        + ", id="
        + id
        + ", price="
        + price
        + ", proceeds="
        + proceeds
        + ", quoteCoin="
        + quoteCoin
        + ", side="
        + side
        + ", toCoin="
        + toCoin
        + "]";
  }
}
