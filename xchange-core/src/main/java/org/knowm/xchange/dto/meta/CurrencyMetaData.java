package org.knowm.xchange.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrencyMetaData {

  @JsonProperty("scale")
  private final int scale;

  /**
   * Constructor
   *
   * @param scale
   */
  public CurrencyMetaData(@JsonProperty("scale") int scale) {
    this.scale = scale;
  }

  public int getScale() {
    return scale;
  }

  @Override
  public String toString() {
    return "CurrencyMetaData{" + "scale=" + scale + '}';
  }
}
