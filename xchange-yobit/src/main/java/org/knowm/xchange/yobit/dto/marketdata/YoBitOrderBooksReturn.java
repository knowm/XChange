package org.knowm.xchange.yobit.dto.marketdata;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = YoBitOrderBooksDeserializer.class)
public class YoBitOrderBooksReturn {

  public final Map<String, YoBitOrderBook> orderBooks;

  public YoBitOrderBooksReturn(Map<String, YoBitOrderBook> tickers) {
    this.orderBooks = tickers;
  }
}


