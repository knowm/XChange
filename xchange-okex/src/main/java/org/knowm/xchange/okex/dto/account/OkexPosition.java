package org.knowm.xchange.okex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/** https://www.okx.com/docs-v5/en/#rest-api-account-get-positions */
@Getter
@NoArgsConstructor
@ToString
public class OkexPosition {

  @JsonProperty("instType")
  private String instrumentType;

  @JsonProperty("mgnMode")
  private String marginMode;

  @JsonProperty("posId")
  private String positionId;

  @JsonProperty("posSide")
  private String positionSide;

  @JsonProperty("pos")
  private BigDecimal position;

  @JsonProperty("baseBal")
  private String baseCurrencyBalance;

  @JsonProperty("quoteBal")
  private String quoteCurrencyBalance;

  @JsonProperty("posCcy")
  private String positionCurrency;

  @JsonProperty("availPos")
  private String availablePosition;

  @JsonProperty("avgPx")
  private BigDecimal averageOpenPrice;

  @JsonProperty("markPx")
  private BigDecimal markPrice;

  @JsonProperty("upl")
  private BigDecimal unrealizedPnL;

  @JsonProperty("uplRatio")
  private String unrealizedPnLRatio;

  @JsonProperty("instId")
  private String instrumentId;

  @JsonProperty("lever")
  private String leverage;

  @JsonProperty("liqPx")
  private BigDecimal liquidationPrice;

  @JsonProperty("imr")
  private String initialMarginRequirement;

  @JsonProperty("margin")
  private String margin;

  @JsonProperty("mgnRatio")
  private String marginRatio;

  @JsonProperty("mmr")
  private String maintenanceMarginRatio;

  @JsonProperty("liab")
  private String liabilities;

  @JsonProperty("liabCcy")
  private String liabilitiesCurrency;

  @JsonProperty("interest")
  private String interest;

  @JsonProperty("tradeId")
  private String tradeId;

  @JsonProperty("optVal")
  private String optionValue;

  @JsonProperty("notionalUsd")
  private String notionalUsd;

  @JsonProperty("adl")
  private String adl;

  @JsonProperty("ccy")
  private String currency;

  @JsonProperty("last")
  private BigDecimal lastPrice;

  @JsonProperty("cTime")
  private String creationTime;

  @JsonProperty("uTime")
  private String updateTime;
}
