package org.knowm.xchange.bitfinex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitfinex.BitfinexExchangeWiremock;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

class BitfinexMarketDataServiceTest extends BitfinexExchangeWiremock {

  MarketDataService marketDataService = exchange.getMarketDataService();

  @Test
  void tickers() throws IOException {
    List<Ticker> actual = marketDataService.getTickers(null);

    Ticker expected =
        new Ticker.Builder()
            .instrument(CurrencyPair.BTC_USD)
            .last(new BigDecimal("26310"))
            .ask(new BigDecimal("26314"))
            .askSize(new BigDecimal("15.24573217"))
            .bid(new BigDecimal("26313"))
            .bidSize(new BigDecimal("15.32037423"))
            .high(new BigDecimal("26465"))
            .low(new BigDecimal("26129"))
            .volume(new BigDecimal("912.65670031"))
            .percentageChange(new BigDecimal("-0.431426"))
            .build();

    assertThat(actual).hasSize(2);

    assertThat(actual).first().usingRecursiveComparison().isEqualTo(expected);
  }
}
