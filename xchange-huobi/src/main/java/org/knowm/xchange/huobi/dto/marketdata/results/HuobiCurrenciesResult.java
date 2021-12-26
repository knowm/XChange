package org.knowm.xchange.huobi.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.huobi.dto.HuobiResultV2;
import org.knowm.xchange.huobi.dto.marketdata.HuobiCurrencyWrapper;

public class HuobiCurrenciesResult extends HuobiResultV2<HuobiCurrencyWrapper[]> {

  public HuobiCurrenciesResult(
      @JsonProperty("code") String code,
      @JsonProperty("message") String message,
      @JsonProperty("data") HuobiCurrencyWrapper[] result) {
    super(code, message, result);
  }
}
