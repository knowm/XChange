package org.knowm.xchange.yacuna.dto.marketdata;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Yingzhe on 12/26/2014.
 */
@JsonIgnoreProperties({ "buyItemCount", "sellItemCount", "buyItemAmount", "sellItemAmount" })
public class YacunaTickerOverallStatistics {

  private final Date timestamp;
  private final YacunaCurrencyAmountPair buyPricePair;
  private final YacunaCurrencyAmountPair sellPricePair;
  private final YacunaCurrencyAmountPair lastPricePair;

  public YacunaTickerOverallStatistics(@JsonProperty("timestamp") Date timestamp, @JsonProperty("buyPrice") YacunaCurrencyAmountPair buyPricePair,
      @JsonProperty("sellPrice") YacunaCurrencyAmountPair sellPricePair, @JsonProperty("lastPrice") YacunaCurrencyAmountPair lastPricePair) {

    this.timestamp = timestamp;
    this.buyPricePair = buyPricePair;
    this.sellPricePair = sellPricePair;
    this.lastPricePair = lastPricePair;
  }

  public Date getTimestamp() {

    return this.timestamp;
  }

  public YacunaCurrencyAmountPair getBuyPricePair() {

    return this.buyPricePair;
  }

  public YacunaCurrencyAmountPair getSellPricePair() {

    return this.sellPricePair;
  }

  public YacunaCurrencyAmountPair getLastPricePair() {

    return this.lastPricePair;
  }

  @Override
  public String toString() {

    return String.format("YacunaTickerOverallStatistics[timestamp: %s, buyPricePair: %s, sellPricePair: %s, lastPricePair: %s]", timestamp,
        buyPricePair, sellPricePair, lastPricePair);
  }
}
