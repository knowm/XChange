package org.knowm.xchange.deribit.v2.dto.marketdata.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.deribit.v2.dto.DeribitError;
import org.knowm.xchange.deribit.v2.dto.DeribitResponse;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitInstrument;

import java.util.List;

public class DeribitInstrumentsResponse extends DeribitResponse<List<DeribitInstrument>> {

  public DeribitInstrumentsResponse(
          @JsonProperty("id") long id,
          @JsonProperty("result") List<DeribitInstrument> result,
          @JsonProperty("error") DeribitError error,
          @JsonProperty("testnet") boolean testnet,
          @JsonProperty("usOut") long usOut,
          @JsonProperty("usIn") long usIn,
          @JsonProperty("usDiff") long usDiff) {

    super(id, result, error, testnet, usOut, usIn, usDiff);
  }
}
