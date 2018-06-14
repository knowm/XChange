package org.knowm.xchange.coingi.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coingi.CoingiExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CoingiOrderBookFetchIntegration {

  @Test
  public void orderBookFetchTest() throws Exception {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoingiExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();

    CurrencyPair pair = CurrencyPair.BTC_EUR;
    OrderBook orderBook = marketDataService.getOrderBook(pair);
    assertThat(orderBook).isNotNull();

    List<CurrencyPair> pairs = exchange.getExchangeSymbols();
    assertThat(pairs).contains(pair);
  }
}
