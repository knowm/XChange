package org.knowm.xchange.dsx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.dsx.dto.DSXReturn;

/** @author Mikhail Wall */
public class DSXTransHistoryReturn extends DSXReturn<Map<Long, DSXTransHistoryResult>> {

  public DSXTransHistoryReturn(
      @JsonProperty("success") boolean success,
      @JsonProperty("return") Map<Long, DSXTransHistoryResult> value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
