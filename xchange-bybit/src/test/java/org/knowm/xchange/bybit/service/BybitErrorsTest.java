package org.knowm.xchange.bybit.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.bybit.BybitExchangeWiremock;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.InstrumentNotValidException;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BybitErrorsTest extends BybitExchangeWiremock {

  MarketDataService marketDataService = exchange.getMarketDataService();


  @Test
  void wrong_symbol() {
    assertThatExceptionOfType(InstrumentNotValidException.class)
        .isThrownBy(() -> marketDataService.getTicker(new CurrencyPair("BLA/BLA")));

  }

}
