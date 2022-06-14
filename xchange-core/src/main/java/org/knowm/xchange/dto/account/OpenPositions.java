package org.knowm.xchange.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class OpenPositions implements Serializable {

  private final List<OpenPosition> openPositions;

  @JsonCreator
  public OpenPositions(@JsonProperty("openPositions") List<OpenPosition> openPositions) {
    this.openPositions = openPositions;
  }

  public List<OpenPosition> getOpenPositions() {
    return openPositions;
  }

  @Override
  public String toString() {
    return "OpenPositions{" + "openPositions=" + openPositions + '}';
  }
}
