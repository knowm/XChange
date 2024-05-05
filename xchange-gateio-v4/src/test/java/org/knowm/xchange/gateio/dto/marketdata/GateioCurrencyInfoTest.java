package org.knowm.xchange.gateio.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GateioCurrencyInfoTest {

  @Test
  void valid_isWithdrawEnabled() {
    GateioCurrencyInfo gateioCurrencyInfo = GateioCurrencyInfo.builder().build();
    assertThat(gateioCurrencyInfo.isWithdrawEnabled()).isFalse();

    gateioCurrencyInfo.setWithdrawDisabled(false);
    assertThat(gateioCurrencyInfo.isWithdrawEnabled()).isTrue();
  }


  @Test
  void valid_isDepositEnabled() {
    GateioCurrencyInfo gateioCurrencyInfo = GateioCurrencyInfo.builder().build();
    assertThat(gateioCurrencyInfo.isDepositEnabled()).isFalse();

    gateioCurrencyInfo.setDepositDisabled(false);
    assertThat(gateioCurrencyInfo.isDepositEnabled()).isTrue();
  }
}