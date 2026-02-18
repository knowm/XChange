package org.knowm.xchange.kucoin;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.kucoin.dto.response.SymbolResponse;

class KucoinMarketDataServiceRawIntegration extends KucoinIntegrationTestParent {

  KucoinMarketDataServiceRaw marketDataServiceRaw = exchange.getMarketDataService();

  @Test
  void valid_symbols() throws Exception {
    List<SymbolResponse> symbols = marketDataServiceRaw.getKucoinSymbolsV2();

    assertThat(symbols).isNotEmpty();

    // validate symbols
    assertThat(symbols)
        .allSatisfy(
            symbol -> {
              assertThat(symbol.getCurrencyPair()).isNotNull();
              assertThat(symbol.getBaseCurrency()).isNotNull();
              assertThat(symbol.getQuoteCurrency()).isNotNull();
              assertThat(symbol.getCurrencyPair())
                  .isEqualTo(new CurrencyPair(symbol.getBaseCurrency(), symbol.getQuoteCurrency()));
            });
  }
}
