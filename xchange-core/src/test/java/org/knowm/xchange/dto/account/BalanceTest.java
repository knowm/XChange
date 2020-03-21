package org.knowm.xchange.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.utils.ObjectMapperHelper;

public class BalanceTest {

  @Test
  public void testSerializeDeserialize() throws IOException {

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

    String json = ObjectMapperHelper.toCompactJSON(balance);

    assertThat(json).contains("\"currency\":\"ADA\"");

    Balance jsonCopy = ObjectMapperHelper.readValueStrict(json, Balance.class);
    assertThat(jsonCopy).isEqualToComparingFieldByField(balance);
  }
}
