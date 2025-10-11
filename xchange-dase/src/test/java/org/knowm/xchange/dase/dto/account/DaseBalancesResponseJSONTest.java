package org.knowm.xchange.dase.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class DaseBalancesResponseJSONTest {

  @Test
  public void unmarshal() throws Exception {
    String json =
        "{\n  \"balances\": [\n    {\n      \"id\": \"acc-1\",\n      \"currency\": \"BTC\",\n      \"total\": \"1.23\",\n      \"available\": \"1.00\",\n      \"blocked\": \"0.23\"\n    }\n  ]\n}";
    ObjectMapper mapper = new ObjectMapper();
    DaseBalancesResponse dto = mapper.readValue(json, DaseBalancesResponse.class);
    assertThat(dto.getBalances()).hasSize(1);
    DaseBalanceItem b = dto.getBalances().get(0);
    assertThat(b.getId()).isEqualTo("acc-1");
    assertThat(b.getCurrency()).isEqualTo("BTC");
    assertThat(b.getTotal()).isEqualTo(new BigDecimal("1.23"));
    assertThat(b.getAvailable()).isEqualTo(new BigDecimal("1.00"));
    assertThat(b.getBlocked()).isEqualTo(new BigDecimal("0.23"));
  }
}
