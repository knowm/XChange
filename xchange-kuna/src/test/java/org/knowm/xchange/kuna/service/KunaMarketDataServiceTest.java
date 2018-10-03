package org.knowm.xchange.kuna.service;

import org.junit.Before;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.kuna.KunaExchange;

public class KunaMarketDataServiceTest {

  private static Exchange exchange;

  @Before
  public static void beforeClass() {
    exchange = ExchangeFactory.INSTANCE.createExchange(KunaExchange.class.getName());
  }
}
