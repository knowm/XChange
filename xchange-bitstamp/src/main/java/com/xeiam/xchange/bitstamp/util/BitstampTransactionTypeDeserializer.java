package com.xeiam.xchange.bitstamp.util;

import com.xeiam.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import com.xeiam.xchange.utils.jackson.EnumIntDeserializer;

/**
 * @author Matija Mazi <br/>
 * @created 3/2/13 12:28 PM
 */
public class BitstampTransactionTypeDeserializer extends EnumIntDeserializer<BitstampUserTransaction.TransactionType> {

  public BitstampTransactionTypeDeserializer() {

    super(BitstampUserTransaction.TransactionType.class);
  }
}
