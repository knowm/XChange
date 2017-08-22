package org.knowm.xchange.hitbtc.v2;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;

public class BaseServiceTest {


  ExchangeSpecification exSpec = new ExchangeSpecification(HitbtcExchange.class);
  Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);


}
