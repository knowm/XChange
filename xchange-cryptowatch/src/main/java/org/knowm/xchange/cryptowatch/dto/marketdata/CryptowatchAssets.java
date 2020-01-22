package org.knowm.xchange.cryptowatch.dto.marketdata;

import java.util.List;

public class CryptowatchAssets {

  private final List<CryptowatchAsset> assets;

  public CryptowatchAssets(List<CryptowatchAsset> assets) {
    this.assets = assets;
  }

  public List<CryptowatchAsset> getAssets() {
    return assets;
  }
}
