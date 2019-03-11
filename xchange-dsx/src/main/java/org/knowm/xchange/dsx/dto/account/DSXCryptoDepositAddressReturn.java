package org.knowm.xchange.dsx.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.dsx.dto.DSXReturn;

/** @author Mikhail Wall */
public class DSXCryptoDepositAddressReturn extends DSXReturn<DSXCryptoDepositAddress> {

  public DSXCryptoDepositAddressReturn(
      @JsonProperty("success") boolean success,
      @JsonProperty("return") DSXCryptoDepositAddress value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
