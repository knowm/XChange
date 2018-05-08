package org.knowm.xchange.huobi.dto.marketdata;

public class HuobiAsset {

  private final String asset;

  public HuobiAsset(String asset) {
    this.asset = asset;
  }

  public String getAsset() {
    return asset;
  }

  @Override
  public String toString() {
    return String.format("HuobiAsset [%s]", asset);
  }
}
