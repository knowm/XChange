package org.knowm.xchange.bittrex.service.batch.order.cancelorder;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.knowm.xchange.bittrex.service.batch.order.OrderPayload;

@Data
@AllArgsConstructor
public class CancelOrderPayload extends OrderPayload {
  private String id;
}
