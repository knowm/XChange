package org.knowm.xchange.btcturk.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author mertguner
 */
public class BTCTurkKeyValues {

  private final String key;
  private final String value;

  public BTCTurkKeyValues(@JsonProperty("key") String key, @JsonProperty("value") String value) {
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "BTCTurkKeyValues [key=" + key + ", value=" + value + "]";
  }
}
