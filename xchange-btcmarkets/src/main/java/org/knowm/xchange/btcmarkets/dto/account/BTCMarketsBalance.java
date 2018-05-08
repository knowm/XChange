package org.knowm.xchange.btcmarkets.dto.account;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import org.knowm.xchange.utils.jackson.BtcToSatoshi;
import org.knowm.xchange.utils.jackson.SatoshiToBtc;

/** @author Matija Mazi */
public class BTCMarketsBalance {

  @JsonSerialize(using = BtcToSatoshi.class)
  @JsonDeserialize(using = SatoshiToBtc.class)
  private BigDecimal pendingFunds;

  @JsonSerialize(using = BtcToSatoshi.class)
  @JsonDeserialize(using = SatoshiToBtc.class)
  private BigDecimal balance;

  private String currency;

  public BigDecimal getAvailable() {
    return pendingFunds != null && balance != null ? balance.subtract(pendingFunds) : null;
  }

  public BigDecimal getPendingFunds() {
    return pendingFunds;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public String getCurrency() {
    return currency;
  }

  @Override
  public String toString() {
    return String.format(
        "BTCMarketsBalance{pendingFunds=%s, balance=%s, currency='%s'}",
        pendingFunds, balance, currency);
  }
}
