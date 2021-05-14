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
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BxExchange.class);
    MarketDataService marketDataService = exchange.getMarketDataService();
    CurrencyPair pair = new CurrencyPair("THB", "BTC");
    Ticker ticker = marketDataService.getTicker(pair);
    System.out.println(ticker);
    assertThat(ticker.getCurrencyPair()).isEqualTo(pair);
    assertThat(ticker.getLast()).isPositive();
    assertThat(ticker.getBid()).isPositive();
    assertThat(ticker.getAsk()).isPositive();
    assertThat(ticker.getVolume()).isPositive();
  }

  @Test
  public void getExchangeSymbolsTest() {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BxExchange.class);
    List<CurrencyPair> pairs = exchange.getExchangeSymbols();
    System.out.println(Arrays.toString(pairs.toArray()));
    assertThat(pairs).isNotNull();
  }
}
