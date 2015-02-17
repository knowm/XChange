package com.xeiam.xchange.anx.v2.dto;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.currency.CurrencyPair;

public class ANXMetaData {
  public Map<CurrencyPair, ANXMarketMetaData> currencyPairs;
  public BigDecimal tradingFee;

  public Integer maxPrivatePollRatePerSecond;
  public Integer maxPrivatePollRatePer10Second;
  public Integer maxPrivatePollRatePerHour;
  public Integer maxPublicPollRatePerSecond;

  public ANXMetaData(@JsonProperty("currencyPairs") Map<CurrencyPair, ANXMarketMetaData> currencyPairs, BigDecimal tradingFee, @JsonProperty("max_private_poll_rate_per_second") int maxPrivatePollRatePerSecond,
      @JsonProperty("max_private_poll_rate_per_10_second") int maxPrivatePollRatePer10Second, @JsonProperty("max_private_poll_rate_per_hour") int maxPrivatePollRatePerHour,
      @JsonProperty("max_public_poll_rate_per_second") int maxPublicPollRatePerSecond) {
    this.currencyPairs = currencyPairs;
    this.tradingFee = tradingFee;
    this.maxPrivatePollRatePerSecond = maxPrivatePollRatePerSecond;
    this.maxPrivatePollRatePer10Second = maxPrivatePollRatePer10Second;
    this.maxPrivatePollRatePerHour = maxPrivatePollRatePerHour;
    this.maxPublicPollRatePerSecond = maxPublicPollRatePerSecond;
  }

}
