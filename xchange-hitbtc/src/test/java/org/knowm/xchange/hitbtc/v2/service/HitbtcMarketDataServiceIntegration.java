package org.knowm.xchange.hitbtc.v2.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.hitbtc.v2.BaseServiceTest;
import org.knowm.xchange.service.marketdata.MarketDataService;

class HitbtcMarketDataServiceIntegration extends BaseServiceTest {

  @Test
  void getTicker() throws Exception {

    MarketDataService marketDataService = exchange().getMarketDataService();

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
    assertThat(ticker).isNotNull();
    assertThat(ticker.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
  }

  @Test
  void getTickerBCC() throws Exception {

    MarketDataService marketDataService = exchange().getMarketDataService();

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BCC_USD);
    assertThat(ticker).isNotNull();
    assertThat(ticker.getCurrencyPair()).isEqualTo(CurrencyPair.BCC_USD);
  }

  @Test
  void getTickerBCH() throws Exception {

    MarketDataService marketDataService = exchange().getMarketDataService();

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BCH_USD);
    assertThat(ticker).isNotNull();
    assertThat(ticker.getCurrencyPair()).isEqualTo(CurrencyPair.BCH_USD);
  }

  @Test
  void getTrades() throws Exception {

    MarketDataService marketDataService = exchange().getMarketDataService();

    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD);

    assertThat(trades).isNotNull();
  }

  @Test
  void getOrderBook() throws Exception {

    MarketDataService marketDataService = exchange().getMarketDataService();

    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);

    assertThat(orderBook).isNotNull();
  }
}
