package com.xeiam.xchange.bitstamp.util;

import si.mazi.rescu.serialization.jackson.serializers.EnumIntDeserializer;

import com.xeiam.xchange.bitstamp.dto.account.WithdrawalRequest;

/**
 * @author Matija Mazi
 */
public class WithdrawalRequestTypeDeserializer extends EnumIntDeserializer<WithdrawalRequest.Type> {

  public WithdrawalRequestTypeDeserializer() {
    super(WithdrawalRequest.Type.class);
  }
}
