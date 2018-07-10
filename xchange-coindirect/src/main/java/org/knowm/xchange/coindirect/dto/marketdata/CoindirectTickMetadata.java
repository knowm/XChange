package org.knowm.xchange.coindirect.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoindirectTickMetadata {
    public final String market;
    public final String history;
    public final long limit;

    public CoindirectTickMetadata(@JsonProperty("market") String market, @JsonProperty("history") String history, @JsonProperty("limit") long limit) {
        this.market = market;
        this.history = history;
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "CoindirectTickMetadata{" +
                "market='" + market + '\'' +
                ", history='" + history + '\'' +
                ", limit=" + limit +
                '}';
    }
}
