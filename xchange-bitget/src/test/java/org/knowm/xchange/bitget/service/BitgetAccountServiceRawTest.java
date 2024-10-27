package org.knowm.xchange.bitget.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.currency.Currency.USDT;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitget.BitgetExchangeWiremock;
import org.knowm.xchange.bitget.dto.account.BitgetAccountType;
import org.knowm.xchange.bitget.dto.account.BitgetBalanceDto;
import org.knowm.xchange.bitget.dto.account.BitgetDepositWithdrawRecordDto;
import org.knowm.xchange.bitget.dto.account.BitgetDepositWithdrawRecordDto.DepositType;
import org.knowm.xchange.bitget.dto.account.BitgetMainSubTransferRecordDto;
import org.knowm.xchange.bitget.dto.account.BitgetSubBalanceDto;
import org.knowm.xchange.bitget.dto.account.BitgetTransferRecordDto;
import org.knowm.xchange.bitget.dto.account.BitgetTransferRecordDto.Status;
import org.knowm.xchange.bitget.dto.account.params.BitgetMainSubTransferHistoryParams;
import org.knowm.xchange.bitget.dto.account.params.BitgetMainSubTransferHistoryParams.Role;
import org.knowm.xchange.bitget.dto.account.params.BitgetTransferHistoryParams;
import org.knowm.xchange.bitget.service.params.BitgetFundingHistoryParams;
import org.knowm.xchange.dto.account.FundingRecord;

class BitgetAccountServiceRawTest extends BitgetExchangeWiremock {

  BitgetAccountServiceRaw bitgetAccountServiceRaw =
      (BitgetAccountServiceRaw) exchange.getAccountService();

  @Test
  void transfer_records() throws IOException {
    BitgetTransferRecordDto expected =
        BitgetTransferRecordDto.builder()
            .clientOid("1225489997897666560")
            .currency(USDT)
            .fromAccountType(BitgetAccountType.SPOT)
            .fromSymbol("")
            .toAccountType(BitgetAccountType.SPOT)
            .toSymbol("")
            .size(new BigDecimal("1.00000000"))
            .status(Status.SUCCESSFUL)
            .timestamp(Instant.ofEpochMilli(1727905504678L))
            .transferId("1225489997897666560")
            .build();

    BitgetTransferHistoryParams params =
        BitgetTransferHistoryParams.builder()
            .currency(USDT)
            .fromAccountType(BitgetAccountType.SPOT)
            .startTime(Instant.ofEpochMilli(1727904561900L))
            .endTime(Instant.ofEpochMilli(1727905505000L))
            .limit(1)
            .endId("1225486043893731328")
            .build();
    List<BitgetTransferRecordDto> actual = bitgetAccountServiceRaw.getBitgetTransferRecords(params);

    assertThat(actual).hasSize(1);
    assertThat(actual).first().usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void main_sub_transfer_records() throws IOException {
    BitgetMainSubTransferRecordDto expected =
        BitgetMainSubTransferRecordDto.builder()
            .clientOid("1225490042499895296")
            .currency(USDT)
            .fromAccountType(BitgetAccountType.SPOT)
            .toAccountType(BitgetAccountType.SPOT)
            .fromUserId("7326856338")
            .toUserId("1548914322")
            .size(new BigDecimal("1.00000000"))
            .status(BitgetMainSubTransferRecordDto.Status.SUCCESSFUL)
            .timestamp(Instant.ofEpochMilli(1727905515312L))
            .transferId("72990567")
            .build();

    BitgetMainSubTransferHistoryParams params =
        BitgetMainSubTransferHistoryParams.builder()
            .currency(USDT)
            .role(Role.INITIATOR)
            .subAccountUid("7326856338")
            .startTime(Instant.ofEpochMilli(1727905515300L))
            .endTime(Instant.ofEpochMilli(1727905515399L))
            .clientOid("1225490042499895296")
            .limit(1)
            .endId("1225490042499895296")
            .build();
    List<BitgetMainSubTransferRecordDto> actual =
        bitgetAccountServiceRaw.getBitgetMainSubTransferRecords(params);

    assertThat(actual).hasSize(1);
    assertThat(actual).first().usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void sub_account_deposit_records() throws IOException {
    BitgetDepositWithdrawRecordDto expected =
        BitgetDepositWithdrawRecordDto.builder()
            .chain("TON(TON)")
            .orderId("1227960429565849600")
            .tradeId("x_8NF1B51t2lLOu-RYxl-7FOwfM4d6UgPhqlr_CAO8s=")
            .currency(USDT)
            .depositType(DepositType.ON_CHAIN)
            .toAddress("EQCJLo0UPRm6RToIXgD0eMpoak5cuj4BTt99NYCY14yOUcoT")
            .size(new BigDecimal("10.00000000"))
            .status(FundingRecord.Status.COMPLETE)
            .createdAt(Instant.ofEpochMilli(1728494501487L))
            .updatedAt(Instant.ofEpochMilli(1728494528012L))
            .build();

    BitgetFundingHistoryParams params =
        BitgetFundingHistoryParams.builder()
            .currency(USDT)
            .subAccountUid("7831928986")
            .startTime(Date.from(Instant.ofEpochMilli(1728494500000L)))
            .endTime(Date.from(Instant.ofEpochMilli(1728494529999L)))
            .limit(1)
            .endId("1227960429565849609")
            .build();
    List<BitgetDepositWithdrawRecordDto> actual =
        bitgetAccountServiceRaw.getBitgetSubAccountDepositRecords(params);

    assertThat(actual).hasSize(1);
    assertThat(actual).first().usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void sub_account_balances() throws IOException {
    BitgetSubBalanceDto expected =
        BitgetSubBalanceDto.builder()
            .userId("7831928986")
            .balances(
                Collections.singletonList(
                    BitgetBalanceDto.builder()
                        .currency(USDT)
                        .available(new BigDecimal("55.44646499"))
                        .limitAvailable(BigDecimal.ZERO)
                        .frozen(BigDecimal.ZERO)
                        .locked(BigDecimal.ZERO)
                        .build()))
            .build();

    List<BitgetSubBalanceDto> actual = bitgetAccountServiceRaw.getSubBitgetBalances();

    assertThat(actual).hasSize(1);
    assertThat(actual).first().usingRecursiveComparison().isEqualTo(expected);
  }
}
