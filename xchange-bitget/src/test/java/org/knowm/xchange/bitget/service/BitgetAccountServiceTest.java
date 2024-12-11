package org.knowm.xchange.bitget.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitget.BitgetExchangeWiremock;
import org.knowm.xchange.bitget.service.params.BitgetFundingHistoryParams;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;

class BitgetAccountServiceTest extends BitgetExchangeWiremock {

  @Test
  void funding_history() throws IOException {
    List<FundingRecord> actual =
        exchange
            .getAccountService()
            .getFundingHistory(
                BitgetFundingHistoryParams.builder()
                    .startTime(Date.from(Instant.ofEpochMilli(1721643200000L)))
                    .endTime(Date.from(Instant.ofEpochMilli(1727970869665L)))
                    .endId("1203378295345901568")
                    .build());

    FundingRecord expected =
        new FundingRecord.Builder()
            .setInternalId("1200126632376020992")
            .setDate(Date.from(Instant.ofEpochMilli(1721858437064L)))
            .setAddress("EQAPOcvrl-4fjsw9W5iUBC8np6UtVgE0QPDzLgfsTJh9NYX5")
            .setBlockchainTransactionHash("scWb5s8OtLL_kSE8rQv5fDvreOGUe82wcV9KXrGNNww=")
            .setCurrency(Currency.USDT)
            .setType(Type.DEPOSIT)
            .setAmount(new BigDecimal("100.00000000"))
            .setStatus(Status.COMPLETE)
            .build();

    assertThat(actual).hasSize(2);
    assertThat(actual).first().usingRecursiveComparison().isEqualTo(expected);
  }
}
