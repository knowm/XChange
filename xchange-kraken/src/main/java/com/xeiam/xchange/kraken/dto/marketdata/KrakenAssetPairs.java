package com.xeiam.xchange.kraken.dto.marketdata;

import java.util.Map;

public class KrakenAssetPairs {

  private final Map<String, KrakenAssetPair> assetPairMap;

  /**
   * Constructor
   * 
   * @param assetPairMap
   */
  public KrakenAssetPairs(final Map<String, KrakenAssetPair> assetPairMap) {

    this.assetPairMap = assetPairMap;
  }

  public Map<String, KrakenAssetPair> getAssetPairMap() {

    return assetPairMap;
  }
}
