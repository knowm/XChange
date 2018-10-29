package org.knowm.xchange.bitz.dto.marketdata.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bitz.dto.BitZResult;
import org.knowm.xchange.bitz.dto.marketdata.BitZCurrencyCoinRateList;

public class BitZCurrencyCoinRateResult extends BitZResult<BitZCurrencyCoinRateList> {
  public BitZCurrencyCoinRateResult(
      @JsonProperty("status") int status,
      @JsonProperty("msg") String message,
      @JsonProperty("data") BitZCurrencyCoinRateList data,
      @JsonProperty("time") String time,
      @JsonProperty("microtime") String microtime,
      @JsonProperty("source") String source) {
    super(status, message, data);
  }
}
