package org.knowm.xchange.bitget.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.knowm.xchange.currency.CurrencyPair.BTC_USDT;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitget.BitgetExchange;
import org.knowm.xchange.bitget.dto.BitgetCoinDto;
import org.knowm.xchange.bitget.dto.BitgetSymbolDto;
import org.knowm.xchange.dto.meta.ExchangeHealth;

class BitgetMarketDataServiceRawIntegration {

  BitgetExchange exchange = ExchangeFactory.INSTANCE.createExchange(BitgetExchange.class);
  BitgetMarketDataServiceRaw bitgetMarketDataServiceRaw = (BitgetMarketDataServiceRaw) exchange.getMarketDataService();

  @BeforeEach
  void exchange_online() {
    // skip if offline
    assumeThat(exchange.getMarketDataService().getExchangeHealth()).isEqualTo(
        ExchangeHealth.ONLINE);
  }


  @Test
  void valid_coins() throws IOException {
    List<BitgetCoinDto> coins = bitgetMarketDataServiceRaw.getBitgetCoinDtoList();

    assertThat(coins).isNotEmpty();

    // validate coins
    assertThat(coins).allSatisfy(coin -> {
      assertThat(coin.getCoinId()).isNotNull();
      assertThat(coin.getCurrency()).isNotNull();

      // validate each chain
      assertThat(coin.getChains()).allSatisfy(chain -> {
        assertThat(chain.getChain()).isNotNull();
      });

    });
  }


  @Test
  void valid_symbol() throws IOException {
    List<BitgetSymbolDto> symbols = bitgetMarketDataServiceRaw.getBitgetSymbolDtos(BTC_USDT);

    assertThat(symbols).hasSize(1);

    BitgetSymbolDto symbol = symbols.get(0);
    assertThat(symbol.getCurrencyPair()).isEqualTo(BTC_USDT);
    assertThat(symbol.getPricePrecision()).isPositive();
    assertThat(symbol.getQuantityPrecision()).isPositive();
    assertThat(symbol.getQuotePrecision()).isPositive();

  }


  @Test
  void valid_symbols() throws IOException {
    List<BitgetSymbolDto> symbols = bitgetMarketDataServiceRaw.getBitgetSymbolDtos(null);

    assertThat(symbols).isNotEmpty();

    // validate symbols
    assertThat(symbols).allSatisfy(symbol -> {
      assertThat(symbol.getCurrencyPair()).isNotNull();
      assertThat(symbol.getBase()).isNotNull();
      assertThat(symbol.getQuote()).isNotNull();

    });

  }


}