package org.knowm.xchange.hitbtc.dto.meta;

import java.util.Map;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.meta.CurrencyMetaData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcMetaData {

  @JsonProperty
  public Map<Currency, CurrencyMetaData> currency;

  @JsonProperty
  public int minPollDelay;
}
