package org.knowm.xchange.huobi.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.huobi.dto.HuobiResult;
import org.knowm.xchange.huobi.dto.marketdata.HuobiKline;

public class HuobiKlinesResult extends HuobiResult<HuobiKline[]> {
  @JsonCreator
  public HuobiKlinesResult(
      @JsonProperty("status") String status,
      @JsonProperty("data") HuobiKline[] data,
      @JsonProperty("err-code") String errCode,
      @JsonProperty("err-msg") String errMsg) {
    super(status, errCode, errMsg, data);
  }
}
