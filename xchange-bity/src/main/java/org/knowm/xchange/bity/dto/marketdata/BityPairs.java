package org.knowm.xchange.bity.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BityPairs {

  @JsonProperty("pairs")
  private List<BityPair> pairs = null;

  @JsonProperty("pairs")
  public List<BityPair> getPairs() {
    return pairs;
  }

  @JsonProperty("pairs")
  public void setPairs(List<BityPair> pairs) {
    this.pairs = pairs;
  }
}
