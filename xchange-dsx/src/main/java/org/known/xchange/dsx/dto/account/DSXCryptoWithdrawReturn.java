package org.known.xchange.dsx.dto.account;

import java.util.Map;

import org.known.xchange.dsx.dto.DSXReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXCryptoWithdrawReturn extends DSXReturn<Map<Long, DSXCryptoWithdraw>> {

  public DSXCryptoWithdrawReturn(@JsonProperty("success") boolean success, @JsonProperty("return") Map<Long, DSXCryptoWithdraw> value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
