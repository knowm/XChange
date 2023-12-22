package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.knowm.xchange.okcoin.v3.service.OkexException;

@Data
@EqualsAndHashCode(callSuper = false)
public class SwapFuturesMultipleOrderPlacementResponse extends OkexResponse {

  /** Order Details */
  @JsonProperty("order_info")
  private List<FuturesMultipleOrderPlacementEntry> orderInfo;

  @Data
  public static class FuturesMultipleOrderPlacementEntry {

    /** Order ID. When failing to place an order, the value is -1 */
    @JsonProperty("order_id")
    private String orderId;

    /** To identify your order with the order ID set by you */
    @JsonProperty("client_oid")
    private String clientOid;

    /** Error code for order placement. Success = 0. Failure = error code */
    @JsonProperty("error_code")
    private String errorCode;

    /**
     * It will be blank if order placement is success. Error message will be returned if order
     * placement fails.
     */
    @JsonProperty("error_message")
    private String errorMessage;

    public String getOrderIdOrFail() {
      if ("0".equals(errorCode)) {
        return orderId;
      }
      OkexException e = new OkexException();
      e.setCode(errorCode);
      e.setMessage(errorMessage);
      throw e;
    }
  }
}
