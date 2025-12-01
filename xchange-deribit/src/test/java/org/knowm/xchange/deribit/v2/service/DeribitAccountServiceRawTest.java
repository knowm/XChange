package org.knowm.xchange.deribit.v2.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.deribit.DeribitExchangeWiremock;
import org.knowm.xchange.deribit.v2.dto.account.DeribitDeposit;
import org.knowm.xchange.deribit.v2.dto.account.DeribitDeposit.ClearanceState;
import org.knowm.xchange.deribit.v2.dto.account.DeribitTransfer;
import org.knowm.xchange.deribit.v2.dto.account.DeribitTransfer.Direction;
import org.knowm.xchange.deribit.v2.dto.account.DeribitTransfer.State;
import org.knowm.xchange.deribit.v2.dto.account.DeribitTransfer.Type;
import org.knowm.xchange.deribit.v2.dto.account.DeribitWithdrawal;
import org.knowm.xchange.dto.account.FundingRecord.Status;

class DeribitAccountServiceRawTest  extends DeribitExchangeWiremock {

  DeribitAccountServiceRaw deribitAccountServiceRaw = (DeribitAccountServiceRaw) exchange.getAccountService();

  @Test
  void valid_deposits() throws IOException {
    var actual = deribitAccountServiceRaw.getDeposits("USDT", null, null);

    DeribitDeposit expected =
        DeribitDeposit.builder()
            .transactionId("0xa5aee397bc7d0005519f8cd24b29779b0637dfa298567a8a5b8b38ac14293e03")
            .sourceAddress("0x9642b23ed1e01df1092b92641051881a322f5d4e")
            .createdAt(Instant.parse("2025-11-16T22:47:15.280Z"))
            .updatedAt(Instant.parse("2025-11-16T22:47:19.145Z"))
            .address("0x1c5a37bc3670026367d38108777c94e5fdaf7a7c")
            .note("")
            .currency(Currency.USDT)
            .clearanceState(ClearanceState.SUCCESS)
            .amount(new BigDecimal("49.54"))
            .status(Status.COMPLETE)
            .build();

    assertThat(actual).hasSize(1);
    assertThat(actual).first().usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void valid_transfers() throws IOException {
    var actual = deribitAccountServiceRaw.getTransfers("USDT", null, null);

    DeribitTransfer expected =
        DeribitTransfer.builder()
            .id("4177189")
            .type(Type.SUBACCOUNT)
            .state(State.CONFIRMED)
            .currency(Currency.USDT)
            .amount(new BigDecimal("5.0"))
            .direction(Direction.INCOME)
            .note("")
            .createdAt(Instant.parse("2025-11-19T23:22:19.474Z"))
            .updatedAt(Instant.parse("2025-11-19T23:22:19.474Z"))
            .otherSide("detection221_1")
            .build();

    assertThat(actual).hasSize(2);
    assertThat(actual).first().usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void valid_withdrawals() throws IOException {
    var actual = deribitAccountServiceRaw.getWithdrawals("USDT", null, null);

    DeribitWithdrawal expected =
        DeribitWithdrawal.builder()
            .id("451337")
            .priority("1.0")
            .targetAddress("2eaPDZw9FoGKNKbPjtAYzyv4HrA3JN2dKVYgknoS1rWm")
            .note("")
            .currency(Currency.USDT)
            .amount(new BigDecimal("0.09952953"))
            .createdAt(Instant.parse("2025-11-23T00:56:36.917Z"))
            .updatedAt(Instant.parse("2025-11-23T00:57:07.687Z"))
            .confirmedAt(Instant.parse("2025-11-23T00:57:07.687Z"))
            .fee(new BigDecimal("0.00047047"))
            .status(Status.PROCESSING)
            .build();

    assertThat(actual).hasSize(1);
    assertThat(actual).first().usingRecursiveComparison().isEqualTo(expected);
  }

}