package org.knowm.xchange.btce.v3.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTCEMetaData {

  /**
   * The number of seconds the public data is cached for.
   */
  @JsonProperty
  public int publicInfoCacheSeconds;

  @JsonProperty
  public int amountScale;
}
