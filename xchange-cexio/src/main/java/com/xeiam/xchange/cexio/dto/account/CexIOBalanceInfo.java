package com.xeiam.xchange.cexio.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.MessageFormat;

/**
 * Author: brox
 * Since:  2/7/14
 */

public class CexIOBalanceInfo {

  private final String error;
  private final long timestamp;
  private final String username;
  private final CexIOBalance balanceBTC;
  private final CexIOBalance balanceNMC;
  private final CexIOBalance balanceIXC;
  private final CexIOBalance balanceDVC;
  private final CexIOBalance balanceGHS;

  /**
   * Constructor
   *
   * @param error
   * @param timestamp The server time (Unix time)
   * @param username
   * @param balanceBTC
   * @param balanceNMC
   * @param balanceIXC
   * @param balanceDVC
   * @param balanceGHS
   */
  public CexIOBalanceInfo(@JsonProperty("error") String error, @JsonProperty("timestamp") long timestamp, @JsonProperty("username") String username,
      @JsonProperty("BTC") CexIOBalance balanceBTC, @JsonProperty("NMC") CexIOBalance balanceNMC,
      @JsonProperty("IXC") CexIOBalance balanceIXC, @JsonProperty("DVC") CexIOBalance balanceDVC,
      @JsonProperty("GHS") CexIOBalance balanceGHS) {

    this.error = error;
    this.timestamp = timestamp;
    this.username = username;
    this.balanceBTC = balanceBTC;
    this.balanceNMC = balanceNMC;
    this.balanceIXC = balanceIXC;
    this.balanceDVC = balanceDVC;
    this.balanceGHS = balanceGHS;
  }

  public String getError() {

    return error;
  }

  public long getTimestamp() {

    return timestamp;
  }

  public String getUsername() {

    return username;
  }

  public CexIOBalance getBalanceBTC() {

    return balanceBTC;
  }

  public CexIOBalance getBalanceNMC() {

    return balanceNMC;
  }

  public CexIOBalance getBalanceIXC() {

    return balanceIXC;
  }

  public CexIOBalance getBalanceDVC() {

    return balanceDVC;
  }

  public CexIOBalance getBalanceGHS() {

    return balanceGHS;
  }

  @Override
  public String toString() {

    return MessageFormat.format("CexIOBalanceInfo[error={0}, timestamp={1}, username={2}, BTC={3}, NMC={4}, IXC={5}, DVC={6}, GHS={7}]", error, timestamp, username, balanceBTC, balanceNMC, balanceIXC, balanceDVC, balanceGHS);
  }

}
