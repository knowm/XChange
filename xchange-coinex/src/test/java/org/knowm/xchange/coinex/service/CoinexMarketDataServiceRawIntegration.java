package org.knowm.xchange.coinex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.coinex.CoinexIntegrationTestParent;
import org.knowm.xchange.coinex.dto.marketdata.CoinexChainInfo;

class CoinexMarketDataServiceRawIntegration extends CoinexIntegrationTestParent {

  @Test
  void valid_chainInfos() throws IOException {
    CoinexMarketDataServiceRaw coinexMarketDataServiceRaw =
        (CoinexMarketDataServiceRaw) exchange.getMarketDataService();
    List<CoinexChainInfo> chainInfos = coinexMarketDataServiceRaw.getAllCoinexChainInfos();

    assertThat(chainInfos)
        .allSatisfy(
            chainInfo -> {
              assertThat(chainInfo.getAsset().getCurrency()).isNotNull();
              if (!chainInfo.getChains().isEmpty()) {
                assertThat(chainInfo.getChains())
                    .allSatisfy(coinexChain -> assertThat(coinexChain.getName()).isNotBlank());
              }
            });
  }
}
