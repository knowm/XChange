package org.knowm.xchange.bitmex.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

/** @author Raphael Voellmy */
public class BitmexResult<V> {

  /**
   * Constructor
   *
   * @param result
   * @param error
   */
  @JsonCreator
  public BitmexResult() {}
}
