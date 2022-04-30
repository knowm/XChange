package org.knowm.xchange.zaif.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.zaif.ZaifExchange;

public class MarketDataFetchIntegration {

  @Test
  public void depthFetchTest() throws Exception {
    ExchangeSpecification spec = new ExchangeSpecification(ZaifExchange.class);
    spec.setProxyHost("localhost");
    spec.setProxyPort(1080);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(spec);
//    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(ZaifExchange.class);
    MarketDataService marketDataService = exchange.getMarketDataService();

    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_JPY);
    System.out.println(orderBook.toString());
    assertThat(orderBook).isNotNull();
  }
}
