package org.knowm.xchange.okex.v5.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */
public class OkexException extends HttpStatusExceptionSupport {

  private final String message;

  public OkexException(@JsonProperty("message") String message) {
    super(message);
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
