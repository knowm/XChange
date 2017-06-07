package org.knowm.xchange.dsx.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXMetaData {

  @JsonProperty
  public int publicInfoCacheSeconds;

  @JsonProperty
  public int amountScale;
}
