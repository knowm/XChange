package org.knowm.xchange.dsx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.dsx.dto.DSXReturn;

/** @author Mikhail Wall */
public class DSXCryptoWithdrawReturn extends DSXReturn<DSXCryptoWithdraw> {

  public DSXCryptoWithdrawReturn(
      @JsonProperty("success") boolean success,
      @JsonProperty("return") DSXCryptoWithdraw value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
