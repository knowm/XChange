package org.knowm.xchange.binance;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;

public class ExchangeMetaDataIntegration extends BinanceExchangeIntegration {

  static ExchangeMetaData metaData;

  @BeforeClass
  public static void fetchMetaData() throws Exception {
    createExchange();
    metaData = exchange.getExchangeMetaData();
  }

  @Test
  public void testEthBtcPairMetaData() {
    CurrencyPairMetaData pairMetaData = metaData.getCurrencyPairs().get(CurrencyPair.ETH_BTC);
    assertThat(pairMetaData.getPriceScale()).isEqualByComparingTo(6);
    assertThat(pairMetaData.getMinimumAmount()).isEqualByComparingTo("0.001");
    assertThat(pairMetaData.getMaximumAmount().longValueExact()).isEqualTo(100000);
    assertThat(pairMetaData.getAmountStepSize()).isEqualByComparingTo("0.001");
  }

  @Test
  public void testLtcBtcPairMetaData() {
    CurrencyPairMetaData pairMetaData =
        metaData.getCurrencyPairs().get(new CurrencyPair("LTC/BTC"));
    assertThat(pairMetaData.getPriceScale()).isEqualByComparingTo(6);
    assertThat(pairMetaData.getMinimumAmount()).isEqualByComparingTo("0.01");
    assertThat(pairMetaData.getMaximumAmount().longValueExact()).isEqualTo(100000);
    assertThat(pairMetaData.getAmountStepSize()).isEqualByComparingTo("0.01");
  }
}
