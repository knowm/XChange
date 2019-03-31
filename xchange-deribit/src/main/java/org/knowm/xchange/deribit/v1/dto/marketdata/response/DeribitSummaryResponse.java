package org.knowm.xchange.deribit.v1.dto.marketdata.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.deribit.v1.dto.DeribitResponse;
import org.knowm.xchange.deribit.v1.dto.marketdata.DeribitSummary;


public class DeribitSummaryResponse extends DeribitResponse<DeribitSummary> {

    public DeribitSummaryResponse(
            @JsonProperty("success") boolean success,
            @JsonProperty("error") int error,
            @JsonProperty("testnet") boolean testnet,
            @JsonProperty("message") String message,
            @JsonProperty("usOut") long usOut,
            @JsonProperty("usIn") long usIn,
            @JsonProperty("usDiff") long usDiff,
            @JsonProperty("result") DeribitSummary result) {

        super(success, error, testnet, message, usOut, usIn, usDiff, result);
    }
}