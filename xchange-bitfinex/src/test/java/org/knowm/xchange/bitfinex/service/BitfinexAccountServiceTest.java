package org.knowm.xchange.bitfinex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitfinex.BitfinexExchangeWiremock;
import org.knowm.xchange.bitfinex.service.trade.params.BitfinexFundingHistoryParams;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.service.account.AccountService;

class BitfinexAccountServiceTest extends BitfinexExchangeWiremock {

  AccountService accountService = exchange.getAccountService();

  @Test
  void account_info() throws IOException {
    AccountInfo actual = accountService.getAccountInfo();

    assertThat(actual.getWallet("exchange").getBalances()).hasSize(2);

    Balance expectedUsd =
        Balance.Builder.from(Balance.zero(Currency.USD))
            .total(new BigDecimal("14.93278618365196"))
            .available(new BigDecimal("8.93278618365196"))
            .frozen(new BigDecimal("6.00000000000000"))
            .build();

    assertThat(actual.getWallet("exchange").getBalance(Currency.USD))
        .usingRecursiveComparison()
        .isEqualTo(expectedUsd);
  }

  @Test
  void funding_history_movements() throws IOException {
    var actual = accountService.getFundingHistory(null);

    var expected =
        FundingRecord.builder()
            .address("0x87dc9d41d4353a033ca9a301a046dd697e7e09b6")
            .currency(Currency.USDT)
            .date(Date.from(Instant.parse("2025-08-27T21:17:39.000Z")))
            .amount(new BigDecimal(6))
            .internalId("23725209")
            .blockchainTransactionHash(
                "0x0dbc079033fa5422b2aac4e58fb55ac27610b41134b4c6e0bc45fb5b57e5f1a8")
            .type(Type.WITHDRAWAL)
            .status(Status.COMPLETE)
            .fee(BigDecimal.ZERO)
            .build();

    assertThat(actual).hasSize(2);
    assertThat(actual).first().usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void funding_internal_sub_account_transfers() throws IOException {
    var actual =
        accountService.getFundingHistory(
            BitfinexFundingHistoryParams.builder()
                .type(Type.INTERNAL_SUB_ACCOUNT_TRANSFER)
                .build());

    var expected =
        FundingRecord.builder()
            .currency(Currency.USDT)
            .date(Date.from(Instant.parse("2025-08-27T20:40:29.000Z")))
            .amount(new BigDecimal(5))
            .balance(new BigDecimal("269.70493408"))
            .internalId("10014161968")
            .type(Type.INTERNAL_SUB_ACCOUNT_TRANSFER)
            .description(
                "Transfer of 5.0 UST from wallet Exchange to Exchange SA(acc1->acc2) on wallet exchange")
            .build();

    assertThat(actual).hasSize(2);
    assertThat(actual).first().usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void funding_internal_wallet_transfers() throws IOException {
    var actual =
        accountService.getFundingHistory(
            BitfinexFundingHistoryParams.builder().type(Type.INTERNAL_WALLET_TRANSFER).build());

    var expected =
        FundingRecord.builder()
            .currency(Currency.USDT)
            .date(Date.from(Instant.parse("2025-08-27T21:49:57.000Z")))
            .amount(new BigDecimal(6))
            .balance(new BigDecimal(6))
            .internalId("10014228001")
            .type(Type.INTERNAL_WALLET_TRANSFER)
            .description("Transfer of 6.0 UST from wallet Trading to Deposit on wallet funding")
            .build();

    assertThat(actual).hasSize(3);
    assertThat(actual).first().usingRecursiveComparison().isEqualTo(expected);
  }
}
