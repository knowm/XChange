package org.knowm.xchange.coinex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinex.CoinexExchange;
import org.knowm.xchange.coinex.dto.marketdata.CoinexChainInfo;

class CoinexMarketDataServiceRawIntegration {

  CoinexExchange exchange = ExchangeFactory.INSTANCE.createExchange(CoinexExchange.class);

  @Test
  void valid_chainInfos() throws IOException {
    CoinexMarketDataServiceRaw coinexMarketDataServiceRaw =
        (CoinexMarketDataServiceRaw) exchange.getMarketDataService();
    List<CoinexChainInfo> chainInfos = coinexMarketDataServiceRaw.getAllCoinexChainInfos();

    assertThat(chainInfos)
        .allSatisfy(
            chainInfo -> {
              assertThat(chainInfo.getCurrency()).isNotNull();
              assertThat(chainInfo.getChainName()).isNotEmpty();
            });
  }
}
