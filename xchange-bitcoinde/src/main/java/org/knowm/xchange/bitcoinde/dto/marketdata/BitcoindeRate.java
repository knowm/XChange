package org.knowm.xchange.bitcoinde.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * @author matthewdowney
 */
public class BitcoindeRate {

  private final String rate_weighted;
  private final String rate_weighted_12h;
  private final String rate_weighted_3h;

  /**
   * Contructor.
   * 
   * @param rate_weighted Usually, the value “rate_weighted” shows the weighted average price of the last 3 hours. If the amount of trades falls below
   *        a critical amount in the last 3 hours, the 12 hour average is returned here.
   * @param rate_weighted_12h Weighted average price of the last 3 hours.
   * @param rate_weighted_3h Weighted average price of the last 12 hours.
   */
  public BitcoindeRate(@JsonProperty("rate_weighted") String rate_weighted, @JsonProperty("rate_weighted_12h") String rate_weighted_12h,
      @JsonProperty("rate_weighted_3h") String rate_weighted_3h) {

    this.rate_weighted = rate_weighted;
    this.rate_weighted_12h = rate_weighted_12h;
    this.rate_weighted_3h = rate_weighted_3h;
  }

  public String getRate_weighted() {

    return this.rate_weighted;
  }

  public String getRate_weighted_12h() {

    return this.rate_weighted_12h;
  }

  public String getRate_weighted_3h() {

    return this.rate_weighted_3h;
  }

  @Override
  public String toString() {

    return "BitcoindeRate{" + "rate_weighted=" + rate_weighted + ", rate_weighted_12h=" + rate_weighted_12h + ", rate_weighted_3h=" + rate_weighted_3h
        + "}";
  }

}
