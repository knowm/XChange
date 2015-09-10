package com.xeiam.xchange.clevercoin.util;

import com.xeiam.xchange.clevercoin.dto.account.WithdrawalRequest;

import si.mazi.rescu.serialization.jackson.serializers.EnumIntDeserializer;

/**
 * @author Karsten Nilsen
 */
public class WithdrawalRequestStatusDeserializer extends EnumIntDeserializer<WithdrawalRequest.Status> {

  public WithdrawalRequestStatusDeserializer() {
    super(WithdrawalRequest.Status.class);
  }
}
