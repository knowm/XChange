package org.knowm.xchange.krakenfutures.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

/** @author Jean-Christophe Laruelle */
@Data
public class KrakenFuturesResult {

  private final String result;
  private final String error;

  /**
   * Constructor
   *
   * @param result
   * @param error
   */
  @JsonCreator
  public KrakenFuturesResult(String result, String error) {
    this.result = result;
    this.error = error;
  }

  public boolean isSuccess() {
    return result.equalsIgnoreCase("success");
  }

  @Override
  public String toString() {

    if (isSuccess()) return result;
    else return result + " : " + error;
  }
}
