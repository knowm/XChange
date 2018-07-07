package org.knowm.xchange.livecoin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.ExceptionalReturnContentException;

/**
 * Base of all Livecoin responses
 *
 * <p>Livecoin doesn't signal most errors via HTTP status. In their documentation they say that they
 * set the success flag to true or false. But in some cases a successful response will not have the
 * "success" property at all.
 *
 * @author walec51
 */
@JsonIgnoreProperties({"errorCode", "errorMessage"})
public class LivecoinBaseResponse {

  public LivecoinBaseResponse(@JsonProperty("success") Boolean success) {
    if (success != null && !success) {
      throw new ExceptionalReturnContentException("Success set to false in response");
    }
  }
}
