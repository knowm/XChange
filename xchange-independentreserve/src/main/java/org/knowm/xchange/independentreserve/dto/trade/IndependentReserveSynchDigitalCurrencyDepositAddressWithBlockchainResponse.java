package org.knowm.xchange.independentreserve.dto.trade;

import java.text.ParseException;
import java.util.Date;

import org.knowm.xchange.independentreserve.util.Util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IndependentReserveSynchDigitalCurrencyDepositAddressWithBlockchainResponse {
  /**
   * Digital currency deposit address to be updated
   */
  private final String depositAddress;
  /**
   * UTC timestamp of when this address was last checked against Blockchain
   */
  private final Date lastChecked;
  /**
   * UTC timestamp of when this address is scheduled to next be checked against Blockchain
   */
  private final Date nextUpdate;

  public IndependentReserveSynchDigitalCurrencyDepositAddressWithBlockchainResponse(
      @JsonProperty("DepositAddress") String depositAddress
      , @JsonProperty("LastCheckedTimestampUtc") String lastChecked
      , @JsonProperty("NextUpdateTimestampUtc") String nextUpdate
  ) throws com.fasterxml.jackson.databind.exc.InvalidFormatException {
    this.depositAddress = depositAddress;
    this.lastChecked = Util.toDate(lastChecked);
    this.nextUpdate = Util.toDate(nextUpdate);
  }

  public String getDepositAddress() {
    return depositAddress;
  }

  public Date getLastChecked() {
    return lastChecked;
  }

  public Date getNextUpdate() {
    return nextUpdate;
  }
}
