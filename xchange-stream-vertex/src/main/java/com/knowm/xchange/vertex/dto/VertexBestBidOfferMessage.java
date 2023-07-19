package com.knowm.xchange.vertex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.knowm.xchange.vertex.NanoSecondsDeserializer;
import java.math.BigInteger;
import java.time.Instant;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class VertexBestBidOfferMessage {


  private final Instant timestamp;
  private final long product_id;
  private final BigInteger bid_price;
  private final BigInteger bid_qty;
  private final BigInteger ask_price;
  private final BigInteger ask_qty;


  public VertexBestBidOfferMessage(@JsonProperty("timestamp") @JsonDeserialize(using = NanoSecondsDeserializer.class) Instant timestamp,
                                   @JsonProperty("product_id") long product_id,
                                   @JsonProperty("bid_price") BigInteger bid_price,
                                   @JsonProperty("bid_qty") BigInteger bid_qty,
                                   @JsonProperty("ask_price") BigInteger ask_price,
                                   @JsonProperty("ask_qty") BigInteger ask_qty) {
    this.timestamp = timestamp;
    this.product_id = product_id;
    this.bid_price = bid_price;
    this.bid_qty = bid_qty;
    this.ask_price = ask_price;
    this.ask_qty = ask_qty;
  }


}
