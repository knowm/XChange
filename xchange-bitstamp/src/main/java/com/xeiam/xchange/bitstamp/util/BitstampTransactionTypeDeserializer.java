package com.xeiam.xchange.bitstamp.util;

import com.xeiam.xchange.bitstamp.dto.trade.BitstampUserTransaction;

import si.mazi.rescu.serialization.jackson.serializers.EnumIntDeserializer;

/**
 * @author Matija Mazi
 */
public class BitstampTransactionTypeDeserializer extends EnumIntDeserializer<BitstampUserTransaction.TransactionType> {

  /**
   * Constructor
   */
  public BitstampTransactionTypeDeserializer() {

    super(BitstampUserTransaction.TransactionType.class);
  }
}
