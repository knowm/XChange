package org.knowm.xchange.hitbtc.dto.meta;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.meta.CurrencyMetaData;

public class HitbtcMetaData {
  @JsonProperty
  public Map<Currency, CurrencyMetaData> currency;

  @JsonProperty
  public int minPollDelay;
}
