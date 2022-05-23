package org.knowm.xchange.blockchain;

import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.blockchain.dto.BlockchainException;
import org.knowm.xchange.exceptions.*;

/** @author walec51 */
public final class BlockchainErrorAdapter {

  private BlockchainErrorAdapter() {}

  public static ExchangeException adapt(BlockchainException e) {
    String message = e.getMessage();
    if (StringUtils.isEmpty(message)) {
      message = "Operation failed without any error message";
    }
    switch (e.getStatus()) {
      case 401:
        return new ExchangeSecurityException(message, e);
      case 404:
        return new RateLimitExceededException(message, e);
      default:
        return new ExchangeException(message, e);
    }
  }
}
