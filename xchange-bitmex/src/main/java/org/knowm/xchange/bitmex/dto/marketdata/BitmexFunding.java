package org.knowm.xchange.bitmex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"timestamp", "symbol", "fundingInterval", "fundingRate", "fundingRateDaily"})
public class BitmexFunding {

  @JsonProperty("timestamp")
  private Date timestamp;

  @JsonProperty("symbol")
  private String symbol;

  @JsonProperty("fundingInterval")
  private Date fundingInterval;

  @JsonProperty("fundingRate")
  private BigDecimal fundingRate;

  @JsonProperty("fundingRateDaily")
  private BigDecimal fundingRateDaily;

  public Date getTimestamp() {
    return timestamp;
  }

  public String getSymbol() {
    return symbol;
  }

  public Date getFundingInterval() {
    return fundingInterval;
  }

  public BigDecimal getFundingRate() {
    return fundingRate;
  }

  public BigDecimal getFundingRateDaily() {
    return fundingRateDaily;
  }

  @Override
  public String toString() {
    return "BitmexFunding{"
        + "timestamp='"
        + timestamp
        + '\''
        + ", symbol='"
        + symbol
        + '\''
        + ", fundingInterval='"
        + fundingInterval
        + '\''
        + ", fundingRate='"
        + fundingRate
        + '\''
        + ", fundingRateDaily='"
        + fundingRateDaily
        + '}';
  }
}
