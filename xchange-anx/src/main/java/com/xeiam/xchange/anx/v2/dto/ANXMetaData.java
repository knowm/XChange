package com.xeiam.xchange.anx.v2.dto;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.currency.CurrencyPair;

public class ANXMetaData {
  @JsonProperty("currencyPairs")
  public Map<CurrencyPair, ANXMarketMetaData> currencyPairs;

  @JsonProperty("currencies")
  public Map<String, ANXCurrencyData> currencies;

  @JsonProperty("makerTradingFee")
  public BigDecimal makerTradingFee;

  @JsonProperty("takerTradingFee")
  public BigDecimal takerTradingFee;

  @JsonProperty("max_private_poll_rate_per_second")
  public Integer maxPrivatePollRatePerSecond;

  @JsonProperty("max_private_poll_rate_per_10_second")
  public Integer maxPrivatePollRatePer10Second;

  @JsonProperty("max_private_poll_rate_per_hour")
  public Integer maxPrivatePollRatePerHour;

  @JsonProperty("max_public_poll_rate_per_second")
  public Integer maxPublicPollRatePerSecond;

  public ANXMetaData() {
  }

  public ANXMetaData(Map<CurrencyPair, ANXMarketMetaData> currencyPairs, Map<String, ANXCurrencyData> currencies, BigDecimal makerTradingFee, BigDecimal takerTradingFee,
      int maxPrivatePollRatePerSecond, int maxPrivatePollRatePer10Second, int maxPrivatePollRatePerHour, int maxPublicPollRatePerSecond) {
    this.currencyPairs = currencyPairs;
    this.currencies = currencies;
    this.makerTradingFee = makerTradingFee;
    this.takerTradingFee = takerTradingFee;
    this.maxPrivatePollRatePerSecond = maxPrivatePollRatePerSecond;
    this.maxPrivatePollRatePer10Second = maxPrivatePollRatePer10Second;
    this.maxPrivatePollRatePerHour = maxPrivatePollRatePerHour;
    this.maxPublicPollRatePerSecond = maxPublicPollRatePerSecond;
  }

}
