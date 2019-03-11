package org.knowm.xchange.dsx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import org.knowm.xchange.dsx.dto.DSXReturn;

/** @author Mikhail Wall */
public class DSXTransactionStatusReturn extends DSXReturn<Map<Long, DSXTransactionStatus>> {

  public DSXTransactionStatusReturn(
      @JsonProperty("success") boolean success,
      @JsonProperty("return") Map<Long, DSXTransactionStatus> value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
