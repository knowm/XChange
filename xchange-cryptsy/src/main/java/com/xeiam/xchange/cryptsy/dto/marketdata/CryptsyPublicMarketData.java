package com.xeiam.xchange.cryptsy.dto.marketdata;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptsy.CryptsyUtils;

public class CryptsyPublicMarketData {

  private final int marketId;
  private final String label;
  private final String priCurrCode;
  private final String priCurrName;
  private final String secCurrCode;
  private final String secCurrName;
  private final BigDecimal volume;
  private final BigDecimal lastTradePrice;
  private final Date lastTradeTime;
  private final List<CryptsyPublicTrade> recentTrades;
  private final List<CryptsyPublicOrder> buyOrders;
  private final List<CryptsyPublicOrder> sellOrders;

  /**
   * Constructor
   * 
   * @throws ParseException
   */
  @JsonCreator
  public CryptsyPublicMarketData(@JsonProperty("marketid") int marketId, @JsonProperty("label") String label, @JsonProperty("primarycode") String priCurrCode,
      @JsonProperty("primaryname") String priCurrName, @JsonProperty("secondarycode") String secCurrCode, @JsonProperty("secondaryname") String secCurrName, @JsonProperty("volume") BigDecimal volume,
      @JsonProperty("lasttradeprice") BigDecimal lastTradePrice, @JsonProperty("lasttradetime") String lastTradeTime, @JsonProperty("recenttrades") List<CryptsyPublicTrade> recentTrades,
      @JsonProperty("buyorders") List<CryptsyPublicOrder> buyOrders, @JsonProperty("sellorders") List<CryptsyPublicOrder> sellOrders) throws ParseException {

    this.marketId = marketId;
    this.label = label;
    this.priCurrCode = priCurrCode;
    this.priCurrName = priCurrName;
    this.secCurrCode = secCurrCode;
    this.secCurrName = secCurrName;
    this.volume = volume;
    this.lastTradePrice = lastTradePrice;
    this.lastTradeTime = lastTradeTime == null ? null : CryptsyUtils.convertDateTime(lastTradeTime);
    this.recentTrades = recentTrades;
    this.buyOrders = buyOrders;
    this.sellOrders = sellOrders;
  }

  public int getMarketId() {

    return marketId;
  }

  public String getLabel() {

    return label;
  }

  public String getPrimaryCurrencyCode() {

    return priCurrCode;
  }

  public String getPriCurrName() {

    return priCurrName;
  }

  public String getSecondaryCurrencyCode() {

    return secCurrCode;
  }

  public String getSecCurrName() {

    return secCurrName;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public BigDecimal getLastTradePrice() {

    return lastTradePrice;
  }

  public Date getLastTradeTime() {

    return lastTradeTime;
  }

  public List<CryptsyPublicTrade> getRecentTrades() {

    return recentTrades;
  }

  public List<CryptsyPublicOrder> getBuyOrders() {

    return buyOrders;
  }

  public List<CryptsyPublicOrder> getSellOrders() {

    return sellOrders;
  }

  @Override
  public String toString() {

    return "CryptsyPublicMarketData [marketId=" + marketId + ", label=" + label + ", priCurrCode=" + priCurrCode + ", priCurrName=" + priCurrName + ", secCurrCode=" + secCurrCode + ", secCurrName="
        + secCurrName + ", volume=" + volume + ", lastTradePrice=" + lastTradePrice + ", lastTradeTime=" + lastTradeTime + ", recentTrades=" + recentTrades + ", buyOrders=" + buyOrders
        + ", sellOrders=" + sellOrders + "]";
  }
}
