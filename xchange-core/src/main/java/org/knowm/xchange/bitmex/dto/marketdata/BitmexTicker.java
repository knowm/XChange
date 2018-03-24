package org.knowm.xchange.bitmex.dto.marketdata;

import java.util.Map;

public class BitmexTicker {

  private final Map<String, BitmexTicker> assetInfoMap;

  /**
   * Constructor
   *
   * @param assetInfoMap
   */
  public BitmexTicker(Map<String, BitmexTicker> assetInfoMap) {

    this.assetInfoMap = assetInfoMap;
  }

  public Map<String, BitmexTicker> getAssetPairMap() {

    return assetInfoMap;
  }

  @Override
  public String toString() {

    return "BitmexTickers [assetInfoMap=" + assetInfoMap + "]";
  }
}
