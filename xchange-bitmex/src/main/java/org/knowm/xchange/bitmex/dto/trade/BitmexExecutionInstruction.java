package org.knowm.xchange.bitmex.dto.trade;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public enum BitmexExecutionInstruction {
  PARTICIPATE_DO_NOT_INITIATE("ParticipateDoNotInitiate"),
  ALL_OR_NONE("AllOrNone"),
  MARK_PRICE("MarkPrice"),
  INDEX_PRICE("IndexPrice"),
  LAST_PRICE("LastPrice"),
  CLOSE("Close"),
  REDUCE_ONLY("ReduceOnly"),
  FIXED("Fixed");

  private String apiParameter;

  BitmexExecutionInstruction(String apiParameter) {
    this.apiParameter = apiParameter;
  }

  public String toApiParameter() {
    return apiParameter;
  }

  public static List<BitmexExecutionInstruction> fromParameter(
      final boolean postOnly, final boolean reduceOnly) {
    final ArrayList<BitmexExecutionInstruction> result = new ArrayList<>();
    if (postOnly) {
      result.add(PARTICIPATE_DO_NOT_INITIATE);
    }
    if (reduceOnly) {
      result.add(REDUCE_ONLY);
    }
    return result;
  }
}
