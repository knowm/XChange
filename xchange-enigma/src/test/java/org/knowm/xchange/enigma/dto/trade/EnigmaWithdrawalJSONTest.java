package org.knowm.xchange.enigma.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

public class EnigmaWithdrawalJSONTest {

  @Test
  public void testUnMarshal() throws IOException {
    InputStream is = getClass().getClassLoader().getResourceAsStream("withdrawal-list.json");
    ObjectMapper mapper = new ObjectMapper();
    EnigmaWithdrawal[] withdrawal = mapper.readValue(is, EnigmaWithdrawal[].class);
    assertThat(withdrawal[0].getWithdrawalType()).isEqualTo("cash");
    assertThat(withdrawal[0].getAmount()).isEqualTo(new BigDecimal("1"));
    assertThat(withdrawal[0].getCurrency()).isEqualTo("USD");
    assertThat(withdrawal[0].getWithdrawalKey())
        .isEqualTo("5d25aacfbfcf1-5d25aacfbfcf3-5d25aacfbfcf4");
  }
}
