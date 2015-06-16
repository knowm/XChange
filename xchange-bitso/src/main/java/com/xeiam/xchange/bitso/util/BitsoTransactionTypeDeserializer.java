package com.xeiam.xchange.bitso.util;


import si.mazi.rescu.serialization.jackson.serializers.EnumIntDeserializer;

import com.xeiam.xchange.bitso.dto.trade.BitsoUserTransaction;

/**
 * @author Piotr Ładyżyński
 */
public class BitsoTransactionTypeDeserializer extends EnumIntDeserializer<BitsoUserTransaction.TransactionType> {

  /**
   * Constructor
   */
  public BitsoTransactionTypeDeserializer() {

    super(BitsoUserTransaction.TransactionType.class);
  }
}
