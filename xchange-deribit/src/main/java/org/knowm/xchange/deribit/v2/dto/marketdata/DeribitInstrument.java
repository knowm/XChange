package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitInstrument {

  @JsonProperty("tick_size") public BigDecimal tickSize;
  @JsonProperty("settlement_period") public String settlementPeriod;
  @JsonProperty("quote_currency") public String quoteCurrency;
  @JsonProperty("min_trade_amount") public int minTradeAmount;
  @JsonProperty("kind") public String kind;
  @JsonProperty("is_active") public boolean isActive;
  @JsonProperty("instrument_name") public String instrumentName;
  @JsonProperty("expiration_timestamp") public long expirationTimestamp;
  @JsonProperty("creation_timestamp") public long creationTimestamp;
  @JsonProperty("contract_size") public int contractSize;
  @JsonProperty("base_currency") public String baseCurrency;


  public BigDecimal getTickSize() {
    return tickSize;
  }

  public String getSettlementPeriod() {
    return settlementPeriod;
  }

  public String getQuoteCurrency() {
    return quoteCurrency;
  }

  public int getMinTradeAmount() {
    return minTradeAmount;
  }

  public String getKind() {
    return kind;
  }

  public boolean isActive() {
    return isActive;
  }

  public String getInstrumentName() {
    return instrumentName;
  }

  public long getExpirationTimestamp() {
    return expirationTimestamp;
  }

  public long getCreationTimestamp() {
    return creationTimestamp;
  }

  public int getContractSize() {
    return contractSize;
  }

  public String getBaseCurrency() {
    return baseCurrency;
  }
}
