package com.xeiam.xchange.examples.empoex;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.empoex.EmpoExExchange;

public class EmpoExDemoUtils {

  public static Exchange getExchange() {

    Exchange empoex = ExchangeFactory.INSTANCE.createExchange(EmpoExExchange.class.getName());
    ExchangeSpecification spec = empoex.getDefaultExchangeSpecification();
    spec.setApiKey("");
    empoex.applySpecification(spec);
    return empoex;
  }
}
