package com.xeiam.xchange.okcoin.dto.account;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OkCoinFunds {

  private Map<String, BigDecimal> free;
  private Map<String, BigDecimal> freezed;
  private Map<String, BigDecimal> borrow;

  public OkCoinFunds(@JsonProperty("free") final Map<String, BigDecimal> free, @JsonProperty("freezed") final Map<String, BigDecimal> freezed,
      @JsonProperty("borrow") final Map<String, BigDecimal> borrow) {

    this.free = free;
    this.freezed = freezed;
    this.borrow = borrow;
  }

  public Map<String, BigDecimal> getFree() {

    if (free == null) {
      free = new HashMap<String, BigDecimal>();
    }

    if (!free.containsKey("cny")) {
      free.put("cny", BigDecimal.ZERO);
    }
    if (!free.containsKey("usd")) {
      free.put("usd", BigDecimal.ZERO);
    }
    if (!free.containsKey("btc")) {
      free.put("btc", BigDecimal.ZERO);
    }
    if (!free.containsKey("ltc")) {
      free.put("ltc", BigDecimal.ZERO);
    }

    return free;
  }

  public Map<String, BigDecimal> getFreezed() {

    if (freezed == null) {
      freezed = new HashMap<String, BigDecimal>();
    }

    if (!freezed.containsKey("cny")) {
      freezed.put("cny", BigDecimal.ZERO);
    }
    if (!freezed.containsKey("usd")) {
      freezed.put("usd", BigDecimal.ZERO);
    }
    if (!freezed.containsKey("btc")) {
      freezed.put("btc", BigDecimal.ZERO);
    }
    if (!freezed.containsKey("ltc")) {
      freezed.put("ltc", BigDecimal.ZERO);
    }

    return freezed;
  }

  public Map<String, BigDecimal> getBorrow() {

    if (borrow == null) {
      borrow = new HashMap<String, BigDecimal>();
    }

    if (!borrow.containsKey("cny")) {
      borrow.put("cny", BigDecimal.ZERO);
    }
    if (!borrow.containsKey("usd")) {
      borrow.put("usd", BigDecimal.ZERO);
    }
    if (!borrow.containsKey("btc")) {
      borrow.put("btc", BigDecimal.ZERO);
    }
    if (!borrow.containsKey("ltc")) {
      borrow.put("ltc", BigDecimal.ZERO);
    }

    return borrow;
  }

  @Override
  public String toString() {

    return "OkCoinFunds [free=" + free + ", freezed=" + freezed + ", borrow=" + borrow + "]";
  }

}
