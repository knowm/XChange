package org.knowm.xchange.bitstamp.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

/** Test Transaction[] JSON parsing */
class BitstampTradingFeeJSONTest {

  @Test
  void unmarshal() throws Exception {

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
