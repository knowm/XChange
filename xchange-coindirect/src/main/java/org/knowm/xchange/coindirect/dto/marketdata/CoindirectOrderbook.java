package org.knowm.xchange.coindirect.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;

public class CoindirectOrderbook {
    public final long sequence;
    public final List<CoindirectPriceLevel> bids;
    public final List<CoindirectPriceLevel> asks;

    public CoindirectOrderbook(
            @JsonProperty("sequence") long sequence,
            @JsonProperty("bids") List<CoindirectPriceLevel> bids,
            @JsonProperty("asks") List<CoindirectPriceLevel> asks) {
        this.sequence = sequence;
        this.bids = bids;
        this.asks = asks;
    }

    @Override
    public String toString() {
        return "CoindirectOrderbook{" +
                "sequence=" + sequence +
                ", bids=" + bids +
                ", asks=" + asks +
                '}';
    }
}
