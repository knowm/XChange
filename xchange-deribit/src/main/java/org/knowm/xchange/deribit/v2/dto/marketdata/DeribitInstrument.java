package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitInstrument {

  @JsonProperty("kind") private String kind;
  @JsonProperty("baseCurrency") private String baseCurrency;
  @JsonProperty("currency") private String currency;
  @JsonProperty("minTradeSize") private double minTradeSize;
  @JsonProperty("instrumentName") private String instrumentName;
  @JsonProperty("isActive") private Boolean isActive;
  @JsonProperty("settlement") private String settlement;
  @JsonProperty("created") private String created;
  @JsonProperty("tickSize") private double tickSize;
  @JsonProperty("pricePrecision") private int pricePrecision;
  @JsonProperty("expiration") private String expiration;
  @JsonProperty("strike") private int strike;
  @JsonProperty("optionType") private String optionType;
  @JsonProperty("contractSize") private int contractSize;


  public String getKind() {
    return kind;
  }

  public String getBaseCurrency() {
    return baseCurrency;
  }

  public String getCurrency() {
    return currency;
  }

  public double getMinTradeSize() {
    return minTradeSize;
  }

  public String getInstrumentName() {
    return instrumentName;
  }

  public Boolean isActive() {
    return isActive;
  }

  public String getSettlement() {
    return settlement;
  }

  public String getCreated() {
    return created;
  }

  public double getTickSize() {
    return tickSize;
  }

  public int getPricePrecision() {
    return pricePrecision;
  }

  public String getExpiration() {
    return expiration;
  }

  public int getStrike() {
    return strike;
  }

  public String getOptionType() {
    return optionType;
  }

  public int getContractSize() {
    return contractSize;
  }
}
