package org.knowm.xchange.kraken.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenAsset {

  private final String altName;
  private final String assetClass;
  private final int scale;
  private final int displayScale;

  /**
   * Constructor
   *
   * @param altName
   * @param assetClass
   * @param scale
   * @param displayScale
   */
  public KrakenAsset(@JsonProperty("altname") String altName, @JsonProperty("aclass") String assetClass, @JsonProperty("decimals") int scale,
      @JsonProperty("display_decimals") int displayScale) {

    this.altName = altName;
    this.assetClass = assetClass;
    this.scale = scale;
    this.displayScale = displayScale;
  }

  public String getAltName() {

    return altName;
  }

  public String getAssetClass() {

    return assetClass;
  }

  public int getScale() {

    return scale;
  }

  public int getDisplayScale() {

    return displayScale;
  }

  @Override
  public String toString() {

    return "KrakenAssetInfo [altName=" + altName + ", assetClass=" + assetClass + ", scale=" + scale + ", displayScale=" + displayScale + "]";
  }
}
