package org.known.xchange.dsx.dto.trade;

import java.util.Map;

import org.known.xchange.dsx.dto.DSXReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXActiveOrdersReturn extends DSXReturn<Map<Long, DSXActiveOrdersResult>> {

  public DSXActiveOrdersReturn(@JsonProperty("success") boolean success, @JsonProperty("return") Map<Long, DSXActiveOrdersResult> value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
