package org.knowm.xchange.btcmarkets.service;

import org.junit.Before;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btcmarkets.BTCMarkets;
import org.knowm.xchange.btcmarkets.BTCMarketsAuthenticated;
import org.knowm.xchange.btcmarkets.BTCMarketsAuthenticatedV3;
import org.knowm.xchange.btcmarkets.BTCMarketsExchange;
import org.mockito.Mockito;

abstract class BTCMarketsServiceTest extends BTCMarketsTestSupport {
  protected BTCMarketsExchange exchange;
  protected BTCMarketsTradeService btcMarketsTradeService;
  protected BTCMarketsAccountService btcMarketsAccountService;
  protected BTCMarketsMarketDataService btcMarketsMarketDataService;

  protected BTCMarketsAuthenticatedV3 btcMarketsAuthenticatedV3;
  protected BTCMarketsAuthenticated btcMarketsAuthenticated;
  protected BTCMarkets btcMarkets;

  @Before
  public void setUp() throws Exception {
    ExchangeSpecification specification =
        ExchangeFactory.INSTANCE
            .createExchange(BTCMarketsExchange.class)
            .getDefaultExchangeSpecification();
    specification.setApiKey(SPECIFICATION_API_KEY);
    specification.setSecretKey(SPECIFICATION_SECRET_KEY);
    exchange = (BTCMarketsExchange) ExchangeFactory.INSTANCE.createExchange(specification);

    btcMarketsTradeService = (BTCMarketsTradeService) exchange.getTradeService();
    btcMarketsAccountService = (BTCMarketsAccountService) exchange.getAccountService();
    btcMarketsMarketDataService = (BTCMarketsMarketDataService) exchange.getMarketDataService();

    btcMarketsAuthenticatedV3 = Mockito.mock(BTCMarketsAuthenticatedV3.class);
    btcMarketsAuthenticated = Mockito.mock(BTCMarketsAuthenticated.class);
    btcMarkets = Mockito.mock(BTCMarkets.class);

    setMock(
        BTCMarketsBaseService.class.getDeclaredField("btcmv3"),
        btcMarketsTradeService,
        btcMarketsAuthenticatedV3);
    setMock(
        BTCMarketsBaseService.class.getDeclaredField("btcm"),
        btcMarketsTradeService,
        btcMarketsAuthenticated);

    setMock(
        BTCMarketsBaseService.class.getDeclaredField("btcmv3"),
        btcMarketsAccountService,
        btcMarketsAuthenticatedV3);
    setMock(
        BTCMarketsBaseService.class.getDeclaredField("btcm"),
        btcMarketsAccountService,
        btcMarketsAuthenticated);

    setMock(
        BTCMarketsBaseService.class.getDeclaredField("btcmPublic"),
        btcMarketsMarketDataService,
        btcMarkets);
  }
}
