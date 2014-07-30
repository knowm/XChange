package com.xeiam.xchange.kraken.dto.marketdata;

import java.util.Map;

public class KrakenAssets {

  private final Map<String, KrakenAsset> assetInfoMap;

  /**
   * Constructor
   * 
   * @param assetInfoMap
   */
  public KrakenAssets(final Map<String, KrakenAsset> assetInfoMap) {

    this.assetInfoMap = assetInfoMap;
  }

  public Map<String, KrakenAsset> getAssetPairMap() {

    return assetInfoMap;
  }

  @Override
  public String toString() {

    return "KrakenAssets [assetInfoMap=" + assetInfoMap + "]";
  }
}
