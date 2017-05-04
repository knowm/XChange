package org.knowm.xchange.okcoin.service.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.okcoin.dto.account.OkCoinAccountRecords;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OkCoinAdaptersTest {

  @Test
  public void testAdaptFundingHistory() throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();

    InputStream is = OkCoinAdaptersTest.class.getResourceAsStream("/account/example-accountrecords-deposit-data.json");
    OkCoinAccountRecords okCoinAccountDepositRecords = mapper.readValue(is, OkCoinAccountRecords.class);

    is = OkCoinAdaptersTest.class.getResourceAsStream("/account/example-accountrecords-withdrawal-data.json");
    OkCoinAccountRecords okCoinAccountWithdrawalRecords = mapper.readValue(is, OkCoinAccountRecords.class);

    final List<FundingRecord> records = OkCoinAdapters
        .adaptFundingHistory(new OkCoinAccountRecords[]{okCoinAccountDepositRecords, okCoinAccountWithdrawalRecords});

    assertThat(records.size()).isEqualTo(3);
    FundingRecord depositRecord = records.get(1);
    assertThat(depositRecord).isInstanceOf(FundingRecord.class);
    assertThat(depositRecord.getType()).isEqualTo(FundingRecord.Type.DEPOSIT);
    assertThat(depositRecord.getStatus()).isEqualTo(FundingRecord.Status.COMPLETE);
    assertThat(depositRecord.getAmount()).isEqualTo(new BigDecimal("50"));
    assertThat(depositRecord.getFee().doubleValue()).isEqualTo(new BigDecimal("0.07").doubleValue());
    assertThat(depositRecord.getAddress()).isEqualTo("1lEWjmlkmlhTqcYj3l33sg980slkjtdqd");

    FundingRecord withdrawalRecord = records.get(2);
    assertThat(withdrawalRecord).isInstanceOf(FundingRecord.class);
    assertThat(withdrawalRecord.getType()).isEqualTo(FundingRecord.Type.WITHDRAWAL);
    assertThat(withdrawalRecord.getStatus()).isEqualTo(FundingRecord.Status.PROCESSING);
    assertThat(withdrawalRecord.getAmount()).isEqualTo(new BigDecimal("33"));
    assertThat(withdrawalRecord.getFee().doubleValue()).isEqualTo(new BigDecimal("0.05").doubleValue());
    assertThat(withdrawalRecord.getAddress()).isEqualTo("8OKSDF39aOIUl34lksUIYW3kl3l39d");
  }
}
