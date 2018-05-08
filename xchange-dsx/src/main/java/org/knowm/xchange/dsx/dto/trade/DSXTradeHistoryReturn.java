package org.knowm.xchange.dsx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.dsx.dto.DSXReturn;

/** @author Mikhail Wall */
public class DSXTradeHistoryReturn extends DSXReturn<Map<Long, DSXTradeHistoryResult>> {

  public DSXTradeHistoryReturn(
      @JsonProperty("success") boolean success,
      @JsonProperty("return") Map<Long, DSXTradeHistoryResult> value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
