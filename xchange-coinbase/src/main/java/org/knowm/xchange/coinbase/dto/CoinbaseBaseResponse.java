package org.knowm.xchange.coinbase.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** @author jamespedwards42 */
public class CoinbaseBaseResponse {

  private final boolean success;
  private final List<String> errors;

  protected CoinbaseBaseResponse(
      @JsonProperty("success") final boolean success,
      @JsonProperty("errors") final List<String> errors) {

    this.success = success;
    this.errors = errors;
  }

  protected CoinbaseBaseResponse(boolean success) {

    this.success = success;
    this.errors = null;
  }

  @JsonIgnore
  public boolean isSuccess() {

    return success;
  }

  @JsonIgnore
  public List<String> getErrors() {

    return errors;
  }

  @Override
  public String toString() {

    return "CoinbasePostResponse [success=" + success + ", errors=" + errors + "]";
  }
}
