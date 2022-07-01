package org.knowm.xchange.globitex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

public class GlobitexSymbols implements Serializable {

  @JsonProperty("symbols")
  private final List<GlobitexSymbol> symbols;

  public GlobitexSymbols(@JsonProperty("symbols") List<GlobitexSymbol> symbols) {
    super();
    this.symbols = symbols;
  }

  public List<GlobitexSymbol> getSymbols() {
    return symbols;
  }

  @Override
  public String toString() {
    return "GlobitexSymbols{" + "symbols=" + symbols + '}';
  }
}
