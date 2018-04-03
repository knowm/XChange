package org.knowm.xchange.bitflyer.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

public class BitflyerExecutionJSONTest {
  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitflyerExecution.class.getResourceAsStream(
            "/org/knowm/xchange/bitflyer/dto/trade/example-execution.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitflyerExecution[] response = mapper.readValue(is, BitflyerExecution[].class);

    // then
    assertThat(response.length).isEqualTo(2);

    assertThat(response[0].getId()).isEqualTo(39287);
    assertThat(response[0].getSide()).isEqualTo("BUY");
    assertThat(response[0].getPrice()).isEqualTo(new BigDecimal(31690));
    assertThat(response[0].getSize()).isEqualTo(new BigDecimal("27.04"));
  }
}
