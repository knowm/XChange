package com.xeiam.xchange.hitbtc.dto.meta;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.dto.meta.CurrencyMetaData;

public class HitbtcMetaData {
  @JsonProperty
  public Map<String, CurrencyMetaData> currency;

  @JsonProperty
  public int minPollDelay;
}
