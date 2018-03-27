package org.knowm.xchange.btctrade.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.btctrade.dto.BTCTradeResult;

public class BTCTradeWallet extends BTCTradeResult {

  private final String address;

  public BTCTradeWallet(
      @JsonProperty("result") Boolean result,
      @JsonProperty("message") String message,
      @JsonProperty("address") String address) {

    super(result, message);
    this.address = address;
  }

  public String getAddress() {

    return address;
  }
}
