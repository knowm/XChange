package org.knowm.xchange.deribit.v2.dto.marketdata.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.deribit.v2.dto.DeribitError;
import org.knowm.xchange.deribit.v2.dto.DeribitResponse;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitOrderbook;

public class DeribitOrderbookResponse extends DeribitResponse<DeribitOrderbook> {

  public DeribitOrderbookResponse(
          @JsonProperty("id") long id,
          @JsonProperty("result") DeribitOrderbook result,
          @JsonProperty("error") DeribitError error,
          @JsonProperty("testnet") boolean testnet,
          @JsonProperty("usOut") long usOut,
          @JsonProperty("usIn") long usIn,
          @JsonProperty("usDiff") long usDiff) {

    super(id, result, error, testnet, usOut, usIn, usDiff);
  }
}
