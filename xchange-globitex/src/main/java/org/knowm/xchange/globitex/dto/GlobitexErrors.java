package org.knowm.xchange.globitex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class GlobitexErrors {

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
    return "GlobitexException{" + "erros=" + errors + '}';
  }
}
