package org.knowm.xchange.bittrex.dto.batch.order.neworder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.knowm.xchange.bittrex.BittrexConstants;

@AllArgsConstructor
@Getter
public enum Direction {
  BUY(BittrexConstants.BUY),
  SELL(BittrexConstants.SELL);

  private final String direction;
}
