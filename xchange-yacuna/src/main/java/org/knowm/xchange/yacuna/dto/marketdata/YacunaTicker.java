package org.knowm.xchange.yacuna.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Yingzhe on 12/23/2014.
 */
@JsonIgnoreProperties({ "id", "description", "priceGranularity" })
public class YacunaTicker {

  private final String baseCurrency;
  private final String targetCurrency;
  private final YacunaTickerOverallStatistics overallStatistics;
  private final YacunaTickerDailyStatistics dailyStatistics;

  public YacunaTicker(@JsonProperty("currencyCode1") String targetCurrency, @JsonProperty("currencyCode2") String baseCurrency,
      @JsonProperty("marketStatistics") YacunaTickerOverallStatistics overallStatistics,
      @JsonProperty("market24hStatistics") YacunaTickerDailyStatistics dailyStatistics) {

    this.targetCurrency = targetCurrency;
    this.baseCurrency = baseCurrency;
    this.overallStatistics = overallStatistics;
    this.dailyStatistics = dailyStatistics;
  }

  public String getBaseCurrency() {

    return this.baseCurrency;
  }

  public String getTargetCurrency() {

    return this.targetCurrency;
  }

  public YacunaTickerOverallStatistics getOverallStatistics() {

    return this.overallStatistics;
  }

  public YacunaTickerDailyStatistics getDailyStatistics() {

    return this.dailyStatistics;
  }

  @Override
  public String toString() {

    return String.format("YacunaTicker[baseCurrency: %s, targetCurrency: %s, overallStatistics: %s, dailyStatistics: %s]", baseCurrency,
        targetCurrency, overallStatistics, dailyStatistics);
  }
}
