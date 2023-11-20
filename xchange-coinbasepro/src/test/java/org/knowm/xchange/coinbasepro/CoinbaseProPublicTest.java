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

    marketDataService.getInstruments().forEach((instrument, instrumentMetaData) -> {
      LOG.info(instrument.toString()+ "--" + instrumentMetaData.toString());
      assertThat(instrument).isInstanceOf(CurrencyPair.class);
      assertThat(instrumentMetaData.getPriceStepSize()).isNotNull();
      assertThat(instrumentMetaData.getAmountStepSize()).isNotNull();
      assertThat(instrumentMetaData.getVolumeScale()).isNotNull();
      assertThat(instrumentMetaData.getPriceScale()).isNotNull();
      assertThat(instrumentMetaData.getTradingFeeCurrency()).isNotNull();
      assertThat(instrumentMetaData.getTradingFee()).isNotNull();
    });
  }

  @Test
  public void testCoinbaseCurrency() throws Exception {
    marketDataService.getCurrencies().forEach((currency, currencyMetaData) -> {
      LOG.info(currency.toString() + "--" + currencyMetaData.toString());
      assertThat(currency).isInstanceOf(Currency.class);
      assertThat(currencyMetaData.getMinWithdrawalAmount()).isNotNull();
      assertThat(currencyMetaData.getWalletHealth()).isNotNull();
      assertThat(currencyMetaData.getScale()).isNotNull();
    });
  }
}
