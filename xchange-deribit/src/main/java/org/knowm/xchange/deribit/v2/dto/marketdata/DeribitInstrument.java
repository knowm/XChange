package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitInstrument {

  @JsonProperty("strike")
  private BigDecimal strike;

  @JsonProperty("tick_size")
  private BigDecimal tickSize;

  @JsonProperty("settlement_period")
  private String settlementPeriod;

  @JsonProperty("quote_currency")
  private String quoteCurrency;

  @JsonProperty("min_trade_amount")
  private BigDecimal minTradeAmount;

  @JsonProperty("kind")
  private String kind;

  @JsonProperty("is_active")
  private boolean isActive;

  @JsonProperty("instrument_name")
  private String instrumentName;

  @JsonProperty("expiration_timestamp")
  private long expirationTimestamp;

  @JsonProperty("creation_timestamp")
  private long creationTimestamp;

  @JsonProperty("contract_size")
  private int contractSize;

  @JsonProperty("base_currency")
  private String baseCurrency;

  public BigDecimal getStrike() {
    return strike;
  }

  public void setStrike(BigDecimal strike) {
    this.strike = strike;
  }

  public BigDecimal getTickSize() {
    return tickSize;
  }

  public void setTickSize(BigDecimal tickSize) {
    this.tickSize = tickSize;
  }

  public String getSettlementPeriod() {
    return settlementPeriod;
  }

  public void setSettlementPeriod(String settlementPeriod) {
    this.settlementPeriod = settlementPeriod;
  }

  public String getQuoteCurrency() {
    return quoteCurrency;
  }

  public void setQuoteCurrency(String quoteCurrency) {
    this.quoteCurrency = quoteCurrency;
  }

  public BigDecimal getMinTradeAmount() {
    return minTradeAmount;
  }

  public void setMinTradeAmount(BigDecimal minTradeAmount) {
    this.minTradeAmount = minTradeAmount;
  }

  public String getKind() {
    return kind;
  }

  public void setKind(String kind) {
    this.kind = kind;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public String getInstrumentName() {
    return instrumentName;
  }

  public void setInstrumentName(String instrumentName) {
    this.instrumentName = instrumentName;
  }

  public long getExpirationTimestamp() {
    return expirationTimestamp;
  }

  public void setExpirationTimestamp(long expirationTimestamp) {
    this.expirationTimestamp = expirationTimestamp;
  }

  public long getCreationTimestamp() {
    return creationTimestamp;
  }

  public void setCreationTimestamp(long creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
  }

  public int getContractSize() {
    return contractSize;
  }

  public void setContractSize(int contractSize) {
    this.contractSize = contractSize;
  }

  public String getBaseCurrency() {
    return baseCurrency;
  }

  public void setBaseCurrency(String baseCurrency) {
    this.baseCurrency = baseCurrency;
  }

  @Override
  public String toString() {
    return "DeribitInstrument{"
        + "strike="
        + strike
        + ", tickSize="
        + tickSize
        + ", settlementPeriod='"
        + settlementPeriod
        + '\''
        + ", quoteCurrency='"
        + quoteCurrency
        + '\''
        + ", minTradeAmount="
        + minTradeAmount
        + ", kind='"
        + kind
        + '\''
        + ", isActive="
        + isActive
        + ", instrumentName='"
        + instrumentName
        + '\''
        + ", expirationTimestamp="
        + expirationTimestamp
        + ", creationTimestamp="
        + creationTimestamp
        + ", contractSize="
        + contractSize
        + ", baseCurrency='"
        + baseCurrency
        + '\''
        + '}';
  }
}
