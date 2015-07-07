package com.xeiam.xchange.bitcointoyou.dto.meta;

import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.meta.CurrencyMetaData;
import com.xeiam.xchange.dto.meta.ExchangeMetaData;
import com.xeiam.xchange.dto.meta.MarketMetaData;

public class BitcoinToYouExchangeMetaData extends ExchangeMetaData {

  private int maxPublicPollRatePerDay;

  /**
   * @param currencyPairs                Map of {@link CurrencyPair} -> {@link MarketMetaData}
   * @param maxPublicPollRatePer10Second Maximum number of public request per day.
   * @param maxPublicPollRatePerDay      Maximum number of public request per day.
   */
  public BitcoinToYouExchangeMetaData(@JsonProperty("currencyPair") Map<CurrencyPair, MarketMetaData> currencyPairs,
      @JsonProperty("max_public_poll_rate_per_10_second") Integer maxPublicPollRatePer10Second, @JsonProperty("max_public_poll_rate_per_day") int maxPublicPollRatePerDay) {
    super(currencyPairs, Collections.<String, CurrencyMetaData>emptyMap(), 0);
    this.maxPublicPollRatePerDay = maxPublicPollRatePerDay;
    this.setMaxPublicPollRatePer10Second(maxPublicPollRatePer10Second);
  }

  public int getMaxPublicPollRatePerDay() {
    return maxPublicPollRatePerDay;
  }
}
