package org.knowm.xchange.coinbase.v2.dto.account.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CoinbasePagination {


    private final String endingBefore;
    private final String startingAfter;
    private final int limit;
    private final String order;
    private final String previousUri;
    private final String nextUri;

    public CoinbasePagination(
            @JsonProperty("ending_before") String endingBefore,
            @JsonProperty("starting_after") String startingAfter,
            @JsonProperty("limit") int limit,
            @JsonProperty("order") String order,
            @JsonProperty("previous_uri") String previousUri,
            @JsonProperty("next_uri") String nextUri
    ) {
        this.endingBefore = endingBefore;
        this.startingAfter = startingAfter;
        this.limit = limit;
        this.order = order;
        this.previousUri = previousUri;
        this.nextUri = nextUri;
    }
}