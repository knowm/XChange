package org.knowm.xchange.examples.independentreserve;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.independentreserve.IndependentReserveExchange;

/** Author: Kamil Zbikowski Date: 4/10/15 */
public class IndependentReserveDemoUtils {
  public static Exchange createExchange() {

    ExchangeSpecification exSpec =
        new IndependentReserveExchange().getDefaultExchangeSpecification();
    exSpec.setApiKey("dfdafa-d483-43fafdafda58-bd02-fdfdafda");
    exSpec.setSecretKey("dfgd34523ffsgsgfs3693dgfsgfsgfsg34f");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
