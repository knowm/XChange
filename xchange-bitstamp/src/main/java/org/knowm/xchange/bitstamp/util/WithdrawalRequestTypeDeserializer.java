package org.knowm.xchange.bitstamp.util;

import org.knowm.xchange.bitstamp.dto.account.WithdrawalRequest;

import si.mazi.rescu.serialization.jackson.serializers.EnumIntDeserializer;

/**
 * @author Matija Mazi
 */
public class WithdrawalRequestTypeDeserializer extends EnumIntDeserializer<WithdrawalRequest.Type> {

  public WithdrawalRequestTypeDeserializer() {
    super(WithdrawalRequest.Type.class);
  }
}
