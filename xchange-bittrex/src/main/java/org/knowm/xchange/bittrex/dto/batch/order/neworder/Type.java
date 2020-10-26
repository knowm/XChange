package org.knowm.xchange.bittrex.dto.batch.order.neworder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.knowm.xchange.bittrex.BittrexConstants;

@AllArgsConstructor
@Getter
public enum Type {
  LIMIT(BittrexConstants.LIMIT),
  MARKET(BittrexConstants.MARKET),
  CEILING_LIMIT(BittrexConstants.CEILING_LIMIT),
  CEILING_MARKET(BittrexConstants.CEILING_MARKET);

  private final String type;
}
