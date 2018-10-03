package org.knowm.xchange.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Created by cyril on 11-Oct-17. */
public class BinanceListenKey {

  private String listenKey;

  public BinanceListenKey(@JsonProperty("listenKey") String listenKey) {
    this.listenKey = listenKey;
  }

  public String getListenKey() {
    return listenKey;
  }

  public void setListenKey(String listenKey) {
    this.listenKey = listenKey;
  }
}
