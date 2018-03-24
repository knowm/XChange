package org.knowm.xchange.dsx.dto.trade;

import java.util.Map;

import org.knowm.xchange.dsx.dto.DSXReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXTransHistoryReturn extends DSXReturn<Map<Long, DSXTransHistoryResult>> {

  public DSXTransHistoryReturn(@JsonProperty("success") boolean success, @JsonProperty("return") Map<Long, DSXTransHistoryResult> value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
