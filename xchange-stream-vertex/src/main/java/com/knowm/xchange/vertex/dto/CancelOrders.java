package com.knowm.xchange.vertex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CancelOrders {

  private final Tx tx;
  private final String signature;

  public CancelOrders(@JsonProperty("tx") Tx tx, @JsonProperty("signature") String signature) {
    this.tx = tx;
    this.signature = signature;
  }

}
