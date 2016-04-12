package org.knowm.xchange.bitstamp.util;

import org.knowm.xchange.bitstamp.dto.account.WithdrawalRequest;

import si.mazi.rescu.serialization.jackson.serializers.EnumIntDeserializer;

/**
 * @author Matija Mazi
 */
public class WithdrawalRequestStatusDeserializer extends EnumIntDeserializer<WithdrawalRequest.Status> {

  public WithdrawalRequestStatusDeserializer() {
    super(WithdrawalRequest.Status.class);
  }
}
