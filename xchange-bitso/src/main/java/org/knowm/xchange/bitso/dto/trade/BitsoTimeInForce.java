package org.knowm.xchange.bitso.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Time in force enum for orders */
public enum BitsoTimeInForce {
  @JsonProperty("goodtillcancelled")
  GOOD_TILL_CANCELLED,

  @JsonProperty("fillorkill")
  FILL_OR_KILL,

  @JsonProperty("immediateorcancel")
  IMMEDIATE_OR_CANCEL,

  @JsonProperty("postonly")
  POST_ONLY;

  @JsonCreator
  public static BitsoTimeInForce fromValue(String value) {
    switch (value) {
      case "goodtillcancelled":
        return GOOD_TILL_CANCELLED;
      case "fillorkill":
        return FILL_OR_KILL;
      case "immediateorcancel":
        return IMMEDIATE_OR_CANCEL;
      case "postonly":
        return POST_ONLY;
      default:
        throw new IllegalArgumentException("Unknown time in force: " + value);
    }
  }
}
