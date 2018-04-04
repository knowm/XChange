package org.xchange.coinegg.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class CoinEggTradeAddJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CoinEggTradeAddJSONTest.class.getResourceAsStream(
            "/org/xchange/coinegg/dto/trade/example-trade-add-data.json");

    // Parse JSON Example Using Jackson
    ObjectMapper mapper = new ObjectMapper();
    CoinEggTradeAdd coinEggTradeAdd = mapper.readValue(is, CoinEggTradeAdd.class);

    // Verify The Ticker Unmarshalls Correctly
    assertThat(coinEggTradeAdd).isNotNull();

    assertThat(coinEggTradeAdd.getResult()).isEqualTo(true);
    assertThat(coinEggTradeAdd.getID()).isEqualTo(11);
  }
}
