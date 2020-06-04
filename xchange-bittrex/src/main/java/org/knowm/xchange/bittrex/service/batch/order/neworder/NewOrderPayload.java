package org.knowm.xchange.bittrex.service.batch.order.neworder;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.knowm.xchange.bittrex.service.batch.order.OrderPayload;

@Data
@AllArgsConstructor
public class NewOrderPayload extends OrderPayload {
  private String marketSymbol;
  private Direction direction;
  private Type type;
  private String quantity;
  private String limit;
  private TimeInForce timeInForce;
}
