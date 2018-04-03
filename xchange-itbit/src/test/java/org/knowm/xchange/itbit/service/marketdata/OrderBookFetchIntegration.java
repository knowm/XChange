package org.knowm.xchange.itbit.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.itbit.v1.ItBitExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author timmolter */
public class OrderBookFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(ItBitExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    OrderBook orderBook = marketDataService.getOrderBook(new CurrencyPair("XBT", "USD"));
    //    System.out.println(orderBook.toString());
    assertThat(orderBook).isNotNull();
  }
}
