package org.knowm.xchange.dsx.dto.account;

import java.util.Map;

import org.knowm.xchange.dsx.dto.DSXReturn;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mikhail Wall
 */

public class DSXFiatWithdrawReturn extends DSXReturn<Map<Long, DSXFiatWithdraw>> {

  public DSXFiatWithdrawReturn(@JsonProperty("success") boolean success, @JsonProperty("return") Map<Long, DSXFiatWithdraw> value,
      @JsonProperty("error") String error) {

    super(success, value, error);
  }
}
