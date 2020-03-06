package org.knowm.xchange.service.trade;

import java.io.IOException;
import org.knowm.xchange.dto.trade.OpenPositions;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

public interface FutureTradeService {

  /** Get all openPositions of the exchange */
  default OpenPositions getOpenPositions() throws IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
