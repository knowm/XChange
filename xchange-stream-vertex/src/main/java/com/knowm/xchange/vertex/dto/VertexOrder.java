package com.knowm.xchange.vertex.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VertexOrder {

  private final String sender;
  private final String priceX18;
  private final String amount;
  private final String expiration;
  private final String nonce;

  public VertexOrder(String sender, String priceX18, String amount, String expiration, String nonce) {
    this.sender = sender;
    this.priceX18 = priceX18;
    this.amount = amount;
    this.expiration = expiration;
    this.nonce = nonce;
  }

}
