package org.knowm.xchange.dase.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.dase.DaseExchange;
import org.knowm.xchange.dase.dto.marketdata.DaseTrade;

/**
 * Public trades with limit parameter smoke test. Run with: mvn clean verify
 * -DskipIntegrationTests=false
 */
public class DaseTradesLimitIntegration {

  private static final String DEFAULT_MARKET = "BTC-CZK";

  @Test
  public void trades_with_limit_live() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    DaseMarketDataServiceRaw raw = (DaseMarketDataServiceRaw) ex.getMarketDataService();

    Integer limit = 10;
    String before = null;
    List<DaseTrade> trades = raw.getTrades(DEFAULT_MARKET, limit, before);
    assertNotNull(trades);
  }
}
