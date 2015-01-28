package com.xeiam.xchange.bitstamp.util;

import com.xeiam.xchange.bitstamp.dto.account.WithdrawalRequest;
import com.xeiam.xchange.utils.jackson.EnumIntDeserializer;

/**
 * @author Matija Mazi
 */
public class WithdrawalRequestTypeDeserializer extends EnumIntDeserializer<WithdrawalRequest.Type> {

  public WithdrawalRequestTypeDeserializer() {
    super(WithdrawalRequest.Type.class);
  }
}
