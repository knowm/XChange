package org.knowm.xchange.dsx.dto.account;

import org.knowm.xchange.dsx.dto.DSXReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXAccountInfoReturn extends DSXReturn<DSXAccountInfo> {

  /**
   * @param success True if successful
   * @param value The DSX account info
   * @param error Any error
   */
  public DSXAccountInfoReturn(@JsonProperty("success") boolean success, @JsonProperty("return") DSXAccountInfo value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
