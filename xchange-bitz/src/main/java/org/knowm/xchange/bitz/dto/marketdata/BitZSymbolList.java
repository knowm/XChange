package org.knowm.xchange.bitz.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Map;

public class BitZSymbolList {
  private final Map<String, BitZSymbol> symbolMap;

  @JsonCreator
  public BitZSymbolList(Map<String, BitZSymbol> symbolMap) {
    this.symbolMap = symbolMap;
  }

  public Map<String, BitZSymbol> getSymbolMap() {
    return symbolMap;
  }
}
