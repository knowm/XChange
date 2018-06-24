package org.knowm.xchange.bitbay.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.UUID;

/** @author walec51 */
public class BitbayUserTrade {

  private final UUID id;
  private final String market;
  private final long time;
  private final BigDecimal amount;
  private final BigDecimal rate;
  private final String userAction;

  public BitbayUserTrade(
      @JsonProperty("id") UUID id,
      @JsonProperty("market") String market,
      @JsonProperty("time") long time,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("rate") BigDecimal rate,
      @JsonProperty("userAction") String userAction) {
    this.id = id;
    this.market = market;
    this.time = time;
    this.amount = amount;
    this.rate = rate;
    this.userAction = userAction;
  }

  public UUID getId() {
    return id;
  }

  public String getMarket() {
    return market;
  }

  public long getTime() {
    return time;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public String getUserAction() {
    return userAction;
  }
}
