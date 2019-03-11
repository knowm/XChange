package org.knowm.xchange.quadrigacx.util;

import org.knowm.xchange.quadrigacx.dto.trade.QuadrigaCxUserTransaction;
import si.mazi.rescu.serialization.jackson.serializers.EnumIntDeserializer;

public class QuadrigaCxTransactionTypeDeserializer
    extends EnumIntDeserializer<QuadrigaCxUserTransaction.TransactionType> {

  /** Constructor */
  public QuadrigaCxTransactionTypeDeserializer() {

    super(QuadrigaCxUserTransaction.TransactionType.class);
  }
}
