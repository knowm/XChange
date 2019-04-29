package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitSummary {

  @JsonProperty("volume_usd")
  public BigDecimal volumeUsd;

  @JsonProperty("volume")
  public BigDecimal volume;

  @JsonProperty("underlying_price")
  public BigDecimal underlyingPrice;

  @JsonProperty("underlying_index")
  public String underlyingIndex;

  @JsonProperty("quote_currency")
  public String quoteCurrency;

  @JsonProperty("open_interest")
  public BigDecimal openInterest;

  @JsonProperty("mid_price")
  public BigDecimal midPrice;

  @JsonProperty("mark_price")
  public BigDecimal markPrice;

  @JsonProperty("low")
  public BigDecimal low;

  @JsonProperty("last")
  public BigDecimal last;

  @JsonProperty("instrument_name")
  public String instrumentName;

  @JsonProperty("high")
  public BigDecimal high;

  @JsonProperty("funding_8h")
  public BigDecimal funding8h;

  @JsonProperty("estimated_delivery_price")
  public BigDecimal estimatedDeliveryPrice;

  @JsonProperty("current_funding")
  public BigDecimal currentFunding;

  @JsonProperty("creation_timestamp")
  public long creationTimestamp;

  @JsonProperty("bid_price")
  public BigDecimal bidPrice;

  @JsonProperty("base_currency")
  public String baseCurrency;

  @JsonProperty("ask_price")
  public BigDecimal askPrice;

  public BigDecimal getVolumeUsd() {
    return volumeUsd;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getUnderlyingPrice() {
    return underlyingPrice;
  }

  public String getUnderlyingIndex() {
    return underlyingIndex;
  }

  public String getQuoteCurrency() {
    return quoteCurrency;
  }

  public BigDecimal getOpenInterest() {
    return openInterest;
  }

  public BigDecimal getMidPrice() {
    return midPrice;
  }

  public BigDecimal getMarkPrice() {
    return markPrice;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getLast() {
    return last;
  }

  public String getInstrumentName() {
    return instrumentName;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getFunding8h() {
    return funding8h;
  }

  public BigDecimal getEstimatedDeliveryPrice() {
    return estimatedDeliveryPrice;
  }

  public BigDecimal getCurrentFunding() {
    return currentFunding;
  }

  public long getCreationTimestamp() {
    return creationTimestamp;
  }

  public BigDecimal getBidPrice() {
    return bidPrice;
  }

  public String getBaseCurrency() {
    return baseCurrency;
  }

  public BigDecimal getAskPrice() {
    return askPrice;
  }
}
