package org.knowm.xchange.dsx.dto.account;

import org.knowm.xchange.dsx.dto.DSXReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXCryptoWithdrawReturn extends DSXReturn<DSXCryptoWithdraw> {

  public DSXCryptoWithdrawReturn(@JsonProperty("success") boolean success, @JsonProperty("return") DSXCryptoWithdraw value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
