package org.knowm.xchange.yacuna.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Yingzhe on 12/27/2014.
 */
@JsonIgnoreProperties({ "openPrice", "closePrice", "averagePrice", "volumeC2" })
public class YacunaTickerDailyStatistics {

  private final YacunaCurrencyAmountPair lowPricePair;
  private final YacunaCurrencyAmountPair highPricePair;
  private final YacunaCurrencyAmountPair volumePair;

  public YacunaTickerDailyStatistics(@JsonProperty("lowPrice") YacunaCurrencyAmountPair lowPricePair,
      @JsonProperty("highPrice") YacunaCurrencyAmountPair highPricePair, @JsonProperty("volumeC1") YacunaCurrencyAmountPair volumePair) {

    this.lowPricePair = lowPricePair;
    this.highPricePair = highPricePair;
    this.volumePair = volumePair;
  }

  public YacunaCurrencyAmountPair getLowPricePair() {

    return this.lowPricePair;
  }

  public YacunaCurrencyAmountPair getHighPricePair() {

    return this.highPricePair;
  }

  public YacunaCurrencyAmountPair getVolumePair() {

    return this.volumePair;
  }

  @Override
  public String toString() {

    return String.format("YacunaTickerDailyStatistics[lowPricePair: %s, highPricePair: %s, volumePair: %s]", lowPricePair, highPricePair, volumePair);
  }
}
