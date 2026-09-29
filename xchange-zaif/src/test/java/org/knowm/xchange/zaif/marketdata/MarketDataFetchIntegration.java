package org.knowm.xchange.zaif.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.zaif.ZaifExchange;

class MarketDataFetchIntegration {

  @Test
  void depthFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(ZaifExchange.class);
    MarketDataService marketDataService = exchange.getMarketDataService();

    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_JPY);
    System.out.println(orderBook.toString());
    assertThat(orderBook).isNotNull();
  }
}
