package org.knowm.xchange.deribit.v2.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.deribit.DeribitExchangeWiremock;
import org.knowm.xchange.deribit.v2.service.params.DeribitFundingHistoryParams;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;

class DeribitAccountServiceTest extends DeribitExchangeWiremock {

  @Test
  void funding_history() throws IOException {
    List<FundingRecord> actual =
        exchange
            .getAccountService()
            .getFundingHistory(
                DeribitFundingHistoryParams.builder()
                    .currency(Currency.USDT)
                    .endTime(Date.from(Instant.ofEpochMilli(1763942244002L)))
                    .build());

    FundingRecord expectedTrade =
        FundingRecord.builder()
            .internalId("6967597")
            .date(Date.from(Instant.parse("2025-11-23T12:22:04.926Z")))
            .currency(Currency.USDT)
            .type(Type.TRADE)
            .amount(new BigDecimal("15.006"))
            .balance(new BigDecimal("12.5124"))
            .status(Status.COMPLETE)
            .build();

    assertThat(actual).hasSize(6);
    assertThat(actual).first().usingRecursiveComparison().isEqualTo(expectedTrade);

    FundingRecord expectedDeposit =
        FundingRecord.builder()
            .internalId("6539450")
            .blockchainTransactionHash("0xa5aee397bc7d0005519f8cd24b29779b0637dfa298567a8a5b8b38ac14293e03")
            .date(Date.from(Instant.parse("2025-11-16T23:10:00.124Z")))
            .address("0x1c5a37bc3670026367d38108777c94e5fdaf7a7c")
            .addressTag("")
            .currency(Currency.USDT)
            .type(Type.DEPOSIT)
            .amount(new BigDecimal("49.54"))
            .balance(new BigDecimal("49.54"))
            .status(Status.COMPLETE)
            .build();
    assertThat(actual.get(5)).usingRecursiveComparison().isEqualTo(expectedDeposit);

    FundingRecord expectedTransfer =
        FundingRecord.builder()
            .internalId("6652554")
            .date(Date.from(Instant.parse("2025-11-19T22:48:55.283Z")))
            .address("429304")
            .addressTag("")
            .currency(Currency.USDT)
            .type(Type.INTERNAL_SUB_ACCOUNT_TRANSFER)
            .amount(new BigDecimal("5.0"))
            .balance(new BigDecimal("44.54"))
            .status(Status.COMPLETE)
            .build();
    assertThat(actual.get(4)).usingRecursiveComparison().isEqualTo(expectedTransfer);
  }


}