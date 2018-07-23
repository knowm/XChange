package org.knowm.xchange.bity.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BityPair {

  @JsonProperty("enabled")
  private Boolean enabled;

  @JsonProperty("input")
  private String input;

  @JsonProperty("output")
  private String output;

  @JsonProperty("enabled")
  public Boolean getEnabled() {
    return enabled;
  }

  @JsonProperty("enabled")
  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  @JsonProperty("input")
  public String getInput() {
    return input;
  }

  @JsonProperty("input")
  public void setInput(String input) {
    this.input = input;
  }

  @JsonProperty("output")
  public String getOutput() {
    return output;
  }

  @JsonProperty("output")
  public void setOutput(String output) {
    this.output = output;
  }
}
