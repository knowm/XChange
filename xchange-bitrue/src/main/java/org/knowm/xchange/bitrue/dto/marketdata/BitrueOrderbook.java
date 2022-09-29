package org.knowm.xchange.bitrue.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;

public final class BitrueOrderbook {

  public final long lastUpdateId;
  public final SortedMap<BigDecimal, BigDecimal> bids;
  public final SortedMap<BigDecimal, BigDecimal> asks;

  public BitrueOrderbook(
      @JsonProperty("lastUpdateId") long lastUpdateId,
      @JsonProperty("bids") List<Object[]> bidsJson,
      @JsonProperty("asks") List<Object[]> asksJson) {
    this.lastUpdateId = lastUpdateId;
    BiConsumer<Object[], Map<BigDecimal, BigDecimal>> entryProcessor =
        (obj, col) -> {
          BigDecimal price = new BigDecimal(obj[0].toString());
          BigDecimal qty = new BigDecimal(obj[1].toString());
          col.put(price, qty);
        };

    TreeMap<BigDecimal, BigDecimal> bids = new TreeMap<>((k1, k2) -> -k1.compareTo(k2));
    TreeMap<BigDecimal, BigDecimal> asks = new TreeMap<>();

    bidsJson.stream().forEach(e -> entryProcessor.accept(e, bids));
    asksJson.stream().forEach(e -> entryProcessor.accept(e, asks));

    this.bids = Collections.unmodifiableSortedMap(bids);
    this.asks = Collections.unmodifiableSortedMap(asks);
  }
}
