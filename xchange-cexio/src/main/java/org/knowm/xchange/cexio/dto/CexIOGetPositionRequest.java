package org.knowm.xchange.cexio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Author: Andrea Fossi Since: 22/06/18 */
public class CexIOGetPositionRequest extends CexIORequest {
  @JsonProperty("id")
  public final String id;

  public CexIOGetPositionRequest(String id) {
    this.id = id;
  }
}
