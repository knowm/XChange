package com.xeiam.xchange.bitso.util;


import com.xeiam.xchange.bitso.dto.trade.BitsoUserTransaction;
import com.xeiam.xchange.utils.jackson.EnumIntDeserializer;

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
