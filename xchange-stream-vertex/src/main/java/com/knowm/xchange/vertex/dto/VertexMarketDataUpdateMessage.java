package com.knowm.xchange.vertex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.knowm.xchange.vertex.NanoSecondsDeserializer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VertexMarketDataUpdateMessage {

  public static final List<PriceAndQuantity> EMPTY_EVENTS = new ArrayList<>();
  public static final VertexMarketDataUpdateMessage EMPTY = new VertexMarketDataUpdateMessage(EMPTY_EVENTS, EMPTY_EVENTS, null, null, null, -1);

  private final List<PriceAndQuantity> bids;
  private final List<PriceAndQuantity> asks;
  private final Instant minTime;
  private final Instant maxTime;
  private final Instant lastMaxTime;
  private final long productId;

  public VertexMarketDataUpdateMessage(@JsonProperty("bids") List<PriceAndQuantity> bids,
                                       @JsonProperty("asks") List<PriceAndQuantity> asks,
                                       @JsonProperty("min_timestamp") @JsonDeserialize(using = NanoSecondsDeserializer.class) Instant minTime,
                                       @JsonProperty("max_timestamp") @JsonDeserialize(using = NanoSecondsDeserializer.class) Instant maxTime,
                                       @JsonProperty("last_max_timestamp") @JsonDeserialize(using = NanoSecondsDeserializer.class) Instant lastMaxTime,
                                       @JsonProperty("product_id") long productId) {
    this.bids = bids;
    this.asks = asks;
    this.minTime = minTime;
    this.maxTime = maxTime;
    this.lastMaxTime = lastMaxTime;
    this.productId = productId;
  }

  public static VertexMarketDataUpdateMessage empty() {
    return EMPTY;
  }

}
