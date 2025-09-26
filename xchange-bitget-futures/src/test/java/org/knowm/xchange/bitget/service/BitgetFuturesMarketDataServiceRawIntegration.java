package org.knowm.xchange.bitget.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.currency.CurrencyPair.BTC_USDT;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitget.BitgetFuturesIntegrationTestParent;
import org.knowm.xchange.bitget.dto.marketdata.BitgetContractDto;

public class BitgetFuturesMarketDataServiceRawIntegration
    extends BitgetFuturesIntegrationTestParent {

  BitgetFuturesMarketDataServiceRaw bitgetMarketDataServiceRaw =
      (BitgetFuturesMarketDataServiceRaw) exchange.getMarketDataService();

  @Test
  void valid_symbol() throws IOException {
    List<BitgetContractDto> symbols = bitgetMarketDataServiceRaw.getBitgetContractDtos(BTC_USDT);

    assertThat(symbols).hasSize(1);

    BitgetContractDto symbol = symbols.get(0);
    assertThat(symbol.getFuturesContract().getCurrencyPair()).isEqualTo(BTC_USDT);
    assertThat(symbol.getPricePrecision()).isPositive();
    assertThat(symbol.getAssetAmountPrecision()).isPositive();
  }

  @Test
  void valid_symbols() throws IOException {
    List<BitgetContractDto> symbols = bitgetMarketDataServiceRaw.getBitgetContractDtos(null);

    assertThat(symbols).isNotEmpty();

    // validate symbols
    assertThat(symbols)
        .allSatisfy(
            symbol -> {
              assertThat(symbol.getFuturesContract().getCurrencyPair()).isNotNull();
              assertThat(symbol.getBase()).isNotNull();
              assertThat(symbol.getQuote()).isNotNull();
            });
  }
}
