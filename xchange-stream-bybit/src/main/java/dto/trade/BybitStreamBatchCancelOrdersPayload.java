package dto.trade;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.bybit.dto.BybitCategory;

@AllArgsConstructor
@Getter
@Setter
public class BybitStreamBatchCancelOrdersPayload {

  private final BybitCategory category;
  private final List<BybitStreamBatchCancelOrderPayload> request;

  @AllArgsConstructor
  @Getter
  public static class BybitStreamBatchCancelOrderPayload {

    private final String symbol;
    private final String orderId;
    private final String orderLinkId;
  }
}
