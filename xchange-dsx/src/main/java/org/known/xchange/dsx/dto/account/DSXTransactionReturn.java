package org.known.xchange.dsx.dto.account;

import java.util.Map;

import org.known.xchange.dsx.dto.DSXReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXTransactionReturn extends DSXReturn<Map<Long, DSXTransaction>> {

  public DSXTransactionReturn(@JsonProperty("success") boolean success, @JsonProperty("return") Map<Long, DSXTransaction> value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
