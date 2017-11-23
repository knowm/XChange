package org.knowm.xchange.luno.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LunoBoolean {

  public final boolean success;

  public LunoBoolean(@JsonProperty(value = "success", required = true) boolean success) {
    this.success = success;
  }

  @Override
  public String toString() {
    return "LunoBoolean [success=" + success + "]";
  }
}
