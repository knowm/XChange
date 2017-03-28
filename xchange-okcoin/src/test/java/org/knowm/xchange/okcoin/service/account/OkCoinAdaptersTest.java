package org.knowm.xchange.okcoin.service.account;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.okcoin.dto.account.OkCoinAccountRecords;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class OkCoinAdaptersTest {

  @Test
  public void testAdaptFundingHistory() throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();

    InputStream is = OkCoinAdaptersTest.class.getResourceAsStream("/account/example-accountrecords-deposit-data.json");
    OkCoinAccountRecords okCoinAccountDepositRecords = mapper.readValue(is, OkCoinAccountRecords.class);

    is = OkCoinAdaptersTest.class.getResourceAsStream("/account/example-accountrecords-withdrawal-data.json");
    OkCoinAccountRecords okCoinAccountWithdrawalRecords = mapper.readValue(is, OkCoinAccountRecords.class);

    final List<FundingRecord> records = OkCoinAdapters.adaptFundingHistory(new OkCoinAccountRecords[] {okCoinAccountDepositRecords, okCoinAccountWithdrawalRecords});

    assertThat(records.size()).isEqualTo(3);
    FundingRecord fundingRecord = records.get(1);
    assertThat(fundingRecord).isInstanceOf(FundingRecord.class);
    assertThat(fundingRecord.getType()).isEqualTo("DEPOSIT");
    assertThat(fundingRecord.getAmount()).isEqualTo(new BigDecimal("50"));
    assertThat(fundingRecord.getFee().doubleValue()).isEqualTo(new BigDecimal("0.07").doubleValue());
    assertThat(fundingRecord.getAddress()).isEqualTo("1lEWjmlkmlhTqcYj3l33sg980slkjtdqd");
  }
}
