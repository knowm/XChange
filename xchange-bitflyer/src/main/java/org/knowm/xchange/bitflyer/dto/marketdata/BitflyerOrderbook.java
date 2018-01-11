package org.knowm.xchange.bitflyer.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;

public class BitflyerOrderbook {
    public final BigDecimal mid_price;
    public final SortedMap<BigDecimal, BigDecimal> bids;
    public final SortedMap<BigDecimal, BigDecimal> asks;

    public BitflyerOrderbook(@JsonProperty("mid_price") BigDecimal mid_price
            , @JsonProperty("bids") List<Object[]> bidsJson
            , @JsonProperty("asks") List<Object[]> asksJson) {

        this.mid_price = mid_price;

        BiConsumer<Object[], Map<BigDecimal, BigDecimal>> entryProcessor = (obj, col) -> {
            BigDecimal price = new BigDecimal(obj[0].toString());
            BigDecimal qty = new BigDecimal(obj[1].toString());
            col.put(price, qty);
        };

        TreeMap<BigDecimal, BigDecimal> bids = new TreeMap<>((k1, k2) -> -k1.compareTo(k2));
        TreeMap<BigDecimal, BigDecimal> asks = new TreeMap<>();

        bidsJson.forEach(e -> entryProcessor.accept(e, bids));
        asksJson.forEach(e -> entryProcessor.accept(e, asks));

        this.bids = Collections.unmodifiableSortedMap(bids);
        this.asks = Collections.unmodifiableSortedMap(asks);
    }
}
