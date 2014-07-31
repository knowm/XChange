package com.xeiam.xchange.mintpal.dto.marketdata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author jamespedwards42
 */
public class MintPalPublicOrders {

  private final String type;
  private final int count;
  private final List<MintPalPublicOrder> orders;

  public MintPalPublicOrders(@JsonProperty("type") String type, @JsonProperty("count") int count, @JsonProperty("orders") List<MintPalPublicOrder> orders) {

    this.type = type;
    this.count = count;
    this.orders = orders;
  }

  public String getType() {

    return type;
  }

  public int getCount() {

    return count;
  }

  public List<MintPalPublicOrder> getOrders() {

    return orders;
  }

  @Override
  public String toString() {

    return "MintPalPublicOrders [type=" + type + ", count=" + count + ", orders=" + orders + "]";
  }

}
