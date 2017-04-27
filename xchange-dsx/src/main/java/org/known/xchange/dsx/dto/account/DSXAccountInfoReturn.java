package org.known.xchange.dsx.dto.account;

import org.known.xchange.dsx.dto.DSXReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXAccountInfoReturn extends DSXReturn<DSXAccountInfo> {


  public DSXAccountInfoReturn(@JsonProperty("success") boolean success, @JsonProperty("return") DSXAccountInfo value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
