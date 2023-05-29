package org.knowm.xchange.gateio.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.gateio.GateioExchangeWiremock;

class GateioAccountServiceTest extends GateioExchangeWiremock {

  GateioAccountService gateioAccountService = ((GateioAccountService) exchange.getAccountService());


  @Test
  void getAccountInfo() throws IOException {

    AccountInfo accountInfo = gateioAccountService.getAccountInfo();

    assertThat(accountInfo.getWallet("spot").balances()).hasSize(1);

    Balance usdtBalance = accountInfo.getWallet().getBalance(Currency.USDT);
    assertThat(usdtBalance.getTotal()).isEqualTo(new BigDecimal("5.2"));
    assertThat(usdtBalance.getAvailable()).isEqualTo(new BigDecimal("4.1"));
    assertThat(usdtBalance.getFrozen()).isEqualTo(new BigDecimal("1.1"));
  }
}