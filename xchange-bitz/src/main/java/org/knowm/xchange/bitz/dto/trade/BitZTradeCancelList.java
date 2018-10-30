package org.knowm.xchange.bitz.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Map;

public class BitZTradeCancelList {
  private final Map<String, BitZTradeCancel> tradeCancelMap;

  @JsonCreator
  public BitZTradeCancelList(Map<String, BitZTradeCancel> tradeCancelMap) {
    this.tradeCancelMap = tradeCancelMap;
  }

  public Map<String, BitZTradeCancel> getTradeCancelMap() {
    return tradeCancelMap;
  }
}
