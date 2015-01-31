package com.xeiam.xchange.itbit.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.itbit.v1.ItBitExchange;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

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
