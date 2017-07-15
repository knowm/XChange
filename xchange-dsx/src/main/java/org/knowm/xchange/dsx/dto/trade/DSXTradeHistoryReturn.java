package org.knowm.xchange.dsx.dto.trade;

import java.util.Map;

import org.knowm.xchange.dsx.dto.DSXReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXTradeHistoryReturn extends DSXReturn<Map<Long, DSXTradeHistoryResult>> {

  public DSXTradeHistoryReturn(@JsonProperty("success") boolean success, @JsonProperty("return") Map<Long, DSXTradeHistoryResult> value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
