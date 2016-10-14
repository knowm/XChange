package org.knowm.xchange.cexio.dto.account;

import java.text.MessageFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: brox Since: 2/7/14
 */
public class CexIOBalanceInfo {

  private final String error;
  private final long timestamp;
  private final String username;
  private final CexIOBalance balanceBTC;
  private final CexIOBalance balanceLTC;
  private final CexIOBalance balanceNMC;
  private final CexIOBalance balanceIXC;
  private final CexIOBalance balanceDVC;
  private final CexIOBalance balanceGHS;
  private final CexIOBalance balanceUSD;
  private final CexIOBalance balanceDRK;
  private final CexIOBalance balanceEUR;

  private final CexIOBalance balanceDOGE;
  private final CexIOBalance balanceFTC;
  private final CexIOBalance balanceAUR;
  private final CexIOBalance balancePOT;
  private final CexIOBalance balanceANC;
  private final CexIOBalance balanceMEC;
  private final CexIOBalance balanceWDC;
  private final CexIOBalance balanceDGB;
  private final CexIOBalance balanceUSDE;
  private final CexIOBalance balanceMYR;
  private final CexIOBalance balanceETH;

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
   * @param balanceUSD
   * @param balanceDRK
   * @param balanceEUR
   * @param balanceETH
   */
  public CexIOBalanceInfo(@JsonProperty("error") String error, @JsonProperty("timestamp") long timestamp, @JsonProperty("username") String username,
      @JsonProperty("BTC") CexIOBalance balanceBTC, @JsonProperty("LTC") CexIOBalance balanceLTC, @JsonProperty("NMC") CexIOBalance balanceNMC,
      @JsonProperty("IXC") CexIOBalance balanceIXC, @JsonProperty("DVC") CexIOBalance balanceDVC, @JsonProperty("GHS") CexIOBalance balanceGHS,
      @JsonProperty("DRK") CexIOBalance balanceDRK, @JsonProperty("USD") CexIOBalance balanceUSD, @JsonProperty("EUR") CexIOBalance balanceEUR,
      @JsonProperty("DOGE") CexIOBalance balanceDOGE, @JsonProperty("FTC") CexIOBalance balanceFTC, @JsonProperty("AUR") CexIOBalance balanceAUR,
      @JsonProperty("POT") CexIOBalance balancePOT, @JsonProperty("ANC") CexIOBalance balanceANC, @JsonProperty("MEC") CexIOBalance balanceMEC,
      @JsonProperty("WDC") CexIOBalance balanceWDC, @JsonProperty("DGB") CexIOBalance balanceDGB, @JsonProperty("USDE") CexIOBalance balanceUSDE,
      @JsonProperty("MYR") CexIOBalance balanceMYR, @JsonProperty("ETH") CexIOBalance balanceETH) {

    this.error = error;
    this.timestamp = timestamp;
    this.username = username;
    this.balanceBTC = balanceBTC;
    this.balanceLTC = balanceLTC;
    this.balanceNMC = balanceNMC;
    this.balanceIXC = balanceIXC;
    this.balanceDVC = balanceDVC;
    this.balanceGHS = balanceGHS;
    this.balanceUSD = balanceUSD;
    this.balanceDRK = balanceDRK;
    this.balanceEUR = balanceEUR;
    this.balanceDOGE = balanceDOGE;
    this.balanceFTC = balanceFTC;
    this.balanceAUR = balanceAUR;
    this.balancePOT = balancePOT;
    this.balanceANC = balanceANC;
    this.balanceMEC = balanceMEC;
    this.balanceWDC = balanceWDC;
    this.balanceDGB = balanceDGB;
    this.balanceUSDE = balanceUSDE;
    this.balanceMYR = balanceMYR;
    this.balanceETH = balanceETH;
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

  public CexIOBalance getBalanceLTC() {

    return balanceLTC;
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

  public CexIOBalance getBalanceUSD() {

    return balanceUSD;
  }

  public CexIOBalance getBalanceDRK() {

    return balanceDRK;
  }

  public CexIOBalance getBalanceEUR() {

    return balanceEUR;
  }

  public CexIOBalance getBalanceDOGE() {

    return balanceDOGE;
  }

  public CexIOBalance getBalanceFTC() {

    return balanceFTC;
  }

  public CexIOBalance getBalanceAUR() {

    return balanceAUR;
  }

  public CexIOBalance getBalancePOT() {

    return balancePOT;
  }

  public CexIOBalance getBalanceANC() {

    return balanceANC;
  }

  public CexIOBalance getBalanceMEC() {

    return balanceMEC;
  }

  public CexIOBalance getBalanceWDC() {

    return balanceWDC;
  }

  public CexIOBalance getBalanceDGB() {

    return balanceDGB;
  }

  public CexIOBalance getBalanceUSDE() {

    return balanceUSDE;
  }

  public CexIOBalance getBalanceMYR() {

    return balanceMYR;
  }

  public CexIOBalance getBalanceETH() {

    return balanceETH;
  }

  @Override
  public String toString() {

    return MessageFormat.format(
        "CexIOBalanceInfo[error={0}, timestamp={1}, username={2}, BTC={3}, LTC={4}, NMC={5}, IXC={6}, DVC={7}, GHS={8}, USD={9}, DRK={10}, EUR={11} ETH={12}]",
        error, timestamp, username, balanceBTC, balanceLTC, balanceNMC, balanceIXC, balanceDVC, balanceGHS, balanceUSD, balanceDRK, balanceEUR,
        balanceETH);
  }

}
