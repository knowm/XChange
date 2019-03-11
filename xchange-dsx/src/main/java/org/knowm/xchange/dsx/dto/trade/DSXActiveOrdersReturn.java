package org.knowm.xchange.dsx.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.dsx.dto.DSXReturn;

/** @author Mikhail Wall */
public class DSXActiveOrdersReturn extends DSXReturn<Map<Long, DSXOrder>> {

  public DSXActiveOrdersReturn(
      @JsonProperty("success") boolean success,
      @JsonProperty("return") Map<Long, DSXOrder> value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
