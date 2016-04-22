package org.knowm.xchange.clevercoin.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Karsten Nilsen
 */
public final class CleverCoinCancelOrder {

  private final String result;
  private final String errorMessage;

  /**
   * Constructor
   * 
   * @param result
   */
  public CleverCoinCancelOrder(@JsonProperty("result") String result,
      @JsonProperty("error") @JsonDeserialize(using = CleverCoinErrorDeserializer.class) String errorMessage) {

    this.result = result;
    this.errorMessage = errorMessage;
  }

  public String getResult() {

    return result;
  }

  @JsonIgnore
  public String getErrorMessage() {

    return errorMessage;
  }

  @Override
  public String toString() {

    return errorMessage != null ? errorMessage : String.format("Result=%s", result);
  }
}
