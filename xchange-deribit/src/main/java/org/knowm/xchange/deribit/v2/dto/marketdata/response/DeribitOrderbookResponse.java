package org.knowm.xchange.deribit.v2.dto.marketdata.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.deribit.v2.dto.DeribitResponse;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitOrderbook;

public class DeribitOrderbookResponse extends DeribitResponse<DeribitOrderbook> {

  public DeribitOrderbookResponse(
          @JsonProperty("success") boolean success,
          @JsonProperty("error") int error,
          @JsonProperty("testnet") boolean testnet,
          @JsonProperty("message") String message,
          @JsonProperty("usOut") long usOut,
          @JsonProperty("usIn") long usIn,
          @JsonProperty("usDiff") long usDiff,
          @JsonProperty("result") DeribitOrderbook result) {

    super(success, error, testnet, message, usOut, usIn, usDiff, result);
  }
}
