package org.knowm.xchange.bitmex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitmex.BitmexExchangeWiremock;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet.WalletFeature;
import org.knowm.xchange.service.account.AccountService;

class BitmexAccountServiceTest extends BitmexExchangeWiremock {

  AccountService accountService = exchange.getAccountService();

  @Test
  void getAccountInfo() throws IOException {

    AccountInfo accountInfo = accountService.getAccountInfo();

    assertThat(accountInfo.getWallet(WalletFeature.TRADING).balances()).hasSize(4);

    Balance usdtBalance = accountInfo.getWallet().getBalance(Currency.USDT);
    assertThat(usdtBalance.getTotal()).isEqualTo(new BigDecimal("40.116243"));
    assertThat(usdtBalance.getAvailable()).isEqualTo(new BigDecimal("40.116243"));
    assertThat(usdtBalance.getTimestamp()).isEqualTo(Date.from(Instant.parse("2024-12-09T11:17:40.965Z")));

    Balance ethBalance = accountInfo.getWallet().getBalance(Currency.ETH);
    assertThat(ethBalance.getTotal()).isEqualTo(new BigDecimal("0.001000000"));
    assertThat(ethBalance.getAvailable()).isEqualTo(new BigDecimal("0.001000000"));
    assertThat(ethBalance.getTimestamp()).isEqualTo(Date.from(Instant.parse("2024-12-09T09:54:20.367Z")));
  }


}
