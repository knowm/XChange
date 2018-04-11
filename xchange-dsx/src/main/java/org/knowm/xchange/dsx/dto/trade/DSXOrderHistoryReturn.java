package org.knowm.xchange.dsx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.dsx.dto.DSXReturn;

/** @author Mikhail Wall */
public class DSXOrderHistoryReturn extends DSXReturn<Map<Long, DSXOrderHistoryResult>> {

  public DSXOrderHistoryReturn(
      @JsonProperty("success") boolean success,
      @JsonProperty("return") Map<Long, DSXOrderHistoryResult> value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
