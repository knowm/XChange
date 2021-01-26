package org.knowm.xchange.dto.account;

import java.io.Serializable;
import java.util.List;

public class OpenPositions implements Serializable {

  private final List<OpenPosition> openPositions;

  public OpenPositions(List<OpenPosition> openPositions) {
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
