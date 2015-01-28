package com.xeiam.xchange.cryptsy.dto.account;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptsy.CryptsyUtils;

/**
 * @author ObsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptsyAccountInfo {

  private final int openOrders;
  private final Date timeStamp;
  private final Map<String, BigDecimal> availableFunds;
  private final Map<String, BigDecimal> availableFundsBTC;

  @JsonCreator
  public CryptsyAccountInfo(@JsonProperty("openordercount") Integer openordercount, @JsonProperty("serverdatetime") String timeStamp,
      @JsonProperty("balances_available") Map<String, BigDecimal> availableFunds,
      @JsonProperty("balances_available_btc") Map<String, BigDecimal> availableFundsBTC) throws ParseException {

    this.openOrders = openordercount;
    this.timeStamp = timeStamp == null ? null : CryptsyUtils.convertDateTime(timeStamp);
    this.availableFunds = availableFunds;
    this.availableFundsBTC = availableFundsBTC;
  }

  public int getOpenOrders() {

    return openOrders;
  }

  public Date getTimeStamp() {

    return timeStamp;
  }

  public Map<String, BigDecimal> getAvailableFunds() {

    return availableFunds;
  }

  public Map<String, BigDecimal> getAvailableFundsBTC() {

    return availableFundsBTC;
  }

  @Override
  public String toString() {

    return "CryptsyAccountInfo[" + "availableFunds='" + availableFunds + "', Available Funds in BTC='" + availableFundsBTC + "',Open Orders='"
        + openOrders + "',TimeStamp='" + timeStamp + "']";
  }
}
