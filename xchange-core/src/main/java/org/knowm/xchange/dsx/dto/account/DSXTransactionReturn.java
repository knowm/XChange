package org.knowm.xchange.dsx.dto.account;

import org.knowm.xchange.dsx.dto.DSXReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXTransactionReturn extends DSXReturn<DSXTransaction> {

  public DSXTransactionReturn(@JsonProperty("success") boolean success, @JsonProperty("return") DSXTransaction value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
