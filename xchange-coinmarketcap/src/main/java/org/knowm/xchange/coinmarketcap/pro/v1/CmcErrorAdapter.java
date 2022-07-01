package org.knowm.xchange.coinmarketcap.pro.v1;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.CmcResult;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.exceptions.FrequencyLimitExceededException;
import org.knowm.xchange.exceptions.FundsExceededException;
import si.mazi.rescu.HttpStatusIOException;

public final class CmcErrorAdapter {

  private CmcErrorAdapter() {}

  /** Parse errors from HTTP exceptions */
  public static void adapt(HttpStatusIOException httpStatusException) {

    String msg = "HTTP Status: " + httpStatusException.getHttpStatusCode();

    // if we have a HTTP body try to parse more error details from body
    if (isNotEmpty(httpStatusException.getHttpBody())) {
      ObjectMapper mapper = new ObjectMapper();
      CmcResult result;
      try {
        result = mapper.readValue(httpStatusException.getHttpBody(), CmcResult.class);
      } catch (Exception e) {
        // but ignore errors on parsing and throw generic ExchangeException instead
        throw new ExchangeException(msg, httpStatusException);
      }
      // but if it contains a parsable result, then try to parse errors from result:
      if (result.getStatus() != null
          && isNotEmpty(result.getStatus().getErrorMessage())
          && !result.isSuccess()) {

        String error = result.getStatus().getErrorMessage();
        if (result.getStatus().getErrorCode() == 401) {
          throw new ExchangeSecurityException(error);
        }
        if (result.getStatus().getErrorCode() == 402) {
          throw new FundsExceededException(error);
        }
        if (result.getStatus().getErrorCode() == 429) {
          throw new FrequencyLimitExceededException(error);
        }

        msg = error + " - ErrorCode: " + result.getStatus().getErrorCode();
        throw new ExchangeException(msg);
      }
    }
    // else: just throw ExchangeException with causing Exception
    throw new ExchangeException(msg, httpStatusException);
  }
}
