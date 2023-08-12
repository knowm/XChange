package org.knowm.xchange.gateio.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.exceptions.OrderAmountUnderMinimumException;
import org.knowm.xchange.exceptions.OrderNotValidException;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.gateio.GateioExchangeWiremock;
import org.knowm.xchange.gateio.service.params.GateioWithdrawFundsParams;

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

  @Test
  void normal_withdraw() throws IOException {
    GateioWithdrawFundsParams params = GateioWithdrawFundsParams.builder()
        .clientRecordId("valid-withdrawal-id")
        .address("6vLyxJ9dBziamyaw2vDcs9n2NwQdW1uk3aooJwrEscnA")
        .addressTag("")
        .chain("SOL")
        .amount(BigDecimal.valueOf(3))
        .currency(Currency.USDT)
        .build();

    String withdrawalId = gateioAccountService.withdrawFunds(params);
    assertThat(withdrawalId).isEqualTo("w35980955");
  }


  @Test
  void rate_limited_withdraw() {
    GateioWithdrawFundsParams params = GateioWithdrawFundsParams.builder()
        .clientRecordId("rate-limited-id")
        .address("6vLyxJ9dBziamyaw2vDcs9n2NwQdW1uk3aooJwrEscnA")
        .addressTag("")
        .chain("SOL")
        .amount(BigDecimal.valueOf(3))
        .currency(Currency.USDT)
        .build();

    assertThatExceptionOfType(RateLimitExceededException.class)
        .isThrownBy(() -> gateioAccountService.withdrawFunds(params));
  }


  @Test
  void zero_amount_withdraw() {
    GateioWithdrawFundsParams params = GateioWithdrawFundsParams.builder()
        .clientRecordId("zero-amount-id")
        .address("6vLyxJ9dBziamyaw2vDcs9n2NwQdW1uk3aooJwrEscnA")
        .addressTag("")
        .chain("SOL")
        .amount(BigDecimal.ZERO)
        .currency(Currency.USDT)
        .build();

    assertThatExceptionOfType(OrderAmountUnderMinimumException.class)
        .isThrownBy(() -> gateioAccountService.withdrawFunds(params));
  }


  @Test
  void invalid_address_withdraw() {
    GateioWithdrawFundsParams params = GateioWithdrawFundsParams.builder()
        .clientRecordId("invalid-address-id")
        .address("invalid-address")
        .addressTag("")
        .chain("SOL")
        .amount(BigDecimal.ZERO)
        .currency(Currency.USDT)
        .build();

    assertThatExceptionOfType(OrderNotValidException.class)
        .isThrownBy(() -> gateioAccountService.withdrawFunds(params));
  }


}