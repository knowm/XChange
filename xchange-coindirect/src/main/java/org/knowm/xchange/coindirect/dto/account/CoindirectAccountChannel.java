package org.knowm.xchange.coindirect.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoindirectAccountChannel {
  public final String value;

  public CoindirectAccountChannel(@JsonProperty("value") String value) {
    this.value = value;
  }
}
