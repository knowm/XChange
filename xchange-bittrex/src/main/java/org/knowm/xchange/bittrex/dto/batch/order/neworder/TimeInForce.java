package org.knowm.xchange.bittrex.dto.batch.order.neworder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.knowm.xchange.bittrex.BittrexConstants;

@AllArgsConstructor
@Getter
public enum TimeInForce {
  GOOD_TIL_CANCELLED(BittrexConstants.GOOD_TIL_CANCELLED),
  IMMEDIATE_OR_CANCEL(BittrexConstants.IMMEDIATE_OR_CANCEL),
  FILL_OR_KILL(BittrexConstants.FILL_OR_KILL),
  POST_ONLY_GOOD_TIL_CANCELLED(BittrexConstants.POST_ONLY_GOOD_TIL_CANCELLED),
  BUY_NOW(BittrexConstants.BUY_NOW);

  private final String timeInForce;
}
