package org.knowm.xchange.dase.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class DaseSingleBalanceJSONTest {

  @Test
  public void unmarshal() throws Exception {
    String json =
        "{\n  \"total\": \"10.00\",\n  \"available\": \"7.50\",\n  \"blocked\": \"2.50\"\n}";
    ObjectMapper mapper = new ObjectMapper();
    DaseSingleBalance dto = mapper.readValue(json, DaseSingleBalance.class);
    assertThat(dto.getTotal()).isEqualTo(new BigDecimal("10.00"));
    assertThat(dto.getAvailable()).isEqualTo(new BigDecimal("7.50"));
    assertThat(dto.getBlocked()).isEqualTo(new BigDecimal("2.50"));
  }
}
