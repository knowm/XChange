package org.knowm.xchange.huobi.dto.marketdata;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.BiConsumer;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class HuobiDepth {

  private final long id;
  public final SortedMap<BigDecimal, BigDecimal> bids;
  public final SortedMap<BigDecimal, BigDecimal> asks;
  private Date ts;

  public HuobiDepth(@JsonProperty("id") long id, @JsonProperty("bids") List<BigDecimal[]> bidsJson,
      @JsonProperty("asks") List<BigDecimal[]> asksJson) {
    this.id = id;

    BiConsumer<Object[], Map<BigDecimal, BigDecimal>> entryProcessor = (obj, col) -> {
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

  public long getId() {
    return id;
  }

  public SortedMap<BigDecimal, BigDecimal> getBids() {
    return bids;
  }

  public SortedMap<BigDecimal, BigDecimal> getAsks() {
    return asks;
  }

  public Date getTs() {
    return ts;
  }

  public void setTs(Date ts) {
    this.ts = ts;
  }

  @Override
  public String toString() {
    return "HuobiTicker [id=" + getId() + ", bids=" + getBids().toString() + ", asks=" + getAsks().toString() + "]";
  }
}
