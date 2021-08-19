package org.knowm.xchange.btcturk.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btcturk.BTCTurkExchange;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkOHLC;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkOrderBook;
import org.knowm.xchange.btcturk.service.BTCTurkDemoUtilsTest;
import org.knowm.xchange.btcturk.service.BTCTurkMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

/**
 * @author semihunaldi
 * @author mertguner
 */
public class MarketDataFetchIntegration {

  private Exchange btcTurk;
  private BTCTurkMarketDataService btcTurkMarketDataService;

  @Before
  public void InitExchange() throws IOException {
    if (BTCTurkDemoUtilsTest.BTCTURK_APIKEY.isEmpty())
      btcTurk = ExchangeFactory.INSTANCE.createExchange(BTCTurkExchange.class);
    else {
      ExchangeSpecification exSpec = new BTCTurkExchange().getDefaultExchangeSpecification();
      exSpec.setApiKey(BTCTurkDemoUtilsTest.BTCTURK_APIKEY);
      exSpec.setSecretKey(BTCTurkDemoUtilsTest.BTCTURK_SECRETKEY);
      btcTurk = ExchangeFactory.INSTANCE.createExchange(exSpec);
    }

    MarketDataService marketDataService = btcTurk.getMarketDataService();
    btcTurkMarketDataService = (BTCTurkMarketDataService) marketDataService;
  }

  @Test
  public void Tests() throws Exception, InterruptedException {

    // Ticker Test
    Thread.sleep(1000);
    Ticker ticker = btcTurkMarketDataService.getTicker(new CurrencyPair("BTC", "TRY"));
    System.out.println(ticker.toString());
    assertThat(ticker).isNotNull();

    Thread.sleep(1000);
    List<Ticker> tickers = btcTurkMarketDataService.getTickers(new Params() {});
    for (Ticker _ticker : tickers) {
      System.out.println(_ticker.toString());
      assertThat(_ticker).isNotNull();
    }

    Thread.sleep(1000);
    // TradesTest
    Trades trades = btcTurkMarketDataService.getTrades(CurrencyPair.BTC_TRY);
    assertThat(trades.getTrades().size()).isEqualTo(50);

    Thread.sleep(1000);
    trades = btcTurkMarketDataService.getTrades(CurrencyPair.BTC_TRY, 5);
    assertThat(trades.getTrades().size()).isEqualTo(5);

    Thread.sleep(1000);
    // OHCLTest
    List<BTCTurkOHLC> btcTurkBTCTurkOHLC =
        btcTurkMarketDataService.getBTCTurkOHLC(CurrencyPair.BTC_TRY);
    assertThat(btcTurkBTCTurkOHLC.size()).isNotEqualTo(0); // Daily size is always changing

    Thread.sleep(1000);
    List<BTCTurkOHLC> btcTurkBTCTurkOHLC2 =
        btcTurkMarketDataService.getBTCTurkOHLC(CurrencyPair.BTC_TRY, 2);
    assertThat(btcTurkBTCTurkOHLC2.size()).isEqualTo(2);

    // OrderBookTest
    Thread.sleep(1000);
    BTCTurkOrderBook btcTurkBTCTurkOrderBook =
        btcTurkMarketDataService.getBTCTurkOrderBook(CurrencyPair.BTC_TRY);
    assertThat(btcTurkBTCTurkOrderBook.getAsks().size()).isEqualTo(100);
    assertThat(btcTurkBTCTurkOrderBook.getBids().size()).isEqualTo(100);
  }
}
