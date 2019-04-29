package org.knowm.xchange.deribit.v2.service.marketdata;

import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitOrderBook;
import org.knowm.xchange.deribit.v2.service.DeribitMarketDataService;
import org.knowm.xchange.dto.marketdata.OrderBook;

import static org.assertj.core.api.Assertions.assertThat;

public class DeribitOrderBookFetchIntegration {

  private static Exchange exchange;
  private static DeribitMarketDataService deribitMarketDataService;

  @BeforeClass
  public static void setUp() {
    exchange = ExchangeFactory.INSTANCE.createExchange(DeribitExchange.class);
    exchange.applySpecification(((DeribitExchange) exchange).getSandboxExchangeSpecification());
    deribitMarketDataService = (DeribitMarketDataService) exchange.getMarketDataService();
  }

  @Test
  public void getDeribitOrderBookTest() throws Exception {
    DeribitOrderBook orderBook = deribitMarketDataService.getDeribitOrderBook("BTC-PERPETUAL");

    assertThat(orderBook).isNotNull();
  }

  @Test
  public void getOrderBookTest() throws Exception {
    CurrencyPair pair = new CurrencyPair("BTC", "PERPETUAL");
    OrderBook orderBook = deribitMarketDataService.getOrderBook(pair, null);

    assertThat(orderBook).isNotNull();
    assertThat(orderBook.getBids()).isNotEmpty();
    assertThat(orderBook.getAsks()).isNotEmpty();
  }
}
