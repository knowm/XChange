package org.knowm.xchange.blockchain;

import static org.knowm.xchange.blockchain.BlockchainConstants.EXCEPTION_MESSAGE;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.blockchain.dto.BlockchainException;
import org.knowm.xchange.exceptions.*;

/** @author scuevas */
@UtilityClass
public final class BlockchainErrorAdapter {

  public static ExchangeException adapt(BlockchainException e) {
    String message = e.getMessage();
    if (StringUtils.isEmpty(message)) {
      message = EXCEPTION_MESSAGE;
    }
    switch (e.getStatus()) {
      case 401:
        return new ExchangeSecurityException(message, e);
      case 404:
        return new RateLimitExceededException(message, e);
      case 500:
        return new InternalServerException(message, e);
      default:
        return new ExchangeException(message, e);
    }
  }
}
