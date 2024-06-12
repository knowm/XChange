package org.knowm.xchange.okex.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.currency.Currency;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OkexAccountPositionRisk {

  private final BigDecimal adjustEquity;
  private final List<BalanceData> balanceData;
  private final List<PositionData> positionData;
  private final Date timestamp;

  public OkexAccountPositionRisk(
      @JsonProperty("adjEq") BigDecimal adjustEquity,
      @JsonProperty("balData") List<BalanceData> balanceData,
      @JsonProperty("posData") List<PositionData> positionData,
      @JsonProperty("ts") Date timestamp) {
    this.adjustEquity = adjustEquity;
    this.balanceData = balanceData;
    this.positionData = positionData;
    this.timestamp = timestamp;
  }

  @Getter
  @ToString
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class BalanceData {
    private final Currency currency;
    private final BigDecimal equityOfCurrency;
    private final BigDecimal discountEquityOfCurrency;

    public BalanceData(
        @JsonProperty("ccy") Currency currency,
        @JsonProperty("eq") BigDecimal equityOfCurrency,
        @JsonProperty("disEq") BigDecimal discountEquityOfCurrency) {
      this.currency = currency;
      this.equityOfCurrency = equityOfCurrency;
      this.discountEquityOfCurrency = discountEquityOfCurrency;
    }
  }

  @Getter
  @ToString
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class PositionData {

    private final String instrumentId;
    private final BigDecimal positionSize;
    private final BigDecimal notionalUsdValue;

    public PositionData(
        @JsonProperty("instId") String instrumentId,
        @JsonProperty("pos") BigDecimal positionSize,
        @JsonProperty("notionalUsd") BigDecimal notionalUsdValue) {
      this.instrumentId = instrumentId;
      this.positionSize = positionSize;
      this.notionalUsdValue = notionalUsdValue;
    }
  }
}
