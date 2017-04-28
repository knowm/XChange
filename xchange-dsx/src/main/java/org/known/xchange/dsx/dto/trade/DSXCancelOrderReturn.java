package org.known.xchange.dsx.dto.trade;

import java.util.Map;

import org.known.xchange.dsx.dto.DSXReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXCancelOrderReturn extends DSXReturn<Map<Long, DSXCancelOrderResult>> {

  public DSXCancelOrderReturn(@JsonProperty("success") boolean success, @JsonProperty("return") Map<Long, DSXCancelOrderResult> value, String error) {

    super(success, value, error);
  }
}
