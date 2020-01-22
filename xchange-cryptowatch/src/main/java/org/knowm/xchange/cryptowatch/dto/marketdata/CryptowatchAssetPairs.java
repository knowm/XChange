package org.knowm.xchange.cryptowatch.dto.marketdata;

import java.util.List;

public class CryptowatchAssetPairs {

  private final List<CryptowatchAssetPair> pairs;

  public CryptowatchAssetPairs(List<CryptowatchAssetPair> pairs) {
    this.pairs = pairs;
  }

  public List<CryptowatchAssetPair> getPairs() {
    return pairs;
  }
}
