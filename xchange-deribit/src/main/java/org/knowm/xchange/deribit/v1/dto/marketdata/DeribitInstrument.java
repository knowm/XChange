package org.knowm.xchange.deribit.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitInstrument {

  @JsonProperty("kind")
  private String kind;

  @JsonProperty("baseCurrency")
  private String baseCurrency;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("minTradeSize")
  private Double minTradeSize;

  @JsonProperty("instrumentName")
  private String instrumentName;

  @JsonProperty("isActive")
  private Boolean isActive;

  @JsonProperty("settlement")
  private String settlement;

  @JsonProperty("created")
  private String created;

  @JsonProperty("tickSize")
  private Double tickSize;

  @JsonProperty("pricePrecision")
  private Integer pricePrecision;

  @JsonProperty("expiration")
  private String expiration;

  @JsonProperty("strike")
  private Integer strike;

  @JsonProperty("optionType")
  private String optionType;

  @JsonProperty("contractSize")
  private int contractSize;


  public String getKind() {
    return kind;
  }

  public String getBaseCurrency() {
    return baseCurrency;
  }

  public String getCurrency() {
    return currency;
  }

  public Double getMinTradeSize() {
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

  public Double getTickSize() {
    return tickSize;
  }

  public Integer getPricePrecision() {
    return pricePrecision;
  }

  public String getExpiration() {
    return expiration;
  }

  public Integer getStrike() {
    return strike;
  }

  public String getOptionType() {
    return optionType;
  }

  public int getContractSize() {
    return contractSize;
  }
}
