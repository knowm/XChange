package com.xeiam.xchange.vaultofsatoshi.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data object representing a Vos Order from VaultOfSatoshi
 */
public final class VosOrder {

  private final VosCurrency quantity;
  private final VosCurrency price;

  public VosOrder(@JsonProperty("quantity") VosCurrency quantity, @JsonProperty("price") VosCurrency price) {

    this.quantity = quantity;
    this.price = price;
  }

  public VosCurrency getPrice() {

    return price;
  }

  public VosCurrency getAmount() {

    return quantity;
  }

  @Override
  public String toString() {

    return "VosOrder [quantity=" + quantity + ", price=" + price + "]";
  }

}
