package org.knowm.xchange.bitso.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BitsoTrades {

  private boolean success;
  private List<BitsoTrade> payload;

  public BitsoTrades(
      @JsonProperty("success") boolean success, @JsonProperty("payload") List<BitsoTrade> payload) {
    this.success = success;
    this.payload = payload;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public List<BitsoTrade> getPayload() {
    return payload;
  }

  public void setPayload(List<BitsoTrade> payload) {
    this.payload = payload;
  }

  @Override
  public String toString() {
    return "BitsoTrades [success=" + success + ", payload=" + payload + "]";
  }
}
