package org.knowm.xchange.coinmarketcap.pro.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.CoinMarketCapResult;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.exceptions.FrequencyLimitExceededException;
import org.knowm.xchange.exceptions.FundsExceededException;
import si.mazi.rescu.HttpStatusIOException;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;


public final class CoinMarketCapErrorAdapter {

  private CoinMarketCapErrorAdapter() {}

  /** Parse errors from HTTP exceptions */
  public static <R> R adapt(HttpStatusIOException httpStatusException) {

    String msg = "HTTP Status: " + httpStatusException.getHttpStatusCode();

    // if we have a HTTP body try to parse more error details from body
    if (isNotEmpty(httpStatusException.getHttpBody())) {
      ObjectMapper mapper = new ObjectMapper();
      CoinMarketCapResult<R> result;
      try {
        result = mapper.readValue(httpStatusException.getHttpBody(), CoinMarketCapResult.class);
      } catch (Exception e1) {
        // but ignore errors on parsing and throw generic ExchangeException instead
        throw new ExchangeException(msg, httpStatusException);
      }
      // but if it contains a parsable result, then try to parse errors from result:
      if (result.getStatus() != null
              && isNotEmpty(result.getStatus().getErrorMessage())
              && !result.isSuccess()) {
        return checkResult(result);
      }
    }
    // else: just throw ExchangeException with causing Exception
    throw new ExchangeException(msg, httpStatusException);
  }

  /** Check results for errors */
  private static <R> R checkResult(CoinMarketCapResult<R> result) {

    if (!result.isSuccess()) {

      String error = result.getStatus().getErrorMessage();
      String msg = error + " - ErrorCode: " + result.getStatus().getErrorCode();

      if (result.getStatus().getErrorCode() == 401) {
        throw new ExchangeSecurityException(error);
      }
      if (result.getStatus().getErrorCode() == 402) {
        throw new FundsExceededException(error);
      }
      if (result.getStatus().getErrorCode() == 429) {
        throw new FrequencyLimitExceededException(error);
      }
      throw new ExchangeException(msg);
    }
    return result.getData();
  }

}