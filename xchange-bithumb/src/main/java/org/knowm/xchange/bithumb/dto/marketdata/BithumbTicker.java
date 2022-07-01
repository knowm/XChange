package org.knowm.xchange.bithumb.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class BithumbTicker {

  private final BigDecimal openingPrice;
  private final BigDecimal closingPrice;
  private final BigDecimal minPrice;
  private final BigDecimal maxPrice;
  private final BigDecimal averagePrice;
  private final BigDecimal unitsTraded;
  private final BigDecimal accTradeValue;
  private final BigDecimal volume1day;
  private final BigDecimal volume7day;
  private final BigDecimal buyPrice;
  private final BigDecimal sellPrice;
  private final BigDecimal _24HFluctate;
  private final BigDecimal _24HFluctateRate;
  private long date;

  public BithumbTicker(
      @JsonProperty("opening_price") BigDecimal openingPrice,
      @JsonProperty("closing_price") BigDecimal closingPrice,
      @JsonProperty("min_price") BigDecimal minPrice,
      @JsonProperty("max_price") BigDecimal maxPrice,
      @JsonProperty("average_price") BigDecimal averagePrice,
      @JsonProperty("units_traded") BigDecimal unitsTraded,
      @JsonProperty("acc_trade_value") BigDecimal accTradeValue,
      @JsonProperty("volume_1day") BigDecimal volume1day,
      @JsonProperty("volume_7day") BigDecimal volume7day,
      @JsonProperty("buy_price") BigDecimal buyPrice,
      @JsonProperty("sell_price") BigDecimal sellPrice,
      @JsonProperty("24H_fluctate") BigDecimal hFluctate,
      @JsonProperty("24H_fluctate_rate") BigDecimal hFluctateRate,
      @JsonProperty("date") long date) {
    this.openingPrice = openingPrice;
    this.closingPrice = closingPrice;
    this.minPrice = minPrice;
    this.maxPrice = maxPrice;
    this.averagePrice = averagePrice;
    this.unitsTraded = unitsTraded;
    this.accTradeValue = accTradeValue;
    this.volume1day = volume1day;
    this.volume7day = volume7day;
    this.buyPrice = buyPrice;
    this.sellPrice = sellPrice;
    _24HFluctate = hFluctate;
    _24HFluctateRate = hFluctateRate;
    this.date = date;
  }

  public BigDecimal getOpeningPrice() {
    return openingPrice;
  }

  public BigDecimal getClosingPrice() {
    return closingPrice;
  }

  public BigDecimal getMinPrice() {
    return minPrice;
  }

  public BigDecimal getMaxPrice() {
    return maxPrice;
  }

  public BigDecimal getAveragePrice() {
    return averagePrice;
  }

  public BigDecimal getUnitsTraded() {
    return unitsTraded;
  }

  public BigDecimal getAccTradeValue() {
    return accTradeValue;
  }

  public BigDecimal getVolume1day() {
    return volume1day;
  }

  public BigDecimal getVolume7day() {
    return volume7day;
  }

  public BigDecimal getBuyPrice() {
    return buyPrice;
  }

  public BigDecimal getSellPrice() {
    return sellPrice;
  }

  public BigDecimal get_24HFluctate() {
    return _24HFluctate;
  }

  public BigDecimal get_24HFluctateRate() {
    return _24HFluctateRate;
  }

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return "BithumbTicker{"
        + "openingPrice="
        + openingPrice
        + ", closingPrice="
        + closingPrice
        + ", minPrice="
        + minPrice
        + ", maxPrice="
        + maxPrice
        + ", averagePrice="
        + averagePrice
        + ", unitsTraded="
        + unitsTraded
        + ", accTradeValue="
        + accTradeValue
        + ", volume1day="
        + volume1day
        + ", volume7day="
        + volume7day
        + ", buyPrice="
        + buyPrice
        + ", sellPrice="
        + sellPrice
        + ", _24HFluctate="
        + _24HFluctate
        + ", _24HFluctateRate="
        + _24HFluctateRate
        + ", date="
        + date
        + '}';
  }
}
