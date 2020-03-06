package org.knowm.xchange.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

public class OpenPositions implements Serializable {

  @JsonProperty("openPositions")
  private final List<Position> positions;

  @JsonCreator
  public OpenPositions(@JsonProperty("openPositions") List<Position> positions) {
    this.positions = positions;
  }

  public List<Position> getPositions() {
    return positions;
  }

  @Override
  public String toString() {
    return "OpenPositions{" + "positions=" + positions + '}';
  }
}
