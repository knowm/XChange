package org.knowm.xchange.bitmex.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BitmexPositionJSONTest {
  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitmexTradesJSONTest.class.getResourceAsStream("/trade/example-position.json");

    ObjectMapper mapper = new ObjectMapper();
    BitmexPosition position = mapper.readValue(is, BitmexPosition.class);
    assertThat(position.getSymbol()).isEqualTo("string");
    assertThat(position.getAccount()).isEqualTo(0);
    assertThat(position.getCurrency()).isEqualTo("string");
    assertThat(position.getUnderlying()).isEqualTo("string");
    assertThat(position.getQuoteCurrency()).isEqualTo("string");

  }
}