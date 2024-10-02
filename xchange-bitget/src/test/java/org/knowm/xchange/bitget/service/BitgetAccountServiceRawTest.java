package org.knowm.xchange.bitget.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.currency.Currency.USDT;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitget.BitgetExchangeWiremock;
import org.knowm.xchange.bitget.dto.account.BitgetAccountType;
import org.knowm.xchange.bitget.dto.account.BitgetTransferRecordDto;
import org.knowm.xchange.bitget.dto.account.BitgetTransferRecordDto.Status;
import org.knowm.xchange.bitget.dto.account.params.BitgetTransferHistoryParams;

class BitgetAccountServiceRawTest extends BitgetExchangeWiremock {

  BitgetAccountServiceRaw bitgetAccountServiceRaw = (BitgetAccountServiceRaw) exchange.getAccountService();


  @Test
  void transfer_records() throws IOException {
    BitgetTransferRecordDto expected = BitgetTransferRecordDto.builder()
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

    BitgetTransferHistoryParams params = BitgetTransferHistoryParams.builder()
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


}