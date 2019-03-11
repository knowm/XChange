package org.knowm.xchange.ripple.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import si.mazi.rescu.HttpStatusExceptionSupport;

/* Examples of rejects observed:

{  "success": false,  "error": "restRIPPLED_NETWORK_ERR",  "message": "Cannot connect to rippled",  "error_type": "connection"}

{"success": false, "error_type": "invalid_request",
"message": "Parameter is not a valid Ripple address: account", "error": "restINVALID_PARAMETER"}

{"success": false, "error_type": "invalid_request",
"message": "Invalid parameter: counter. Must be a currency string in the form currency+counterparty", "error": "restINVALID_PARAMETER"}

{"success": false, "error": "Cannot read property 'currency' of undefined", "error_type": "transaction"}

*/

public class RippleException extends HttpStatusExceptionSupport {

  @JsonProperty("message")
  private String message;

  @JsonProperty("error")
  private String error;

  @JsonProperty("error_type")
  private String errorType;

  @Override
  public String getMessage() {
    return message;
  }

  public String getError() {
    return error;
  }

  public String getErrorType() {
    return errorType;
  }

  @Override
  public String toString() {
    return String.format(
        "%s errorType[%s] error[%s] message[%s]", super.toString(), errorType, error, message);
  }
}
