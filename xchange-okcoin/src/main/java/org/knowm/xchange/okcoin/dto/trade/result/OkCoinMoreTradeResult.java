package org.knowm.xchange.okcoin.dto.trade.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.okcoin.dto.trade.OkCoinErrorResult;

public class OkCoinMoreTradeResult extends OkCoinErrorResult {

  private final List<Map<String, Object>> orderInfo;
  private final boolean status;

  public OkCoinMoreTradeResult(
      @JsonProperty("result") final boolean result,
      @JsonProperty("error_code") final int errorCode,
      @JsonProperty("order_info") final List<Map<String, Object>> orderInfo) {

    super(result, errorCode);
    this.orderInfo = orderInfo;
    this.status = result;
  }

  public boolean isStatus() {
    return status;
  }

  public List<Map<String, Object>> getOrderInfo() {
    return orderInfo;
  }
}
