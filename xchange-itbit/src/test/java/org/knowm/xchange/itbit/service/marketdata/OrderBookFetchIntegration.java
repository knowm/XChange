package org.knowm.xchange.itbit.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.itbit.v1.ItBitExchange;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * @author timmolter
 */
public class OrderBookFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(ItBitExchange.class.getName());
    PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();
    OrderBook orderBook = marketDataService.getOrderBook(new CurrencyPair("XBT", "USD"));
    //    System.out.println(orderBook.toString());
    assertThat(orderBook).isNotNull();

  }

}
