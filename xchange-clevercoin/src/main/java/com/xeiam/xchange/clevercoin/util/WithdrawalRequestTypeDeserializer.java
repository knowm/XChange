package com.xeiam.xchange.clevercoin.util;

import si.mazi.rescu.serialization.jackson.serializers.EnumIntDeserializer;

import com.xeiam.xchange.clevercoin.dto.account.WithdrawalRequest;

/**
 * @author Karsten Nilsen
 */
public class WithdrawalRequestTypeDeserializer extends EnumIntDeserializer<WithdrawalRequest.Type> {

  public WithdrawalRequestTypeDeserializer() {
    super(WithdrawalRequest.Type.class);
  }
}
