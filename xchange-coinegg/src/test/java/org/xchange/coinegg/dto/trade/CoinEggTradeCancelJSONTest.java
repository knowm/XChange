package org.xchange.coinegg.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class CoinEggTradeCancelJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CoinEggTradeCancelJSONTest.class.getResourceAsStream(
            "/org/xchange/coinegg/dto/trade/example-trade-cancel-data.json");

    // Parse JSON Example Using Jackson
    ObjectMapper mapper = new ObjectMapper();
    CoinEggTradeCancel coinEggTradeCancel = mapper.readValue(is, CoinEggTradeCancel.class);

    // Verify The Ticker Unmarshalls Correctly
    assertThat(coinEggTradeCancel).isNotNull();

    assertThat(coinEggTradeCancel.getResult()).isEqualTo(true);
    assertThat(coinEggTradeCancel.getID()).isEqualTo(11);
  }
}
