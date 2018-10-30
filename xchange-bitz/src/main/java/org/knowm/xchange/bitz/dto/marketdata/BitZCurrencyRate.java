package org.knowm.xchange.bitz.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;

public class BitZCurrencyRate {

  private final String coin;
  private final String currencyCoin;
  private final BigDecimal rate;
  private final String rateTime;
  private final String ratenm;
  private final String flatform;
  private final Date created;
  private final String timezone;

  public BitZCurrencyRate(
      @JsonProperty("coin") String coin,
      @JsonProperty("currencyCoin") String currencyCoin,
      @JsonProperty("rate") BigDecimal rate,
      @JsonProperty("rateTime") String rateTime,
      @JsonProperty("ratenm") String ratenm,
      @JsonProperty("flatform") String flatform,
      @JsonProperty("created") long created,
      @JsonProperty("timezone") String timezone) {
    this.coin = coin;
    this.currencyCoin = currencyCoin;
    this.rate = rate;
    this.rateTime = rateTime;
    this.ratenm = ratenm;
    this.flatform = flatform;
    this.created = new Date(created);
    this.timezone = timezone;
  }

  public String getCoin() {
    return coin;
  }

  public String getCurrencyCoin() {
    return currencyCoin;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public String getRateTime() {
    return rateTime;
  }

  public String getRatenm() {
    return ratenm;
  }

  public String getFlatform() {
    return flatform;
  }

  public Date getCreated() {
    return created;
  }

  public String getTimezone() {
    return timezone;
  }

  @Override
  public String toString() {
    return "BitZCurrenctRate{"
        + "coin='"
        + coin
        + '\''
        + ", currencyCoin='"
        + currencyCoin
        + '\''
        + ", rate="
        + rate
        + ", rateTime='"
        + rateTime
        + '\''
        + ", ratenm='"
        + ratenm
        + '\''
        + ", flatform='"
        + flatform
        + '\''
        + ", created="
        + created
        + ", timezone='"
        + timezone
        + '\''
        + '}';
  }
}
