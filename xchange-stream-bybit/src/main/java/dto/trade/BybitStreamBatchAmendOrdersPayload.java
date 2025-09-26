package dto.trade;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.bybit.dto.BybitCategory;

@AllArgsConstructor
@Getter
@Setter
public class BybitStreamBatchAmendOrdersPayload {
  BybitCategory category;
  List<BybitStreamBatchAmendOrderPayload> request;

  @AllArgsConstructor
  @Getter
  public static class BybitStreamBatchAmendOrderPayload {
    String symbol;
    String orderId;
    String orderLinkId;
    String triggerPrice;
    String qty;
    String price;
    String tpslMode;
    String takeProfit;
    String stopLoss;
    String tpTriggerBy;
    String slTriggerBy;
    String triggerBy;
    String tpLimitPrice;
    String slLimitPrice;
  }
}
