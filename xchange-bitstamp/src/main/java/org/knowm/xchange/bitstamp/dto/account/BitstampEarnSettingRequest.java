package org.knowm.xchange.bitstamp.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class BitstampEarnSettingRequest {

  @JsonProperty("setting")
  Setting setting;

  @JsonProperty("currency")
  String currency;

  @JsonProperty("earn_type")
  BitstampEarnType earnType;

  public enum Setting {
    OPT_IN,
    OPT_OUT
  }
}
