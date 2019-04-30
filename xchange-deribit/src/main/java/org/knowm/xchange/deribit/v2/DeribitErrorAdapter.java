package org.knowm.xchange.deribit.v2;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.BadRequestException;
import org.knowm.xchange.deribit.v2.dto.DeribitError;
import org.knowm.xchange.deribit.v2.dto.DeribitResponse;
import org.knowm.xchange.exceptions.ExchangeException;
import si.mazi.rescu.HttpStatusIOException;

public class DeribitErrorAdapter {

  public DeribitErrorAdapter() {}

  /** Parse errors from HTTP exceptions */
  public static void adapt(HttpStatusIOException httpStatusException) {

    String httpStatusCode = "HTTP Status code: " + httpStatusException.getHttpStatusCode();

    // if we have a HTTP body try to parse more error details from body
    if (isNotEmpty(httpStatusException.getHttpBody())) {
      ObjectMapper mapper = new ObjectMapper();
      DeribitResponse<Object> response;
      try {
        response = mapper.readValue(httpStatusException.getHttpBody(), DeribitResponse.class);
      } catch (Exception e) {
        // but ignore errors on parsing and throw generic ExchangeException instead
        throw new ExchangeException(httpStatusCode, httpStatusException);
      }

      if (response.getError() != null
          && response.getError().getClass().equals(DeribitError.class)
          && isNotEmpty(response.getError().getMessage())) {

        DeribitError error = response.getError();
        String msg = error.getMessage();

        if (error.getCode() == -11050) {
          throw new BadRequestException(msg);
        }
        if (error.getCode() == -32602) {
          throw new IllegalArgumentException(msg + ": " + error.getData());
        }

        //                if (error.getCode() == ) {
        //                    throw new FundsExceededException(msg + ": " + error.getData());
        //                }
        //                if (error.getCode() == ) {
        //                    throw new FrequencyLimitExceededException(msg + ": " +
        // error.getData());
        //                }

        throw new ExchangeException(msg);
      }
    }
    // else: just throw ExchangeException with causing Exception
    throw new ExchangeException(httpStatusCode, httpStatusException);
  }
}
