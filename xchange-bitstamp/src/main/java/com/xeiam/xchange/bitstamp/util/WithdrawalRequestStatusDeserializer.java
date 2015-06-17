package com.xeiam.xchange.bitstamp.util;

import si.mazi.rescu.serialization.jackson.serializers.EnumIntDeserializer;

import com.xeiam.xchange.bitstamp.dto.account.WithdrawalRequest;

/**
 * @author Matija Mazi
 */
public class WithdrawalRequestStatusDeserializer extends EnumIntDeserializer<WithdrawalRequest.Status> {

  public WithdrawalRequestStatusDeserializer() {
    super(WithdrawalRequest.Status.class);
  }
}
