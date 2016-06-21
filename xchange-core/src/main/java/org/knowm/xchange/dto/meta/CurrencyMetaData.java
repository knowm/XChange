package org.knowm.xchange.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrencyMetaData {

  @JsonProperty
  public int scale;

  /**
   * Constructor
   */
  public CurrencyMetaData() {
  }

  /**
   * Constructor
   *
   * @param scale
   */
  public CurrencyMetaData(int scale) {
    this.scale = scale;
  }

  @Override
  public String toString() {
    return "CurrencyMetaData{" + "scale=" + scale + '}';
  }
}
