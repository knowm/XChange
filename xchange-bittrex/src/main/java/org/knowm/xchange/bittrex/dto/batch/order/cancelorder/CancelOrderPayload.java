package org.knowm.xchange.bittrex.dto.batch.order.cancelorder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.knowm.xchange.bittrex.dto.batch.order.OrderPayload;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CancelOrderPayload extends OrderPayload {
  private String id;
}
