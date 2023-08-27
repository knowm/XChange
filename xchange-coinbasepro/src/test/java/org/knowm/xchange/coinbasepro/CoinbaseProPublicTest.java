package org.knowm.xchange.coinbasepro;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProCurrency;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProduct;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProduct.CoinbaseProProductStatus;
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
    for (CoinbaseProProduct coinbaseProProduct : marketDataService.getCoinbaseProProducts()) {
      LOG.info(coinbaseProProduct.toString());
      assertThat(coinbaseProProduct).isInstanceOf(CoinbaseProProduct.class);
      assertThat(coinbaseProProduct.getId()).isNotNull();
      assertThat(coinbaseProProduct.getBaseCurrency()).isNotNull();
      assertThat(coinbaseProProduct.getQuoteCurrency()).isNotNull();
      assertThat(coinbaseProProduct.getQuoteIncrement()).isGreaterThan(BigDecimal.ZERO);
      assertThat(coinbaseProProduct.getBaseIncrement()).isGreaterThan(BigDecimal.ZERO);
      assertThat(coinbaseProProduct.getDisplayName()).isNotNull();
      assertThat(coinbaseProProduct.getMinMarketFunds()).isGreaterThan(BigDecimal.ZERO);
      assertThat(coinbaseProProduct.isMarginEnabled()).isNotNull();
      assertThat(coinbaseProProduct.isPostOnly()).isNotNull();
      assertThat(coinbaseProProduct.isLimitOnly()).isNotNull();
      assertThat(coinbaseProProduct.isCancelOnly()).isNotNull();
      assertThat(coinbaseProProduct.getStatus()).isInstanceOf(CoinbaseProProductStatus.class);
      assertThat(coinbaseProProduct.getStatusMessage()).isNotNull();
      assertThat(coinbaseProProduct.isTradingDisabled()).isNotNull();
      assertThat(coinbaseProProduct.isFxStablecoin()).isNotNull();
      assertThat(coinbaseProProduct.getMaxSlippagePercentage()).isGreaterThan(BigDecimal.ZERO);
      assertThat(coinbaseProProduct.isAuctionMode()).isNotNull();
      if(coinbaseProProduct.getBaseCurrency().equals("USDC")){
        assertThat(coinbaseProProduct.getHighBidLimitPercentage()).isGreaterThan(BigDecimal.ZERO);
      }
    }

    marketDataService.getInstruments().forEach(instrument -> {
      LOG.info(instrument.toString());
      assertThat(instrument).isInstanceOf(CurrencyPair.class);
    });
  }

  @Test
  public void testCoinbaseCurrency() throws Exception {
    for (CoinbaseProCurrency coinbaseProCurrency : marketDataService.getCoinbaseProCurrencies()) {
      LOG.info(coinbaseProCurrency.toString());
      assertThat(coinbaseProCurrency).isInstanceOf(CoinbaseProCurrency.class);
      assertThat(coinbaseProCurrency.getId()).isNotNull();
      assertThat(coinbaseProCurrency.getName()).isNotNull();
      assertThat(coinbaseProCurrency.getMinSize()).isGreaterThan(BigDecimal.ZERO);
      assertThat(coinbaseProCurrency.getStatus()).isNotNull();
      assertThat(coinbaseProCurrency.getMessage()).isNotNull();
      assertThat(coinbaseProCurrency.getMaxPrecision()).isGreaterThan(BigDecimal.ZERO);
      assertThat(coinbaseProCurrency.getConvertibleTo().size()).isNotNegative();
      assertThat(coinbaseProCurrency.getDetails()).isNotNull();
      assertThat(coinbaseProCurrency.getDefaultNetwork()).isNotNull();
      assertThat(coinbaseProCurrency.getSupportedNetworks().size()).isNotNegative();
    }

    marketDataService.getCurrencies().forEach(currency -> {
      LOG.info(currency.toString());
      assertThat(currency).isInstanceOf(Currency.class);
    });
  }
}
