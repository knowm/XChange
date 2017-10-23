package org.knowm.xchange.luno.dto.marketdata;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LunoOrderBook {

  public final long timestamp;
  private final TreeMap<BigDecimal, BigDecimal> bids = new TreeMap<BigDecimal, BigDecimal>(new Comparator<BigDecimal>() {
    @Override
    public int compare(BigDecimal k1, BigDecimal k2) {
      return -k1.compareTo(k2);
    }
  });
  private final TreeMap<BigDecimal, BigDecimal> asks = new TreeMap<BigDecimal, BigDecimal>();

  public LunoOrderBook(@JsonProperty(value = "timestamp", required = true) long timestamp
      , @JsonProperty(value = "asks") Order[] asks
      , @JsonProperty(value = "bids") Order[] bids) {
    this.timestamp = timestamp;
    // we merge the orders with the same price together bei adding the volumes

    // java8 style:
    //this.asks = Stream.of(asks).collect(Collectors.toMap(o -> o.price, o -> o.volume, (v1, v2) -> v1.add(v2), () -> new TreeMap<BigDecimal, BigDecimal>()));
    //this.bids = Stream.of(bids).collect(Collectors.toMap(o -> o.price, o -> o.volume, (v1, v2) -> v1.add(v2), () -> new TreeMap<BigDecimal, BigDecimal>((k1, k2) -> -k1.compareTo(k2))));

    // without java8:
    addOrdersToMap(asks, this.asks);
    addOrdersToMap(bids, this.bids);

  }

  private static void addOrdersToMap(Order[] orders, Map<BigDecimal, BigDecimal> map) {
    for (Order o : orders) {
      BigDecimal v = map.get(o.price);
      map.put(o.price, v == null ? o.volume : o.volume.add(v));
    }
  }

  public Date getTimestamp() {
    return new Date(timestamp);
  }

  public Map<BigDecimal, BigDecimal> getBids() {
    return Collections.unmodifiableMap(bids);
  }

  public Map<BigDecimal, BigDecimal> getAsks() {
    return Collections.unmodifiableMap(asks);
  }

  @Override
  public String toString() {
    return "LunoOrderBook [timestamp=" + getTimestamp() + ", bids=" + bids + ", asks=" + asks + "]";
  }

  public static class Order {
    public final BigDecimal price;
    public final BigDecimal volume;

    public Order(@JsonProperty(value = "price", required = true) BigDecimal price, @JsonProperty(value = "volume", required = true) BigDecimal volume) {
      this.price = price;
      this.volume = volume;
    }
  }
}
