package org.knowm.xchange.yobit.dto.marketdata;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = YoBitTickersDeserializer.class)
public class YoBitTickersReturn {

  public final Map<String, YoBitTicker> tickers;

  public YoBitTickersReturn(Map<String, YoBitTicker> tickers) {
    this.tickers = tickers;
  }
}


