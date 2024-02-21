package org.knowm.xchange.gateio.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GateioCurrencyChainTest {

  @Test
  void valid_isWithdrawEnabled() {
    GateioCurrencyChain gateioCurrencyChain = GateioCurrencyChain.builder().build();
    assertThat(gateioCurrencyChain.isWithdrawEnabled()).isFalse();

    gateioCurrencyChain.setDisabled(false);
    gateioCurrencyChain.setWithdrawDisabled(false);
    assertThat(gateioCurrencyChain.isWithdrawEnabled()).isTrue();
  }

  @Test
  void valid_isDepositEnabled() {
    GateioCurrencyChain gateioCurrencyChain = GateioCurrencyChain.builder().build();
    assertThat(gateioCurrencyChain.isDepositEnabled()).isFalse();

    gateioCurrencyChain.setDisabled(false);
    gateioCurrencyChain.setDepositDisabled(false);
    assertThat(gateioCurrencyChain.isDepositEnabled()).isTrue();
  }
}