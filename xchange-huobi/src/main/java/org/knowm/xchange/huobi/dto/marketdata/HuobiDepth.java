package org.knowm.xchange.huobi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.BiConsumer;

public final class HuobiDepth {

  private final long id;
  private final Date ts;
  public final SortedMap<BigDecimal, BigDecimal> bids;
  public final SortedMap<BigDecimal, BigDecimal> asks;

  public HuobiDepth(
      @JsonProperty("id") long id,
      @JsonProperty("ts") Date ts,
      @JsonProperty("bids") List<BigDecimal[]> bidsJson,
      @JsonProperty("asks") List<BigDecimal[]> asksJson) {
    this.id = id;
    this.ts = ts;

    BiConsumer<BigDecimal[], Map<BigDecimal, BigDecimal>> entryProcessor =
        (obj, col) -> {
          col.put(obj[0], obj[1]);
        };

    TreeMap<BigDecimal, BigDecimal> bids = new TreeMap<>((k1, k2) -> -k1.compareTo(k2));
    TreeMap<BigDecimal, BigDecimal> asks = new TreeMap<>();

    bidsJson.stream().forEach(e -> entryProcessor.accept(e, bids));
    asksJson.stream().forEach(e -> entryProcessor.accept(e, asks));

    this.bids = Collections.unmodifiableSortedMap(bids);
    this.asks = Collections.unmodifiableSortedMap(asks);
  }

  public long getId() {
    return id;
  }

  public Date getTs() {
    return ts;
  }

  public SortedMap<BigDecimal, BigDecimal> getBids() {
    return bids;
  }

  public SortedMap<BigDecimal, BigDecimal> getAsks() {
    return asks;
  }

  @Override
  public String toString() {
    return "HuobiDepth [id="
        + getId()
        + ", timestamp="
        + getTs()
        + ", bids="
        + getBids().toString()
        + ", asks="
        + getAsks().toString()
        + "]";
  }
}
