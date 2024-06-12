package org.knowm.xchange.coincheck.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Test;

public class CoincheckExceptionTest {
  @Test
  @SneakyThrows
  public void testParse() {
    String json = "{\"success\":false,\"error\":\"invalid pair\"}";
    CoincheckException ex = new ObjectMapper().readValue(json, CoincheckException.class);
    ex.setHttpStatusCode(400);
    assertThat(ex.isSuccess()).isFalse();
    assertThat(ex.getError()).isEqualTo("invalid pair");
    assertThat(ex.getMessage()).isEqualTo("invalid pair (HTTP status code: 400)");
  }
}
