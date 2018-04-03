package org.knowm.xchange.dsx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.dsx.dto.DSXReturn;

/** @author Mikhail Wall */
public class DSXFiatWithdrawReturn extends DSXReturn<DSXFiatWithdraw> {

  public DSXFiatWithdrawReturn(
      @JsonProperty("success") boolean success,
      @JsonProperty("return") DSXFiatWithdraw value,
      @JsonProperty("error") String error) {
    super(success, value, error);
  }
}
