package org.knowm.xchange.abucoins.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AbucoinsCryptoDeposit {
  String address;
        
  String tag;
        
  /** Error codes */
  String message;

  public AbucoinsCryptoDeposit(@JsonProperty("address") String address, @JsonProperty("tag") String tag, @JsonProperty("message") String message) {
    this.address = address;
    this.tag = tag;
    this.message = message;
  }
        
  public String getAddress() {
    return address;
  }

  public String getTag() {
    return tag;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "CryptoDeposit [address=" + address + ", tag=" + tag + ", message=" + message + "]";
  }
}
