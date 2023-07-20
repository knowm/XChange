package com.knowm.xchange.vertex.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CancelProductOrders {
  private final Tx tx;
  private final String signature;

  public CancelProductOrders(Tx tx, String signature) {
    this.tx = tx;
    this.signature = signature;
  }
}
