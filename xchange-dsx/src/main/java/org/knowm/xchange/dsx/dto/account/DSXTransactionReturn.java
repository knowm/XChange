package org.knowm.xchange.dsx.dto.account;

import java.util.List;
import java.util.Map;

import org.knowm.xchange.dsx.dto.DSXReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXTransactionReturn extends DSXReturn<List<DSXTransaction>> {

  public DSXTransactionReturn(@JsonProperty("success") boolean success, @JsonProperty("return") Map<String, List<DSXTransaction>> value,
      @JsonProperty("error") String error) {

    super(success, value.get("transactions"), error);
  }
}
