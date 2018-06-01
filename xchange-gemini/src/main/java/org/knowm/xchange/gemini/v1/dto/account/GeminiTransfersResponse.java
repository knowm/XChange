package org.knowm.xchange.gemini.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.ArrayList;

public class GeminiTransfersResponse extends ArrayList<GeminiTransfersResponse.GeminiTransfer> {

  public static class GeminiTransfer {
    public @JsonProperty("type") String type;
    public @JsonProperty("status") String status;
    public @JsonProperty("timestampms") Long timestamp;
    public @JsonProperty("eid") String eid;
    public @JsonProperty("currency") String currency;
    public @JsonProperty("amount") BigDecimal amount;
    public @JsonProperty("method") BigDecimal method;
    public @JsonProperty("txHash") String txnHash;
    public @JsonProperty("outputIdx") Long outputIdx;
    public @JsonProperty("destination") String destination;
    public @JsonProperty("purpose") String purpose;

    @Override
    public String toString() {
      return "GeminiTransfer{"
          + "type='"
          + type
          + '\''
          + ", status='"
          + status
          + '\''
          + ", timestamp="
          + timestamp
          + ", eid='"
          + eid
          + '\''
          + ", currency='"
          + currency
          + '\''
          + ", amount="
          + amount
          + ", txnHash='"
          + txnHash
          + '\''
          + ", destination='"
          + destination
          + '\''
          + '}';
    }
  }
}
