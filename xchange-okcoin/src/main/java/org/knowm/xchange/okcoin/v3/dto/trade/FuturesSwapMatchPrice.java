package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

/**
 * Order at best counter party price? (0:no 1:yes) the parameter is defaulted as 0. If it is set as
 * 1, the price parameter will be ignored，When posting orders at best bid price, order_type can only
 * be 0 (regular order)
 */
@AllArgsConstructor
public enum FuturesSwapMatchPrice {
  best_counter_party_price_no("0"),
  best_counter_party_price_yes("1");

  private final String value;

  @JsonValue
  public String getValue() {
    return value;
  }
}
