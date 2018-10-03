package org.knowm.xchange.huobi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class HuobiPublicApiIntegration {

  @Test
  public void getTickerTest() throws Exception {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(HuobiExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "USDT"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }

  @Test
  public void getExchangeSymbolsTest() {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(HuobiExchange.class.getName());
    System.out.println(Arrays.toString(exchange.getExchangeSymbols().toArray()));
  }
}
