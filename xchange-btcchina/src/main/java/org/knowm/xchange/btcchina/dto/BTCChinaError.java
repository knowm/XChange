package org.knowm.xchange.btcchina.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ObsessiveOrange
 */
public class BTCChinaError {

  private final int code;
  private final String message;
  private final String id;

  /**
   * Constructor
   */
  public BTCChinaError(@JsonProperty("code") int code, @JsonProperty("message") String message, @JsonProperty("id") String id) {

    this.code = code;
    this.message = message;
    this.id = id;
  }

  /**
   * @return the code
   */
  public int getCode() {

    return code;
  }

  /**
   * @return the message
   */
  public String getMessage() {

    return message;
  }

  /**
   * @return the id
   */
  public String getID() {

    return id;
  }

  @Override
  public String toString() {

    return String.format("BTCChinaError{code=%s, message=%s, id=%s}", code, message, id);
  }

}
