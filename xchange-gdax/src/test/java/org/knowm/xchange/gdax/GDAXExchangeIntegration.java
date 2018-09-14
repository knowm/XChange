package org.knowm.xchange.gdax;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.gdax.dto.GDAXTrades;
import org.knowm.xchange.gdax.service.GDAXMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class GDAXExchangeIntegration {

  @Test
  public void coinbaseShouldBeInstantiatedWithoutAnExceptionWhenUsingDefaultSpecification() {

    ExchangeFactory.INSTANCE.createExchange(GDAXExchange.class.getCanonicalName());
  }

  @Test
  public void shouldSupportEthUsdByRemoteInit() throws Exception {

    Exchange ex = ExchangeFactory.INSTANCE.createExchange(GDAXExchange.class.getCanonicalName());
    ex.remoteInit();
    Assert.assertTrue(
        ((GDAXMarketDataServiceRaw) ex.getMarketDataService())
            .checkProductExists(new CurrencyPair("ETH", "USD")));
  }

  @Test
  public void testExtendedGetTrades() throws IOException {
    final MarketDataService marketDataService;
    final GDAXMarketDataServiceRaw marketDataServiceRaw;
    final CurrencyPair currencyPair = new CurrencyPair("BTC", "EUR");
    final Exchange exchange;

    exchange = ExchangeFactory.INSTANCE.createExchange(GDAXExchange.class.getName());
    marketDataService = exchange.getMarketDataService();
    marketDataServiceRaw = (GDAXMarketDataServiceRaw) exchange.getMarketDataService();

    // get latest trades

    GDAXTrades trades1 =
        marketDataServiceRaw.getGDAXTradesExtended(currencyPair, new Long(Integer.MAX_VALUE), null);
    //	  System.out.println(trades1);
    assertEquals("Unexpected trades list length (100)", 100, trades1.size());

    // get latest 10 trades
    GDAXTrades trades2 =
        marketDataServiceRaw.getGDAXTradesExtended(currencyPair, new Long(Integer.MAX_VALUE), 10);
    //	  System.out.println(trades2);
    assertEquals("Unexpected trades list length (10)", 10, trades2.size());

    Trades trades3 = marketDataService.getTrades(currencyPair, new Long(0), new Long(1005));
    //	  trades3.getTrades().stream().forEach(System.out::println);
    assertEquals("Unexpected trades list length (100)", 1004, trades3.getTrades().size());
  }
}
