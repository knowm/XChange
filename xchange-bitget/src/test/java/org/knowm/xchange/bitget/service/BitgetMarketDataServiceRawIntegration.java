package org.knowm.xchange.bitget.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitget.BitgetExchange;
import org.knowm.xchange.bitget.dto.BitgetCoinDto;
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

}