package org.knowm.xchange.bittrex.dto.batch.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.knowm.xchange.bittrex.BittrexConstants;

@AllArgsConstructor
@Getter
public enum Operation {
  POST(BittrexConstants.POST),
  DELETE(BittrexConstants.DELETE);

  private final String operation;
}
