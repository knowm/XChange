package org.knowm.xchange.bitcointoyou.service.marketdata;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitcointoyou.BitcointoyouExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Integration tests for Bitcointoyou Ticker
 *
 * @author Danilo Guimaraes
 * @author Jonathas Carrijo
 */
class TickerFetchIntegration {

  private static Ticker ticker;

  @BeforeAll
  static void setUp() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BitcointoyouExchange.class);
    exchange.remoteInit();
    MarketDataService marketDataService = exchange.getMarketDataService();
    ticker = marketDataService.getTicker(new CurrencyPair(Currency.BTC, Currency.BRL));
  }

  @Test
  void tickerTest() throws Exception {

    final SoftAssertions softly = new SoftAssertions();

    softly.assertThat(ticker).isNotNull();
    softly.assertThat(ticker.getHigh()).isNotNull();
    softly.assertThat(ticker.getLow()).isNotNull();
    softly.assertThat(ticker.getVolume()).isNotNull();
    softly.assertThat(ticker.getLast()).isNotNull();
    softly.assertThat(ticker.getBid()).isNotNull();
    softly.assertThat(ticker.getTimestamp()).isNotNull();

    softly.assertAll();
  }
}
