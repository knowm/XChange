package org.knowm.xchange.bitz.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

// TODO: Implement Once Implemented By The Exchange
public class BitZTradeCancel {
  private final BitZAssetsInfo assetsInfo;
  private final BitZAssetsData assetsData;

  public BitZTradeCancel(
      @JsonProperty("assetsInfo") BitZAssetsInfo assetsInfo,
      @JsonProperty("updateAssetsData") BitZAssetsData assetsData) {
    this.assetsInfo = assetsInfo;
    this.assetsData = assetsData;
  }

  public BitZAssetsInfo getAssetsInfo() {
    return assetsInfo;
  }

  public BitZAssetsData getAssetsData() {
    return assetsData;
  }
}
