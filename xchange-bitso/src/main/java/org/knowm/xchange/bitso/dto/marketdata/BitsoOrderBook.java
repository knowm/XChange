package org.knowm.xchange.bitso.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.bitso.dto.trade.BitsoErrorDeserializer;

/** @author Ravi Pandit */
public class BitsoOrderBook {

  private final boolean success;
  public final OrderBookPayload payload;
  private String error;

  /**
   * Constructor
   *
   * @param success
   * @param payload
   * @param error
   */
  public BitsoOrderBook(
      @JsonProperty("success") boolean success,
      @JsonProperty("payload") OrderBookPayload payload,
      @JsonProperty("error") @JsonDeserialize(using = BitsoErrorDeserializer.class)
          String errorMessage) {
    this.success = success;
    this.payload = payload;
    this.error = errorMessage;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public boolean isSuccess() {
    return success;
  }

  public OrderBookPayload getPayload() {
    return payload;
  }

  @Override
  public String toString() {
    return "BitsoOrderBook [success=" + success + ", payload=" + payload + ", error=" + error + "]";
  }
}
