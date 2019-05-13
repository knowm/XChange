package org.knowm.xchange.globitex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

public class GlobitexErrors implements Serializable {

  @JsonProperty("errors")
  private final List<GlobitexError> errors;

  public GlobitexErrors(@JsonProperty("errors") List<GlobitexError> errors) {
    this.errors = errors;
  }

  public List<GlobitexError> getErrors() {
    return errors;
  }

  @Override
  public String toString() {
    return "GlobitexErrors{" + "erros=" + errors + '}';
  }
}
