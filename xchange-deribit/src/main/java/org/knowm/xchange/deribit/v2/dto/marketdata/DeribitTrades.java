package org.knowm.xchange.deribit.v2.dto.marketdata;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitTrades {

    @JsonProperty("trades") public List<DeribitTrade> trades;
    @JsonProperty("has_more") public boolean hasMore;


    public List<DeribitTrade> getTrades() {
        return trades;
    }

    public boolean isHasMore() {
        return hasMore;
    }
}
