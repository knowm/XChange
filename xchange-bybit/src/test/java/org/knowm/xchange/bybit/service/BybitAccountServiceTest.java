package org.knowm.xchange.bybit.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;

public class BybitAccountServiceTest extends BaseWiremockTest {

  @Test
  public void testGetWalletBalancesWithUnified() throws IOException {
    Exchange bybitExchange = createExchange();
    BybitAccountService bybitAccountService =
        new BybitAccountService(bybitExchange, BybitAccountType.UNIFIED);

    initGetStub("/v5/account/wallet-balance", "/getWalletBalance.json5");

    AccountInfo accountInfo = bybitAccountService.getAccountInfo();
    assertThat(accountInfo.getWallet().getBalance(new Currency("BTC")).getTotal())
        .isEqualTo(new BigDecimal("0"));
    assertThat(accountInfo.getWallet().getBalance(new Currency("BTC")).getAvailable())

        .isEqualTo(new BigDecimal("0"));
  }

  @Test
  public void testGetAllCoinsBalanceWithFund() throws IOException {
    Exchange bybitExchange = createExchange();
    BybitAccountService bybitAccountService =
        new BybitAccountService(bybitExchange, BybitAccountType.FUND);

    initGetStub("/v5/asset/transfer/query-account-coins-balance", "/getAllCoinsBalance.json5");

    AccountInfo accountInfo = bybitAccountService.getAccountInfo();
    assertThat(accountInfo.getWallet().getBalance(new Currency("USDC")).getTotal())
        .isEqualTo(new BigDecimal("0"));
    assertThat(accountInfo.getWallet().getBalance(new Currency("USDC")).getAvailable())
        .isEqualTo(new BigDecimal("0"));
  }
}
