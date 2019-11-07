package org.knowm.xchange.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;

public class BalanceTest {

  @Test
  public void testSerializeDeserialize() throws IOException {

    // This deliberately doesn't use FAIL_ON_UNKNOWN_PROPERTIES so that
    // we ensure serialization/deserialization is symmetrical.
    ObjectMapper objectMapper = new ObjectMapper();

    Balance balance =
        new Balance.Builder()
            .available(new BigDecimal("0.12"))
            .borrowed(new BigDecimal("0.13"))
            .currency(Currency.ADA)
            .depositing(new BigDecimal("0.14"))
            .frozen(new BigDecimal("0.15"))
            .loaned(new BigDecimal("0.16"))
            .withdrawing(new BigDecimal("0.17"))
            .build();

    String json = objectMapper.writeValueAsString(balance);

    System.out.println(json);
    assertThat(json).contains("\"currency\":\"ADA\"");

    Balance jsonCopy = objectMapper.readValue(json, Balance.class);
    assertThat(jsonCopy).isEqualToComparingFieldByField(balance);
  }
}
