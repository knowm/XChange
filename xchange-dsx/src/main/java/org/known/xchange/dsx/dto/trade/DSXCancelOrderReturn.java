package org.known.xchange.dsx.dto.trade;

import org.known.xchange.dsx.dto.DSXReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXCancelOrderReturn extends DSXReturn<DSXCancelOrderResult> {

  public DSXCancelOrderReturn(@JsonProperty("success") boolean success, @JsonProperty("return") DSXCancelOrderResult value, String error) {

    super(success, value, error);
  }
}
