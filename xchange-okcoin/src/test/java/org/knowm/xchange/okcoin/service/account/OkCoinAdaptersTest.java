package org.knowm.xchange.okcoin.service.account;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.dto.account.FundsInfo;
import org.knowm.xchange.dto.account.FundsRecord;
import org.knowm.xchange.okcoin.OkCoinAdapters;
import org.knowm.xchange.okcoin.dto.account.OkCoinAccountRecords;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class OkCoinAdaptersTest {

  @Test
  public void testAdaptFundsInfoHistory() throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();

    InputStream is = OkCoinAdaptersTest.class.getResourceAsStream("/account/example-accountrecords-deposit-data.json");
    OkCoinAccountRecords okCoinAccountDepositRecords = mapper.readValue(is, OkCoinAccountRecords.class);

    is = OkCoinAdaptersTest.class.getResourceAsStream("/account/example-accountrecords-withdrawal-data.json");
    OkCoinAccountRecords okCoinAccountWithdrawalRecords = mapper.readValue(is, OkCoinAccountRecords.class);

    FundsInfo fundsInfo = OkCoinAdapters.adaptFundsInfo(new OkCoinAccountRecords[] {okCoinAccountDepositRecords, okCoinAccountWithdrawalRecords});
    List<FundsRecord> records = fundsInfo.getFundsRecordList();

    assertThat(records.size()).isEqualTo(3);
    FundsRecord fundsRecord = records.get(1);
    assertThat(fundsRecord).isInstanceOf(FundsRecord.class);
    assertThat(fundsRecord.getFundsType()).isEqualTo("DEPOSIT");
    assertThat(fundsRecord.getAmount()).isEqualTo(new BigDecimal("50"));
    assertThat(fundsRecord.getFee().doubleValue()).isEqualTo(new BigDecimal("0.07").doubleValue());
    assertThat(fundsRecord.getAddress()).isEqualTo("1lEWjmlkmlhTqcYj3l33sg980slkjtdqd");
  }
}
