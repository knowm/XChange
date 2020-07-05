package org.knowm.xchange.bittrex.dto.batch.order.neworder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.knowm.xchange.bittrex.dto.batch.order.OrderPayload;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NewOrderPayload extends OrderPayload {
  private String marketSymbol;
  private Direction direction;
  private Type type;
  private String quantity;
  private String limit;
  private TimeInForce timeInForce;
}
