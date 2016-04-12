package org.knowm.xchange.cryptsy.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptsyPublicOrderbook {

  private final int marketId;
  private final String label;
  private final String priCurrCode;
  private final String priCurrName;
  private final String secCurrCode;
  private final String secCurrName;
  private final List<CryptsyPublicOrder> buyOrders;
  private final List<CryptsyPublicOrder> sellOrders;

  /**
   * Constructor
   */
  @JsonCreator
  public CryptsyPublicOrderbook(@JsonProperty("marketid") int marketId, @JsonProperty("label") String label,
      @JsonProperty("primarycode") String priCurrCode, @JsonProperty("primaryname") String priCurrName,
      @JsonProperty("secondarycode") String secCurrCode, @JsonProperty("secondaryname") String secCurrName,
      @JsonProperty("buyorders") List<CryptsyPublicOrder> buyOrders, @JsonProperty("sellorders") List<CryptsyPublicOrder> sellOrders) {

    this.marketId = marketId;
    this.label = label;
    this.priCurrCode = priCurrCode;
    this.priCurrName = priCurrName;
    this.secCurrCode = secCurrCode;
    this.secCurrName = secCurrName;
    this.buyOrders = buyOrders;
    this.sellOrders = sellOrders;
  }

  public int getMarketId() {

    return marketId;
  }

  public String getLabel() {

    return label;
  }

  public String getPriCurrCode() {

    return priCurrCode;
  }

  public String getPriCurrName() {

    return priCurrName;
  }

  public String getSecCurrCode() {

    return secCurrCode;
  }

  public String getSecCurrName() {

    return secCurrName;
  }

  public List<CryptsyPublicOrder> getBuyOrders() {

    return buyOrders;
  }

  public List<CryptsyPublicOrder> getSellOrders() {

    return sellOrders;
  }

  @Override
  public String toString() {

    return "CryptsyPublicOrderbook [marketId=" + marketId + ", label=" + label + ", priCurrCode=" + priCurrCode + ", priCurrName=" + priCurrName
        + ", secCurrCode=" + secCurrCode + ", secCurrName=" + secCurrName + ", buyOrders=" + buyOrders + ", sellOrders=" + sellOrders + "]";
  }

}
