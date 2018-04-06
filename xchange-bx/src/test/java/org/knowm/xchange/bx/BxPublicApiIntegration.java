package org.knowm.xchange.bx;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BxPublicApiIntegration {

  @Test
  public void getTickerTest() throws IOException {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BxExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("THB", "BTC"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }

  @Test
  public void getExchangeSymbolsTest() {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BxExchange.class.getName());
    List<CurrencyPair> pairs = exchange.getExchangeSymbols();
    System.out.println(Arrays.toString(pairs.toArray()));
    assertThat(pairs).isNotNull();
  }
}
