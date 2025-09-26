package org.knowm.xchange.bitstamp.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

/** Test Transaction[] JSON parsing */
public class BitstampTradingFeeJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BitstampTradingFeeJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitstamp/dto/trade/example-trading-fee.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitstampTradingFee[] result = mapper.readValue(is, BitstampTradingFee[].class);

    assertThat(result.length).isEqualTo(1);
    assertThat(result[0].getMarket()).isEqualTo("btcusd");
  }
}
