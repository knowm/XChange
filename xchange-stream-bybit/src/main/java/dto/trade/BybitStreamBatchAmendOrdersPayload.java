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
  private final BybitCategory category;
  private final List<BybitStreamBatchAmendOrderPayload> request;

  @AllArgsConstructor
  @Getter
  public static class BybitStreamBatchAmendOrderPayload {
    private final String symbol;
    private final String orderId;
    private final String orderLinkId;
    private final String triggerPrice;
    private final String qty;
    private final String price;
    private final String tpslMode;
    private final String takeProfit;
    private final String stopLoss;
    private final String tpTriggerBy;
    private final String slTriggerBy;
    private final String triggerBy;
    private final String tpLimitPrice;
    private final String slLimitPrice;
  }
}
