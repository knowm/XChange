package org.knowm.xchange.upbit.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.upbit.UpbitExchange;

public class OrderBookIntegration {
  @Test
  public void orderBookTest() throws Exception {
    ExchangeSpecification exSpec = new ExchangeSpecification(UpbitExchange.class);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
    MarketDataService marketDataService = exchange.getMarketDataService();
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.ETH_BTC);
    assertThat(orderBook).isNotNull();
  }
}
