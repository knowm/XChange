package org.knowm.xchange.gatecoin.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class GatecoinDepositAddress {

  private final String addressName;
  private final String currency;
  private final String address;
  private final String createDateTime;
  private final BigDecimal received;

  public GatecoinDepositAddress(
      @JsonProperty("addressName") String addressName,
      @JsonProperty("currency") String currency,
      @JsonProperty("address") String address,
      @JsonProperty("createDateTime") String createDateTime,
      @JsonProperty("received") BigDecimal received) {
    this.addressName = addressName;
    this.currency = currency;
    this.address = address;
    this.createDateTime = createDateTime;
    this.received = received;
  }

  public String getAddressName() {
    return addressName;
  }

  public String getCurrency() {
    return currency;
  }

  public String getAddress() {
    return address;
  }

  public String getCreateDateTime() {
    return createDateTime;
  }

  public BigDecimal getReceived() {
    return received;
  }

  @Override
  public String toString() {

    return "GatecoinDepositAddress [depositAddress=" + address + "]";
  }
}
