package com.xeiam.xchange.bitstamp.util;

import com.xeiam.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import com.xeiam.xchange.utils.jackson.EnumIntDeserializer;

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
