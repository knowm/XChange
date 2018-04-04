package org.knowm.xchange.huobi.dto.marketdata.results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import org.knowm.xchange.huobi.dto.HuobiResult;
import org.knowm.xchange.huobi.dto.marketdata.HuobiTicker;

public class HuobiTickerResult extends HuobiResult<HuobiTicker> {

  private final Date ts;
  private final String ch;

  @JsonCreator
  public HuobiTickerResult(
      @JsonProperty("status") String status,
      @JsonProperty("ts") Date ts,
      @JsonProperty("tick") HuobiTicker tick,
      @JsonProperty("ch") String ch,
      @JsonProperty("err-code") String errCode,
      @JsonProperty("err-msg") String errMsg) {
    super(status, errCode, errMsg, tick);
    this.ts = ts;
    this.ch = ch;
    getResult().setTs(ts);
  }

  public Date getTs() {
    return ts;
  }

  public String getCh() {
    return ch;
  }
}
