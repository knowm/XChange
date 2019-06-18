package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class OrderCancellationRequest {

  /**
   * required By providing this parameter, the corresponding order of a designated trading pair will
   * be cancelled. If not providing this parameter, it will be back to error code.
   */
  @JsonProperty("instrument_id")
  private String instrumentId;

  /**
   * optional the order ID created by yourself, The client_oid type should be comprised of alphabets
   * + numbers or only alphabets within 1 – 32 characters， both uppercase and lowercase letters are
   * supported
   */
  @JsonProperty("client_oid")
  private String clientOid;

  /** optional, order ID */
  @JsonProperty("order_id")
  private String orderId;
}
