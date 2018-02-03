package org.knowm.xchange.bitmex.dto.marketdata.results;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BitmexSymbolsAndPromptsResult<V> {

  private final V intervals;
  private final V symbols;

  /**
   * Constructor
   *
   * @param intervals
   * @param symbols
   */

  @JsonCreator
  public BitmexSymbolsAndPromptsResult(@JsonProperty("intervals") V intervals, @JsonProperty("symbols") V symbols) {

    this.intervals = intervals;
    this.symbols = symbols;
  }

  public boolean isSuccess() {

    return symbols.toString().length() != 0;
  }

  public List<V> getIntervals() {

    return (List<V>) intervals;

  }

  public List<V> getSymbols() {

    return (List<V>) symbols;
  }

  @Override
  public String toString() {

    return String.format("BitmexSymbolsAndPromptsResult[%s: %s]", isSuccess() ? "OK" : "error", isSuccess() ? intervals.toString() + " / " + symbols.toString() : "error");
  }

}
