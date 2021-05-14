package org.knowm.xchange.bitso.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitsoTicker {

  private boolean success;
  private BitsoTickerPayload payload;

  BitsoTicker(
      @JsonProperty("success") Boolean success,
      @JsonProperty("payload") BitsoTickerPayload payload) {
    this.success = success;
    this.payload = payload;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public BitsoTickerPayload getPayload() {
    return payload;
  }

  public void setPayload(BitsoTickerPayload payload) {
    this.payload = payload;
  }

  @Override
  public String toString() {
    return "BitsoTicker{" + "success=" + success + ", payload=" + payload + '}';
  }
}
