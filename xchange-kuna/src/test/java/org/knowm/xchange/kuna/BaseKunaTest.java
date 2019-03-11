package org.knowm.xchange.kuna;

import org.junit.BeforeClass;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;

/** @author Dat Bui */
public class BaseKunaTest {

  private static Exchange exchange;

  @BeforeClass
  public static void beforeClass() {
    exchange = ExchangeFactory.INSTANCE.createExchange(KunaExchange.class.getName());
  }

  protected static Exchange getExchange() {
    return exchange;
  }
}
