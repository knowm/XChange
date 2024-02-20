package org.knowm.xchange.kucoin.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.ToString;


/*
 * https://www.kucoin.com/docs/rest/spot-trading/orders/cancel-order-by-clientoid
 */
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderCancelByClientOrderIdResponse {
  private String cancelledOrderId;
  private String clientOid;
}
