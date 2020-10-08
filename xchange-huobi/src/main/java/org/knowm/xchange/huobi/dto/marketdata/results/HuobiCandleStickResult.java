package org.knowm.xchange.huobi.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import org.knowm.xchange.huobi.dto.HuobiResult;
import org.knowm.xchange.huobi.dto.marketdata.HuobiCandle;

public class HuobiCandleStickResult extends HuobiResult<HuobiCandle[]> {

  @JsonCreator
  public HuobiCandleStickResult(
      @JsonProperty("status") String status,
      @JsonProperty("ts") Date ts,
      @JsonProperty("data") HuobiCandle[] data,
      @JsonProperty("ch") String ch,
      @JsonProperty("err-code") String errCode,
      @JsonProperty("err-msg") String errMsg) {
    super(status, errCode, errMsg, data);
  }
}
