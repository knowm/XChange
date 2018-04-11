package org.knowm.xchange.kraken.dto.trade.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.trade.results.KrakenCancelOrderResult.KrakenCancelOrderResponse;

public class KrakenCancelOrderResult extends KrakenResult<KrakenCancelOrderResponse> {

  /**
   * Constructor
   *
   * @param result
   * @param error
   */
  public KrakenCancelOrderResult(
      @JsonProperty("result") KrakenCancelOrderResponse result,
      @JsonProperty("error") String[] error) {

    super(result, error);
  }

  public static class KrakenCancelOrderResponse {

    private final int count;
    private final boolean pending;

    /**
     * Constructor
     *
     * @param count
     * @param pending
     */
    public KrakenCancelOrderResponse(
        @JsonProperty("count") int count, @JsonProperty("pending") boolean pending) {

      this.count = count;
      this.pending = pending;
    }

    public int getCount() {

      return count;
    }

    public boolean isPending() {

      return pending;
    }

    @Override
    public String toString() {

      return "KrakenCancelOrderResponse [count=" + count + ", pending=" + pending + "]";
    }
  }
}
