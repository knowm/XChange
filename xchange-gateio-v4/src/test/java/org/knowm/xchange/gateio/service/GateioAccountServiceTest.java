package org.knowm.xchange.gateio.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.exceptions.OrderAmountUnderMinimumException;
import org.knowm.xchange.exceptions.OrderNotValidException;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.gateio.GateioExchangeWiremock;
import org.knowm.xchange.gateio.config.Config;
import org.knowm.xchange.gateio.service.params.GateioFundingHistoryParams;
import org.knowm.xchange.gateio.service.params.GateioWithdrawFundsParams;
import si.mazi.rescu.CustomRestProxyFactoryImpl;

class GateioAccountServiceTest extends GateioExchangeWiremock {


  static {
    Config.getInstance().setRestProxyFactoryClass(CustomRestProxyFactoryImpl.class);
  }

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


  @Test
  void funding_history() throws IOException {
    List<FundingRecord> actual = gateioAccountService.getFundingHistory(GateioFundingHistoryParams.builder()
        .currency(Currency.USDT)
        .startTime(Date.from(Instant.ofEpochSecond(1691447482)))
        .endTime(Date.from(Instant.ofEpochSecond(1691533882)))
        .pageLength(2)
        .pageNumber(1)
        .type("order_fee")
        .build());

    FundingRecord expected = new FundingRecord.Builder()
        .setInternalId("40558668441")
        .setDate(Date.from(Instant.ofEpochMilli(1691510538067L)))
        .setCurrency(Currency.USDT)
        .setBalance(new BigDecimal("16.00283141582979715942"))
        .setType(Type.OTHER_OUTFLOW)
        .setAmount(new BigDecimal("0.0113918056"))
        .setDescription("order_fee")
        .build();

    assertThat(actual).hasSize(2);
    assertThat(actual).first().usingRecursiveComparison().isEqualTo(expected);
  }


}