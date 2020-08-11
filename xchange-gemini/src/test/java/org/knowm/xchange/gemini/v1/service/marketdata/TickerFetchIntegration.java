package org.knowm.xchange.gemini.v1.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.Arrays;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.gemini.v1.GeminiExchange;
import org.knowm.xchange.gemini.v1.service.GeminiMarketDataServiceRaw;
import org.knowm.xchange.gemini.v2.dto.marketdata.GeminiCandle;
import org.knowm.xchange.gemini.v2.dto.marketdata.GeminiTicker2;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author timmolter */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(GeminiExchange.class.getName());
    MarketDataService marketDataService = exchange.getMarketDataService();
    Ticker ticker = marketDataService.getTicker(new CurrencyPair("BTC", "USD"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();
  }

  @Test
  public void candleFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(GeminiExchange.class.getName());
    GeminiMarketDataServiceRaw mds = (GeminiMarketDataServiceRaw) exchange.getMarketDataService();
    GeminiCandle[] candles = mds.getCandles(CurrencyPair.BTC_USD, Duration.ofHours(1));
    System.out.println(Arrays.toString(candles));
    assertThat(candles).hasSizeBetween(1000, 2000);
  }

  @Test
  public void ticker2FetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(GeminiExchange.class.getName());
    GeminiMarketDataServiceRaw mds = (GeminiMarketDataServiceRaw) exchange.getMarketDataService();
    GeminiTicker2 ticker = mds.getTicker2(CurrencyPair.BTC_USD);
    System.out.println(ticker);
  }
}
