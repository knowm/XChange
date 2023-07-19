package com.knowm.xchange.vertex.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Tx {

  private final String sender;
  private final long[] productIds;
  private final String[] digests;
  private final String nonce;

  public Tx(@JsonProperty("sender") String sender, @JsonProperty("productIds") long[] productIds, @JsonProperty("digests") String[] digests, @JsonProperty("nonce") String nonce) {
    this.sender = sender;
    this.productIds = productIds;
    this.digests = digests;
    this.nonce = nonce;
  }

  @JsonInclude()
  public long[] getProductIds() {
    return productIds;
  }

  @JsonInclude(JsonInclude.Include.NON_NULL)
  public String[] getDigests() {
    return digests;
  }
}
