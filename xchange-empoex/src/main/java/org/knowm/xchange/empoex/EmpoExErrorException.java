package org.knowm.xchange.empoex;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmpoExErrorException extends RuntimeException {

  private static final long serialVersionUID = 7066215022625090515L;

  @JsonProperty("error")
  String error;

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

}
