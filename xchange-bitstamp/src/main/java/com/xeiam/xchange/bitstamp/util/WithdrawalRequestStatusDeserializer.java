package com.xeiam.xchange.bitstamp.util;

import com.xeiam.xchange.bitstamp.dto.account.WithdrawalRequest;
import com.xeiam.xchange.utils.jackson.EnumIntDeserializer;

/**
 * @author Matija Mazi
 */
public class WithdrawalRequestStatusDeserializer extends EnumIntDeserializer<WithdrawalRequest.Status> {

  public WithdrawalRequestStatusDeserializer() {
    super(WithdrawalRequest.Status.class);
  }
}
