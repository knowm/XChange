package org.knowm.xchange.bybit.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class BybitOrderbook {

  @JsonProperty("s")
  String symbol;

  @JsonProperty("b")
  List<String[]> bids;

  @JsonProperty("a")
  List<String[]> asks;

  @JsonProperty("ts")
  long timestamp;

  @JsonProperty("u")
  long updateId;

  @JsonProperty("seq")
  long crossSequence;

  @JsonProperty("cts")
  long crossTimestamp;

  public SortedMap<BigDecimal, BigDecimal> getBids() {
    TreeMap<BigDecimal, BigDecimal> bidsMap = new TreeMap<>((k1, k2) -> -k1.compareTo(k2));
    convertToSortedMap(bidsMap, bids);
    return Collections.unmodifiableSortedMap(bidsMap);
  }

  public SortedMap<BigDecimal, BigDecimal> getAsks() {
    TreeMap<BigDecimal, BigDecimal> asksMap = new TreeMap<>();
    convertToSortedMap(asksMap, asks);
    return Collections.unmodifiableSortedMap(asksMap);
  }

  private void convertToSortedMap(SortedMap<BigDecimal, BigDecimal> result, List<String[]> asks) {
    BiConsumer<String[], Map<BigDecimal, BigDecimal>> entryProcessor =
        (entry, map) -> {
          BigDecimal price = new BigDecimal(entry[0]);
          BigDecimal qty = new BigDecimal(entry[1]);
          map.put(price, qty);
        };
    asks.forEach(e -> entryProcessor.accept(e, result));
  }
}
