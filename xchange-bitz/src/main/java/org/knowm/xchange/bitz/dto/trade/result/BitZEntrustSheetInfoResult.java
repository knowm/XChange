package org.knowm.xchange.bitz.dto.trade.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bitz.dto.BitZResult;
import org.knowm.xchange.bitz.dto.trade.BitZUserHistory;

public class BitZEntrustSheetInfoResult extends BitZResult<BitZUserHistory> {
  public BitZEntrustSheetInfoResult(
      @JsonProperty("status") int status,
      @JsonProperty("msg") String message,
      @JsonProperty("data") BitZUserHistory data,
      @JsonProperty("time") String time,
      @JsonProperty("microtime") String microtime,
      @JsonProperty("source") String source) {
    super(status, message, data);
  }
}
