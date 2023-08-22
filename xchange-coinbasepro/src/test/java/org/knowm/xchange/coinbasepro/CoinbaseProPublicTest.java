package org.knowm.xchange.coinbasepro;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinbasepro.service.CoinbaseProMarketDataService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinbaseProPublicTest {

  Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CoinbaseProExchange.class);
  CoinbaseProMarketDataService marketDataService = (CoinbaseProMarketDataService) exchange.getMarketDataService();
  private static final Logger LOG = LoggerFactory.getLogger(CoinbaseProPrivateIntegration.class);

  @Test
  public void testCoinbaseInstruments() throws Exception {
    marketDataService.getInstruments().forEach(instrument -> {
      LOG.info(instrument.toString());
      assertThat(instrument).isInstanceOf(CurrencyPair.class);
    });
  }

  @Test
  public void testCoinbaseCurrency() throws Exception {
    marketDataService.getCurrencies().forEach(currency -> {
      LOG.info(currency.toString());
      assertThat(currency).isInstanceOf(Currency.class);
    });
  }
}
