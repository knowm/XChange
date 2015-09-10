package com.xeiam.xchange.examples.independentreserve;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.independentreserve.IndependentReserveExchange;

/**
 * Author: Kamil Zbikowski Date: 4/10/15
 */
public class IndependentReserveDemoUtils {
  public static Exchange createExchange() {

    ExchangeSpecification exSpec = new IndependentReserveExchange().getDefaultExchangeSpecification();
    exSpec.setApiKey("dfdafa-d483-43fafdafda58-bd02-fdfdafda");
    exSpec.setSecretKey("dfgd34523ffsgsgfs3693dgfsgfsgfsg34f");
    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }
}
