package org.knowm.xchange.bitz.dto.marketdata.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.bitz.dto.BitZResult;
import org.knowm.xchange.bitz.dto.marketdata.BitZCurrencyRate;

public class BitZCurrencyRateResult extends BitZResult<Map<String, BitZCurrencyRate>> {
  public BitZCurrencyRateResult(
      @JsonProperty("status") int status,
      @JsonProperty("msg") String message,
      @JsonProperty("data") Map<String, BitZCurrencyRate> data,
      @JsonProperty("time") String time,
      @JsonProperty("microtime") String microtime,
      @JsonProperty("source") String source) {
    super(status, message, data);
  }
}
