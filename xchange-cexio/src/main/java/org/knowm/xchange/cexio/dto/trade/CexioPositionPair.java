package org.knowm.xchange.cexio.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Andrea Fossi.
 */
public class CexioPositionPair {
  private final String symbol1;
  private final String symbol2;

  public CexioPositionPair(
      @JsonProperty("symbol1") String symbol1, @JsonProperty("symbol2") String symbol2) {
    this.symbol1 = symbol1;
    this.symbol2 = symbol2;
  }

  public String getSymbol1() {
    return symbol1;
  }

  public String getSymbol2() {
    return symbol2;
  }
}
