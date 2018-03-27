package org.knowm.xchange.yobit.dto.marketdata;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Map;

@JsonDeserialize(using = YoBitTickersDeserializer.class)
public class YoBitTickersReturn {

  public final Map<String, YoBitTicker> tickers;

  public YoBitTickersReturn(Map<String, YoBitTicker> tickers) {
    this.tickers = tickers;
  }
}
