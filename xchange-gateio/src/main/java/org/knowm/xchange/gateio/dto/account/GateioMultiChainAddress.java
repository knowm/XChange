package org.knowm.xchange.gateio.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GateioMultiChainAddress {

  private final String chain;
  private final String address;
  private final String paymentId;
  private final String paymentName;
  private final int obtainFailed;

  public GateioMultiChainAddress(
      @JsonProperty("chain") String chain,
      @JsonProperty("address") String address,
      @JsonProperty("payment_id") String paymentId,
      @JsonProperty("payment_name") String paymentName,
      @JsonProperty("obtain_failed") int obtainFailed) {
    this.chain = chain;
    this.address = address;
    this.paymentId = paymentId;
    this.paymentName = paymentName;
    this.obtainFailed = obtainFailed;
  }

  public String getChain() {
    return chain;
  }

  public String getAddress() {
    return address;
  }

  public String getPaymentId() {
    return paymentId;
  }

  public String getPaymentName() {
    return paymentName;
  }

  public int getObtainFailed() {
    return obtainFailed;
  }
}
